package com.esasyassistivetouch.membooster.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.esasyassistivetouch.membooster.MainActivity;
import com.esasyassistivetouch.membooster.R;
import com.esasyassistivetouch.membooster.message.MessageEvent;
import com.esasyassistivetouch.membooster.ulti.Constants;
import com.esasyassistivetouch.membooster.view.MyViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

@SuppressLint("Registered")
public class MyService extends Service {
    private static final int REQUEST_CODE = 2019;
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String CHANNEL_NAME = "Foreground_Service_Channel";
    private static final int LOCK_ROTATION = 0;
    private static final int ACTIVE_ACCELEROMETER_ROTATION = 1;
    public static boolean isOnFlashLight = false;
    private MyViewHolder myViewHolder;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.createNotification();
        if (myViewHolder == null) {
            myViewHolder = MyViewHolder.getInstance(this.getApplicationContext());
        }

        MyViewHolder.showEasyTouchView();

        if (intent != null) {
            if (intent.getBooleanExtra(Constants.EXTRA_HIDE_ICON_TOUCH, false)) {
                MyViewHolder.hideEasyTouchView();
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_TURN_OFF_SWITCH));
                this.stopSelf();
            }
        }
        return START_STICKY;
    }

    private void createNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager mNotificationManager = getSystemService(NotificationManager.class);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(serviceChannel);
            }
        }
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_control_easy_touch);

        PendingIntent piOpenHome = PendingIntent.getActivity(this, 2019,
                new Intent(this, MainActivity.class), 0);

        PendingIntent piHideIcon = PendingIntent.getService(this, REQUEST_CODE,
                new Intent(this, MyService.class).putExtra(Constants.EXTRA_HIDE_ICON_TOUCH, true), 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_notification_stop, piHideIcon);
        Notification mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.icon_touch, 1)
                .setContentIntent(piOpenHome)
                .build();
        startForeground(1, mBuilder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void onEventMainThread(@NotNull MessageEvent event) {
        String msg = event.getMsg();
        switch (msg) {
            case Constants.MSG_STOP_SERVICE:
                MyViewHolder.hideEasyTouchView();
                stopSelf();
                break;
            case Constants.MSG_GLOBAL_CONTROL_WIFI:
                controlWifiStage();
                break;
            case Constants.MSG_GLOBAL_CONTROL_BLUE_TOOTH:
                controlBlueToothStage();
                break;
            case Constants.MSG_OPEN_DATA_SUMMARY:
                openDataUsedSummary();
                break;
            case Constants.MSG_OPEN_AIR_PLANE_SETTING_ACTIVITY:
                openAirPlaneSetting();
                break;
            case Constants.MSG_GLOBAL_CONTROL_RING_MODE_NORMAL:
                controlSoundMode(RINGER_MODE_NORMAL);
                break;
            case Constants.MSG_GLOBAL_CONTROL_RING_MODE_SILENT:
                controlSoundMode(RINGER_MODE_SILENT);
                break;
            case Constants.MSG_GLOBAL_CONTROL_RING_MODE_VIBRATE:
                controlSoundMode(RINGER_MODE_VIBRATE);
                break;
            case Constants.MSG_GLOBAL_CONTROL_VOLUME_DOWN:
                controlVolume(false);
                break;
            case Constants.MSG_GLOBAL_CONTROL_VOLUME_UP:
                controlVolume(true);
                break;
            case Constants.MSG_GLOBAL_CONTROL_ACTIVE_FLASH:
                controlActiveFlash(true);
                break;
            case Constants.MSG_GLOBAL_CONTROL_INACTIVE_FLASH:
                controlActiveFlash(false);
                break;
            case Constants.MSG_GLOBAL_CONTROL_ROTATION:
                controlRotation(ACTIVE_ACCELEROMETER_ROTATION);
                break;
            case Constants.MSG_GLOBAL_LOCK_ROTATION:
                controlRotation(LOCK_ROTATION);
                break;
        }
    }

    private void controlRotation(int controlRotation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {

                if (controlRotation == LOCK_ROTATION) {
                    Settings.System.putInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, LOCK_ROTATION);
                } else if (controlRotation == ACTIVE_ACCELEROMETER_ROTATION) {
                    Settings.System.putInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, ACTIVE_ACCELEROMETER_ROTATION);
                }

            } else {
                Intent intentPermission = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intentPermission.setData(Uri.parse("package:" + getPackageName()));
                this.startActivity(intentPermission);
            }

        } else {
            if (controlRotation == LOCK_ROTATION) {
                Settings.System.putInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, LOCK_ROTATION);
            } else if (controlRotation == ACTIVE_ACCELEROMETER_ROTATION) {
                Settings.System.putInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, ACTIVE_ACCELEROMETER_ROTATION);
            }
        }
    }

    private void controlActiveFlash(boolean isActive) {
        if (this.getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null;

            if (camManager != null) {
                try {
                    cameraId = camManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            if (camManager != null) {
                try {
                    if (cameraId != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            camManager.setTorchMode(cameraId, isActive);
                            isOnFlashLight = isActive;
                        } else {
                            Toast.makeText(this.getApplicationContext(), R.string.all_function_not_work_at_low_api, Toast.LENGTH_SHORT).show();
                            isOnFlashLight = false;
                        }

                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this.getApplicationContext(), R.string.all_camera_flash_not_available, Toast.LENGTH_SHORT).show();
        }

    }

    private void controlVolume(boolean isAdjustVolumeUp) {
        AudioManager audioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (isAdjustVolumeUp) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            } else {
                audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
        }
    }

    private void openAirPlaneSetting() {
        Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void controlWifiStage() {
        WifiManager wifiManager;
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
                wifiManager.setWifiEnabled(true);
            }
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    private void controlBlueToothStage() {
        BluetoothManager bluetoothManager = (BluetoothManager) this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            if (bluetoothManager.getAdapter().isEnabled()) {
                bluetoothManager.getAdapter().disable();
            }
            if (bluetoothManager.getAdapter().disable()) {
                bluetoothManager.getAdapter().enable();
            }
        }
    }

    private void controlSoundMode(int soundMode) {
        NotificationManager notificationManager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            startActivity(new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        } else {
            AudioManager audioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                switch (soundMode) {
                    case RINGER_MODE_VIBRATE:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        break;
                    case RINGER_MODE_SILENT:
                        audioManager.setRingerMode(RINGER_MODE_SILENT);
                        break;
                    case RINGER_MODE_NORMAL:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        break;
                }
            }
        }
    }

    private void openDataUsedSummary() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(Constants.ANDROID_SETTING_PACKAGE, Constants.ANDROID_SETTING_DATA_SUMMARY_ACTIVITY));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

}
