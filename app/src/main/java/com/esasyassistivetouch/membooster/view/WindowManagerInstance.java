package com.esasyassistivetouch.membooster.view;

import android.content.Context;
import android.view.WindowManager;

class WindowManagerInstance {


    private static WindowManager windowManager ;
    private static Context applicationContext;

    static WindowManager newInstance(){
        if (windowManager == null){
            windowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    static void setApplicationContext(Context context){
        applicationContext = context;
    }
}
