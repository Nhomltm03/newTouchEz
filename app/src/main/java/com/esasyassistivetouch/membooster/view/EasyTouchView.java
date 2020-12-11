package com.esasyassistivetouch.membooster.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.esasyassistivetouch.membooster.R;


public class EasyTouchView extends LinearLayout {

    public static boolean isAlive = false;

    private static int screenWidth;
    private static int screenHeight;
    private static int viewWidth;
    private static float xInScreen;
    private static float yInScreen;
    private static float xDownInScreen;
    private static float yDownInScreen;
    private static float xInView;
    private static float yInView;

    private WindowManager.LayoutParams mLayoutParams;

    public EasyTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        this.initLayoutParams();
        LayoutInflater.from(context).inflate(R.layout.easytouch_view_layout, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = this.getWidth();
    }

    @SuppressLint("RtlHardcoded")
    public void initLayoutParams() {
        this.mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            this.mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        this.mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        this.mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        this.mLayoutParams.x = screenWidth;
        this.mLayoutParams.y = screenHeight / 2;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                mLayoutParams.x = (int) (xInScreen - xInView);
                mLayoutParams.y = (int) (yInScreen - yInView);
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (xInScreen == xDownInScreen && yInScreen == yDownInScreen) {
                    MyViewHolder.openMultiTaskWindow();
                } else {
                    startViewPositionAnimator();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void updateViewPosition() {
        WindowManager windowManager = WindowManagerInstance.newInstance();
        windowManager.updateViewLayout(this, mLayoutParams);
    }

    public void startViewPositionAnimator() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            if (mLayoutParams.x + viewWidth / 2 <= screenWidth / 2) {
                mLayoutParams.x = (int) ((float) mLayoutParams.x * (1 - value));
            } else {
                mLayoutParams.x += (int) ((float) (screenWidth - mLayoutParams.x) * value);
            }
            this.updateViewPosition();
        });
        valueAnimator.start();
    }

    public float getStatusBarHeight() {
        Rect rectangle = new Rect();
        this.getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public WindowManager.LayoutParams getWindowManagerLayoutParams() {
        return this.mLayoutParams;
    }

}
