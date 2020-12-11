package com.esasyassistivetouch.membooster.feature;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.esasyassistivetouch.membooster.MainActivity;
import com.esasyassistivetouch.membooster.R;
import com.esasyassistivetouch.membooster.message.MessageEvent;
import com.esasyassistivetouch.membooster.service.AccessibilityServiceConnect;
import com.esasyassistivetouch.membooster.service.MyService;
import com.esasyassistivetouch.membooster.ulti.Constants;
import com.esasyassistivetouch.membooster.view.MyViewHolder;

import org.greenrobot.eventbus.EventBus;


public class MultiTaskMainView extends RelativeLayout {

    private static final int SELECT_CONTROL_WIFI = 1;
    private static final int SELECT_CONTROL_BLUE_TOOTH = 2;
    private static final int SELECT_CONTROL_LOCATION = 3;
    private static final int SELECT_DATA_SUMMARY = 4;
    private static final int SELECT_ACTIVE_AIRPLANE = 5;
    private static final int SELECT_VOLUME_DOWN = 6;
    private static final int SELECT_VOLUME_UP = 7;
    private static final int SELECT_CONTROL_SOUND_MODE = 8;
    private static final int SELECT_CONTROL_ROTATION_MODE = 9;
    private static final int SELECT_ACTIVE_FLASH = 10;
    private static final int SELECT_SCREEN_SHOT = 11;
    private static final int SELECT_VISUAL_POWER_BUTTON = 12;

