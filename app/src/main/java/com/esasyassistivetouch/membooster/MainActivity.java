package com.esasyassistivetouch.membooster;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.esasyassistivetouch.membooster.activity.AdjustMenuActivity;
import com.esasyassistivetouch.membooster.databinding.ActivityMainBinding;
import com.esasyassistivetouch.membooster.message.MessageEvent;
import com.esasyassistivetouch.membooster.service.MyService;
import com.esasyassistivetouch.membooster.ulti.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_CODE = 101;

    private ActivityMainBinding mainBinding;
    private MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainBinding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.mainBinding.getRoot());

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        this.myService = new MyService();

        this.mainBinding.swOpenEasyTouch.setOnCheckedChangeListener(this);
        this.mainBinding.btAdjustMenuItem.setOnClickListener(v -> this.startActivity(new Intent(this, AdjustMenuActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.checkFloatWindowPermission()) {
            ContextCompat.startForegroundService(this, new Intent(this, MyService.class));
        }
        this.mainBinding.swOpenEasyTouch.setChecked(this.isMyServiceRunning(this.myService.getClass()));
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFloatWindowPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(this);
        } else {
            return true;
        }
    }

    private void showPromptingDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.hint));
        alertDialog.setMessage(getResources().getString(R.string.hint_content_window));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            Intent intent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            }

            this.startActivityForResult(intent, REQUEST_CODE);
            Toast.makeText(this, R.string.toast, Toast.LENGTH_LONG).show();
        });

        alertDialog.setNegativeButton(R.string.cancel,
                (dialogInterface, i) -> this.mainBinding.swOpenEasyTouch.setChecked(false));
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (!checkFloatWindowPermission()) {
                showPromptingDialog();
            } else {
                ContextCompat.startForegroundService(MainActivity.this, new Intent(MainActivity.this, MyService.class));
            }
        } else {

            if (isMyServiceRunning(myService.getClass())) {
                EventBus.getDefault().post(new MessageEvent(Constants.MSG_STOP_SERVICE));
            }
        }
    }


    @Subscribe
    public void onEventMainThread(@NotNull MessageEvent event) {
        String msg = event.getMsg();

        if (msg.equals(Constants.MSG_TURN_OFF_SWITCH)) {
            this.mainBinding.swOpenEasyTouch.setChecked(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (!isMyServiceRunning(myService.getClass())) {
                this.mainBinding.swOpenEasyTouch.setChecked(false);
            }
        }
    }
}
