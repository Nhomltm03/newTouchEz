package com.esasyassistivetouch.membooster.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;

import com.esasyassistivetouch.membooster.feature.MultiTaskMainView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class MyViewHolder {

    private static EasyTouchView mEasyTouchView;

    @SuppressLint("StaticFieldLeak")
    private static MultiTaskMainView multiTaskMainView;

    private static WindowManager mWindowManager;

    private static MyViewHolder instance;

    private MyViewHolder(@NotNull Context context) {
        Context applicationContext = context.getApplicationContext();
        mEasyTouchView = new EasyTouchView(applicationContext, null);
        multiTaskMainView = new MultiTaskMainView(applicationContext, null);
        WindowManagerInstance.setApplicationContext(applicationContext);
        mWindowManager = WindowManagerInstance.newInstance();
    }

    public static MyViewHolder getInstance(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        context = contextWeakReference.get();
        if (instance == null) {
            instance = new MyViewHolder(context);
        }
        return instance;
    }

    public static void showEasyTouchView() {
        if (!EasyTouchView.isAlive) {
            WindowManager.LayoutParams layoutParams = mEasyTouchView.getWindowManagerLayoutParams();
            EasyTouchView.isAlive = true;
            mWindowManager.addView(mEasyTouchView, layoutParams);
        }
    }

    public static void hideEasyTouchView() {
        if (EasyTouchView.isAlive) {
            EasyTouchView.isAlive = false;
            mWindowManager.removeView(mEasyTouchView);
        }
    }

    private static void showMultiTaskMainView() {
        WindowManager.LayoutParams layoutParams = multiTaskMainView.getWindowLayoutParams();
        try {
            if (!ViewCompat.isAttachedToWindow(multiTaskMainView)) {
                mWindowManager.addView(multiTaskMainView, layoutParams);
            } else {
                new Handler().post(() -> {
                    try {
                        mWindowManager.addView(multiTaskMainView, layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Handler().post(() -> {
                try {
                    mWindowManager.addView(multiTaskMainView, layoutParams);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public static void hideMultiTaskMainView() {
        mWindowManager.removeView(multiTaskMainView);
    }

    static void openMultiTaskWindow() {
        hideEasyTouchView();
        showMultiTaskMainView();
    }

}