    private static int functionType1;
    private static int functionType2;
    private static int functionType3;
    private static int functionType4;
    private static int functionType5;
    private static int functionType6;
    private static int functionType7;
    private static int functionType8;
    private Context mContext;
    private WindowManager.LayoutParams mLayoutParams;
    private View viewMultiTaskView;
    private RelativeLayout.LayoutParams floatingWindowParams;
    private ImageView ivSettingFunctionItem1;
    private ImageView ivSettingFunctionItem2;
    private ImageView ivSettingFunctionItem3;
    private ImageView ivSettingFunctionItem4;
    private ImageView ivSettingFunctionItem5;
    private ImageView ivSettingFunctionItem6;
    private ImageView ivSettingFunctionItem7;
    private ImageView ivSettingFunctionItem8;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_visual_home_button:
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    mContext.startActivity(intent);
                    MyViewHolder.hideMultiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.iv_visual_back_button:
                    handleAccessibilityClick(Constants.MSG_GLOBAL_ACTION_BACK);
                    MyViewHolder.hideMultiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.iv_recent_app:
                    handleAccessibilityClick(Constants.MSG_GLOBAL_ACTION_RECENT);
                    MyViewHolder.hideMultiTaskMainView();
                    MyViewHolder.showEasyTouchView();
                    break;
                case R.id.iv_open_home:
                    openMainActivity();
                    break;
                case R.id.iv_favorite_app:
                    replaceToFavorView();
                    break;
                case R.id.iv_favorite_back:
                    replaceToMainView();
                    break;
                case R.id.iv_all_setting_control:
                    replaceToSettingView();
                    break;
                case R.id.iv_list_all_app:
                    getListAllApp();
                    break;
                case R.id.iv_clear_ram:
                    clearRam();
                    break;
                case R.id.iv_lock_screen:
                    lockScreen();
                    break;
                default:
                    break;
            }
        }
    };
    private OnClickListener settingFunctionOnClickListener = view -> {
        switch (view.getId()) {
            case R.id.iv_setting_function_1:
                handleOnClickFunctionItem(ivSettingFunctionItem1, functionType1);
                break;
            case R.id.iv_setting_function_2:
                handleOnClickFunctionItem(ivSettingFunctionItem2, functionType2);
                break;
            case R.id.iv_setting_function_3:
                handleOnClickFunctionItem(ivSettingFunctionItem3, functionType3);
                break;
            case R.id.iv_setting_function_4:
                handleOnClickFunctionItem(ivSettingFunctionItem4, functionType4);
                break;
            case R.id.iv_setting_function_5:
                handleOnClickFunctionItem(ivSettingFunctionItem5, functionType5);
                break;
            case R.id.iv_setting_function_6:
                handleOnClickFunctionItem(ivSettingFunctionItem6, functionType6);
                break;
            case R.id.iv_setting_function_7:
                handleOnClickFunctionItem(ivSettingFunctionItem7, functionType7);
                break;
            case R.id.iv_setting_function_8:
                handleOnClickFunctionItem(ivSettingFunctionItem8, functionType8);
                break;
            case R.id.iv_setting_back:
                replaceToMainView();
                break;
            default:
                break;
        }
    };

    public MultiTaskMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLayoutParams();
        initHomeScreen();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;

    }

    public void initHomeScreen() {
        ImageView ivVisualHome = findViewById(R.id.iv_visual_home_button);
        ImageView ivFavoriteScreen = findViewById(R.id.iv_favorite_app);
        ImageView ivVisualBackButton = findViewById(R.id.iv_visual_back_button);
        ImageView ivRecentApp = findViewById(R.id.iv_recent_app);
        ImageView ivAllSetting = findViewById(R.id.iv_all_setting_control);
        ImageView ivOpenHome = findViewById(R.id.iv_open_home);
        ImageView ivListAllApp = findViewById(R.id.iv_list_all_app);
        ImageView ivClearRam = findViewById(R.id.iv_clear_ram);
        ImageView ivLockScreen = findViewById(R.id.iv_lock_screen);

        ivListAllApp.setOnClickListener(onClickListener);
        ivClearRam.setOnClickListener(onClickListener);
        ivLockScreen.setOnClickListener(onClickListener);
        ivFavoriteScreen.setOnClickListener(onClickListener);
        ivOpenHome.setOnClickListener(onClickListener);
        ivAllSetting.setOnClickListener(onClickListener);
        ivVisualBackButton.setOnClickListener(onClickListener);
        ivVisualHome.setOnClickListener(onClickListener);
        ivRecentApp.setOnClickListener(onClickListener);
    }

    public void initFavoritesScreen() {
        ImageView ivFavoriteBack = findViewById(R.id.iv_favorite_back);
        ivFavoriteBack.setOnClickListener(onClickListener);
    }

    public void initAllSettingFunctionView() {
        ivSettingFunctionItem1 = findViewById(R.id.iv_setting_function_1);
        ivSettingFunctionItem2 = findViewById(R.id.iv_setting_function_2);
        ivSettingFunctionItem3 = findViewById(R.id.iv_setting_function_3);
        ivSettingFunctionItem4 = findViewById(R.id.iv_setting_function_4);
        ivSettingFunctionItem5 = findViewById(R.id.iv_setting_function_5);
        ivSettingFunctionItem6 = findViewById(R.id.iv_setting_function_6);
        ivSettingFunctionItem7 = findViewById(R.id.iv_setting_function_7);
        ivSettingFunctionItem8 = findViewById(R.id.iv_setting_function_8);
        ImageView ivSettingBack = findViewById(R.id.iv_setting_back);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        functionType1 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, 1);
        functionType2 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, 2);
        functionType3 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, 3);
        functionType4 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, 4);
        functionType5 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, 5);
        functionType6 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, 6);
        functionType7 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, 7);
        functionType8 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, 8);
        updateIconFunctionForItem(ivSettingFunctionItem1, functionType1);
        updateIconFunctionForItem(ivSettingFunctionItem2, functionType2);
        updateIconFunctionForItem(ivSettingFunctionItem3, functionType3);
        updateIconFunctionForItem(ivSettingFunctionItem4, functionType4);
        updateIconFunctionForItem(ivSettingFunctionItem5, functionType5);
        updateIconFunctionForItem(ivSettingFunctionItem6, functionType6);
        updateIconFunctionForItem(ivSettingFunctionItem7, functionType7);
        updateIconFunctionForItem(ivSettingFunctionItem8, functionType8);
        ivSettingBack.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem1.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem2.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem3.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem4.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem5.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem6.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem7.setOnClickListener(settingFunctionOnClickListener);
        ivSettingFunctionItem8.setOnClickListener(settingFunctionOnClickListener);
    }

    private void handleOnClickFunctionItem(ImageView view, int functionType) {
        switch (functionType) {
            case SELECT_CONTROL_WIFI:
                controlWifiStage(view);
                break;
            case SELECT_CONTROL_BLUE_TOOTH:
                controlBlueToothStage(view);
                break;
            case SELECT_CONTROL_LOCATION:
                controlLocation();
                break;
            case SELECT_DATA_SUMMARY:
                openDataSummary();
                break;
            case SELECT_ACTIVE_AIRPLANE:
                openAirPlaneSettingActivity();
                break;
            case SELECT_VOLUME_DOWN:
                controlVolume(false);
                break;
            case SELECT_VOLUME_UP:
                controlVolume(true);
                break;
            case SELECT_CONTROL_SOUND_MODE:
                controlSoundMode(view);
                break;
            case SELECT_CONTROL_ROTATION_MODE:
                controlRotationMode(view);
                break;
            case SELECT_ACTIVE_FLASH:
                activeFlash(view);
                break;
            case SELECT_SCREEN_SHOT:
                screenShot();
                break;
            case SELECT_VISUAL_POWER_BUTTON:
                pressVisualPowerButton();
                break;
            default:
                break;
        }
    }

    private void pressVisualPowerButton() {
        if (AccessibilityServiceConnect.isConnect) {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_POWER_SETTING));
            MyViewHolder.hideMultiTaskMainView();
            MyViewHolder.showEasyTouchView();
        } else {
            mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @SuppressLint("WrongThread")
    private void screenShot() {

        if (AccessibilityServiceConnect.isConnect) {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_SCREEN_SHOT));
            MyViewHolder.hideMultiTaskMainView();
            MyViewHolder.showEasyTouchView();
        } else {
            mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private int getAccelerometerRotationStatus() throws Settings.SettingNotFoundException {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
    }

    private void activeFlash(ImageView view) {

        if (MyService.isOnFlashLight) {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_INACTIVE_FLASH));
            view.setImageResource(R.drawable.ic_highlight_off);
        } else {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_ACTIVE_FLASH));
            view.setImageResource(R.drawable.ic_highlight_black);
        }

    }

    private void controlRotationMode(ImageView view) {
        try {
            if (getAccelerometerRotationStatus() == 0) {
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_ROTATION));
                view.setImageResource(R.drawable.ic_screen_rotation);
            } else if (getAccelerometerRotationStatus() == 1) {
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_LOCK_ROTATION));
                view.setImageResource(R.drawable.ic_screen_lock_rotation);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateIconFunctionForItem(ImageView view, int functionType) {
        switch (functionType) {
            case SELECT_CONTROL_WIFI:
                if (isWifiOn()) {
                    view.setImageResource(R.drawable.ic_wifi_black_24dp);
                } else {
                    view.setImageResource(R.drawable.ic_signal_wifi_off_black_24dp);
                }
                break;
            case SELECT_CONTROL_BLUE_TOOTH:
                if (isBlueToothOn()) {
                    view.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                } else {
                    view.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
                }
                break;
            case SELECT_CONTROL_LOCATION:
                if (isLocationEnabled(mContext)) {
                    view.setImageResource(R.drawable.ic_location_on_black_24dp);
                } else {
                    view.setImageResource(R.drawable.ic_location_off);
                }
                break;
            case SELECT_DATA_SUMMARY:
                view.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case SELECT_ACTIVE_AIRPLANE:
                if (isAirplaneModeOn(mContext)) {
                    view.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                } else {
                    view.setImageResource(R.drawable.ic_airplanemode_inactive);
                }
                break;
            case SELECT_VOLUME_DOWN:
                view.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case SELECT_VOLUME_UP:
                view.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case SELECT_CONTROL_SOUND_MODE:
                AudioManager audioManager = (AudioManager) mContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                if (audioManager != null) {
                    switch (audioManager.getRingerMode()) {
                        case AudioManager.RINGER_MODE_NORMAL:
                            view.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                            break;
                        case AudioManager.RINGER_MODE_VIBRATE:
                            view.setImageResource(R.drawable.ic_vibration_black_24dp);
                            break;
                        case AudioManager.RINGER_MODE_SILENT:
                            view.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                            break;
                    }
                }
                break;
            case SELECT_ACTIVE_FLASH:
                if (isOnFlashLight()) {
                    view.setImageResource(R.drawable.ic_highlight_black);
                } else {
                    view.setImageResource(R.drawable.ic_highlight_off);
                }
                break;
            case SELECT_CONTROL_ROTATION_MODE:
                try {
                    if (getAccelerometerRotationStatus() == 0) {
                        view.setImageResource(R.drawable.ic_screen_lock_rotation);
                    } else if (getAccelerometerRotationStatus() == 1) {
                        view.setImageResource(R.drawable.ic_screen_rotation);
                    }
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case SELECT_SCREEN_SHOT:
                view.setImageResource(R.drawable.ic_screen_shot);
                break;
            case SELECT_VISUAL_POWER_BUTTON:
                view.setImageResource(R.drawable.ic_power_settings);
                break;
        }
    }

    private void controlVolume(boolean isVolumeUp) {
        if (isVolumeUp) {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_VOLUME_UP));
        } else {
            EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_VOLUME_DOWN));
        }
    }

    private void controlLocation() {
        mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        MyViewHolder.hideMultiTaskMainView();
        MyViewHolder.showEasyTouchView();
    }

    private void controlSoundMode(ImageView view) {

        NotificationManager notificationManager = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            mContext.startActivity(new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        } else {
            AudioManager audioManager = (AudioManager) mContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                switch (audioManager.getRingerMode()) {
                    case AudioManager.RINGER_MODE_NORMAL:
                        EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_RING_MODE_VIBRATE));
                        view.setImageResource(R.drawable.ic_vibration_black_24dp);
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_RING_MODE_SILENT));
                        view.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_RING_MODE_NORMAL));
                        view.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                        break;
                }
            }
        }
    }

    private void openAirPlaneSettingActivity() {
        EventBus.getDefault().post(new MessageEvent(Constants.MSG_OPEN_AIR_PLANE_SETTING_ACTIVITY));
    }

    private void controlBlueToothStage(ImageView view) {
        EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_BLUE_TOOTH));
        if (!isBlueToothOn()) {
            view.setImageResource(R.drawable.ic_bluetooth_black_24dp);
        } else {
            view.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
        }
    }

    private void openDataSummary() {
        EventBus.getDefault().post(new MessageEvent(Constants.MSG_OPEN_DATA_SUMMARY));
        MyViewHolder.hideMultiTaskMainView();
        MyViewHolder.showEasyTouchView();
    }

    private void clearRam() {
    }

    private void lockScreen() {
    }

    private void controlWifiStage(ImageView view) {
        EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_CONTROL_WIFI));
        if (!isWifiOn()) {
            view.setImageResource(R.drawable.ic_wifi_black_24dp);
        } else {
            view.setImageResource(R.drawable.ic_signal_wifi_off_black_24dp);
        }
    }

    private boolean isBlueToothOn() {
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        return bluetoothManager != null && bluetoothManager.getAdapter().isEnabled();
    }

    private boolean isOnFlashLight() {
        return MyService.isOnFlashLight;
    }

    private boolean isWifiOn() {
        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return mainWifiObj != null && mainWifiObj.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }

    @SuppressLint("InflateParams")
    public void initLayoutParams() {
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0,
                0,
                WindowManager.LayoutParams.TYPE_PHONE,
                //          0,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
        );
        mLayoutParams.gravity = Gravity.CENTER;
        viewMultiTaskView = LayoutInflater.from(mContext).inflate(R.layout.multitask_main_layout, null);
        floatingWindowParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        floatingWindowParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(viewMultiTaskView, floatingWindowParams);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            MyViewHolder.hideMultiTaskMainView();
            MyViewHolder.showEasyTouchView();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            MyViewHolder.hideMultiTaskMainView();
            MyViewHolder.showEasyTouchView();
        }
        return super.onKeyDown(keyCode, event);
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        return mLayoutParams;
    }

    private void handleAccessibilityClick(String message) {
        if (AccessibilityServiceConnect.isConnect) {
            if (message.equals(Constants.MSG_GLOBAL_ACTION_BACK)) {
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_ACTION_BACK));
            }
            if (message.equals(Constants.MSG_GLOBAL_ACTION_RECENT)) {
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_GLOBAL_ACTION_RECENT));
            }
        } else {
            mContext.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @SuppressLint("InflateParams")
    private void replaceToFavorView() {
        this.removeView(viewMultiTaskView);
        viewMultiTaskView = LayoutInflater.from(mContext).inflate(R.layout.multitask_favor_layout, null);
        this.addView(viewMultiTaskView, floatingWindowParams);
        initFavoritesScreen();
    }

    private void openMainActivity() {
        mContext.startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void getListAllApp() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    }

    @SuppressLint("InflateParams")
    private void replaceToSettingView() {
        this.removeView(viewMultiTaskView);
        viewMultiTaskView = LayoutInflater.from(mContext).inflate(R.layout.multitask_setting_layout, null);
        this.addView(viewMultiTaskView, floatingWindowParams);
        initAllSettingFunctionView();
    }

    @SuppressLint("InflateParams")
    private void replaceToMainView() {
        this.removeView(viewMultiTaskView);
        viewMultiTaskView = LayoutInflater.from(mContext).inflate(R.layout.multitask_main_layout, null);
        this.addView(viewMultiTaskView, floatingWindowParams);
        initHomeScreen();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        MyViewHolder.hideMultiTaskMainView();
        MyViewHolder.showEasyTouchView();
        return super.dispatchKeyEvent(event);
    }


}
