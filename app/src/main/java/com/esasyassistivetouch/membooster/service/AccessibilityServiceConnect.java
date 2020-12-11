package com.esasyassistivetouch.membooster.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import com.esasyassistivetouch.membooster.message.MessageEvent;
import com.esasyassistivetouch.membooster.ulti.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SuppressLint("Registered")
public class AccessibilityServiceConnect extends AccessibilityService {
    public static boolean isConnect;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        isConnect = false;
    }

    @Override
    protected void onServiceConnected() {
        /*Log.e("Connect", "isServiceRunning");*/
        super.onServiceConnected();
        isConnect = true;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    @Override
    public void onInterrupt() {
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String msg = event.getMsg();
        if (msg.equals(Constants.MSG_GLOBAL_ACTION_BACK)) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        if (msg.equals(Constants.MSG_GLOBAL_ACTION_RECENT)) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
        if (msg.equals(Constants.MSG_GLOBAL_CONTROL_POWER_SETTING)) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
        }
        if (msg.equals(Constants.MSG_GLOBAL_SCREEN_SHOT)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
            }

        }


    }

}
