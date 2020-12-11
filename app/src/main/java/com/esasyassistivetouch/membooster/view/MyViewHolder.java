package com.esasyassistivetouch.membooster.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.WindowManager;

import com.esasyassistivetouch.membooster.feature.MultiTaskMainView;

public class MyViewHolder {
    private static EasyTouchView mEasyTouchView;
    @SuppressLint("StaticFieldLeak")
    private static MultiTaskMainView multiTaskMainView;
    private static WindowManager mWindowManager;


    public MyViewHolder(Context context){
        Context applicationContext = context.getApplicationContext();
        mEasyTouchView = new EasyTouchView(applicationContext, null);
        multiTaskMainView = new MultiTaskMainView(applicationContext, null);
        WindowManagerInstance.setApplicationContext(applicationContext);
        mWindowManager = WindowManagerInstance.newInstance();
    }

    public static void showEasyTouchView(){
        if (!EasyTouchView.isAlive) {
            WindowManager.LayoutParams layoutParams = mEasyTouchView.getWindowManagerLayoutParams();
            EasyTouchView.isAlive = true;
            mWindowManager.addView(mEasyTouchView, layoutParams);
        }
    }

    public static void hideEasyTouchView(){
        if (EasyTouchView.isAlive) {
            EasyTouchView.isAlive = false;
            mWindowManager.removeView(mEasyTouchView);
        }
    }

    private static void showMultiTaskMainView(){
        WindowManager.LayoutParams layoutParams = multiTaskMainView.getWindowLayoutParams();
        mWindowManager.addView(multiTaskMainView, layoutParams);
    }

    public static void hideMultiTaskMainView(){
        mWindowManager.removeView(multiTaskMainView);
    }

    static void openMultiTaskWindow(){
        hideEasyTouchView();
        showMultiTaskMainView();
    }

}
