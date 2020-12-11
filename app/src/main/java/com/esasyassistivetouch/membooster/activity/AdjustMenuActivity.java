package com.esasyassistivetouch.membooster.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.esasyassistivetouch.membooster.R;
import com.esasyassistivetouch.membooster.ulti.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdjustMenuActivity extends AppCompatActivity {
    private static final int SELECT_NONE = 0;
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

    private static final int ITEM_AT_POSITION_1 = 1;
    private static final int ITEM_AT_POSITION_2 = 2;
    private static final int ITEM_AT_POSITION_3 = 3;
    private static final int ITEM_AT_POSITION_4 = 4;
    private static final int ITEM_AT_POSITION_5 = 5;
    private static final int ITEM_AT_POSITION_6 = 6;
    private static final int ITEM_AT_POSITION_7 = 7;
    private static final int ITEM_AT_POSITION_8 = 8;

    private static final String BASE_FUNCTION_KEY = "k_setting_function_for_item_";

    @BindView(R.id.iv_pick_setting_function_1)
    ImageView ivPickSettingFunction1;
    @BindView(R.id.iv_pick_setting_function_2)
    ImageView ivPickSettingFunction2;
    @BindView(R.id.iv_pick_setting_function_3)
    ImageView ivPickSettingFunction3;
    @BindView(R.id.iv_pick_setting_function_4)
    ImageView ivPickSettingFunction4;
    @BindView(R.id.iv_pick_setting_function_5)
    ImageView ivPickSettingFunction5;
    @BindView(R.id.iv_pick_setting_function_6)
    ImageView ivPickSettingFunction6;
    @BindView(R.id.iv_pick_setting_function_7)
    ImageView ivPickSettingFunction7;
    @BindView(R.id.iv_pick_setting_function_8)
    ImageView ivPickSettingFunction8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_menu);
        ButterKnife.bind(this);
        getCurrentFunctionItem();

    }

    @OnClick({R.id.iv_pick_setting_function_1
            , R.id.iv_pick_setting_function_2
            , R.id.iv_pick_setting_function_3
            , R.id.iv_pick_setting_function_4
            , R.id.iv_pick_setting_function_5
            , R.id.iv_pick_setting_function_6
            , R.id.iv_pick_setting_function_7
            , R.id.iv_pick_setting_function_8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pick_setting_function_1:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_1);
                break;
            case R.id.iv_pick_setting_function_2:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_2);
                break;
            case R.id.iv_pick_setting_function_3:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_3);
                break;
            case R.id.iv_pick_setting_function_4:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_4);
                break;
            case R.id.iv_pick_setting_function_5:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_5);
                break;
            case R.id.iv_pick_setting_function_6:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_6);
                break;
            case R.id.iv_pick_setting_function_7:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_7);
                break;
            case R.id.iv_pick_setting_function_8:
                createMenuAllSettingFunctionDialog(ITEM_AT_POSITION_8);
                break;
        }
    }

    private void getCurrentFunctionItem() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        int functionForPosition1 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, SELECT_CONTROL_WIFI);
        int functionForPosition2 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, SELECT_CONTROL_BLUE_TOOTH);
        int functionForPosition3 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, SELECT_CONTROL_LOCATION);
        int functionForPosition4 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, SELECT_DATA_SUMMARY);
        int functionForPosition5 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, SELECT_ACTIVE_AIRPLANE);
        int functionForPosition6 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, SELECT_VOLUME_DOWN);
        int functionForPosition7 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, SELECT_VOLUME_UP);
        int functionForPosition8 = sharedPreferences.getInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, SELECT_CONTROL_SOUND_MODE);
        updateItemIconByFunctionType(ivPickSettingFunction1, functionForPosition1);
        updateItemIconByFunctionType(ivPickSettingFunction2, functionForPosition2);
        updateItemIconByFunctionType(ivPickSettingFunction3, functionForPosition3);
        updateItemIconByFunctionType(ivPickSettingFunction4, functionForPosition4);
        updateItemIconByFunctionType(ivPickSettingFunction5, functionForPosition5);
        updateItemIconByFunctionType(ivPickSettingFunction6, functionForPosition6);
        updateItemIconByFunctionType(ivPickSettingFunction7, functionForPosition7);
        updateItemIconByFunctionType(ivPickSettingFunction8, functionForPosition8);

        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 1; i <= 8; i++) {
            editor.putInt(BASE_FUNCTION_KEY + i, i);
            editor.apply();
        }

    }


    private void createMenuAllSettingFunctionDialog(int itemPosition) {

        Dialog allMenuItemDialog = new Dialog(this);
        allMenuItemDialog.setContentView(R.layout.dialog_all_setting_function);

        final LinearLayout llItemFunctionWifi = allMenuItemDialog.findViewById(R.id.ll_item_function_control_wifi);
        final LinearLayout llItemFunctionBlueTooth = allMenuItemDialog.findViewById(R.id.ll_item_function_control_blue_tooth);
        final LinearLayout llItemFunctionLocation = allMenuItemDialog.findViewById(R.id.ll_item_function_control_location);
        final LinearLayout llItemFunctionSummary = allMenuItemDialog.findViewById(R.id.ll_item_function_open_summary);
        final LinearLayout llItemFunctionAirplane = allMenuItemDialog.findViewById(R.id.ll_item_function_airplane);
        final LinearLayout llItemFunctionVolumeDown = allMenuItemDialog.findViewById(R.id.ll_item_function_volume_down);
        final LinearLayout llItemFunctionVolumeUp = allMenuItemDialog.findViewById(R.id.ll_item_function_volume_up);
        final LinearLayout llItemFunctionSoundMode = allMenuItemDialog.findViewById(R.id.ll_item_function_control_sound_mode);
        final LinearLayout llItemFunctionRotationMode = allMenuItemDialog.findViewById(R.id.ll_item_control_rotation_mode);
        final LinearLayout llItemFunctionActiveFlash = allMenuItemDialog.findViewById(R.id.ll_item_active_flash);
        final LinearLayout llItemFunctionScreenShot = allMenuItemDialog.findViewById(R.id.ll_item_screen_shot);
        final LinearLayout llItemFunctionVisualPowerButton = allMenuItemDialog.findViewById(R.id.ll_item_visual_power_button);
        final LinearLayout llItemFunctionNone = allMenuItemDialog.findViewById(R.id.ll_item_no_function);

        llItemFunctionWifi.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_CONTROL_WIFI)) {
                onPickControlWifi(itemPosition);
            } else {
                swapFunction(itemPosition, SELECT_CONTROL_WIFI);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionBlueTooth.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_CONTROL_BLUE_TOOTH)) {
                onPickControlBlueTooth(itemPosition);
            } else {
                swapFunction(itemPosition, SELECT_CONTROL_BLUE_TOOTH);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionLocation.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_CONTROL_LOCATION)) {
                onPickFunctionToPosition(itemPosition, SELECT_CONTROL_LOCATION);
            } else {
                swapFunction(itemPosition, SELECT_CONTROL_LOCATION);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionSummary.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_DATA_SUMMARY)) {
                onPickFunctionToPosition(itemPosition, SELECT_DATA_SUMMARY);
            } else {
                swapFunction(itemPosition, SELECT_DATA_SUMMARY);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionAirplane.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_ACTIVE_AIRPLANE)) {
                onPickFunctionToPosition(itemPosition, SELECT_ACTIVE_AIRPLANE);
            } else {
                swapFunction(itemPosition, SELECT_ACTIVE_AIRPLANE);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionVolumeDown.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_VOLUME_DOWN)) {
                onPickFunctionToPosition(itemPosition, SELECT_VOLUME_DOWN);
            } else {
                swapFunction(itemPosition, SELECT_VOLUME_DOWN);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionVolumeUp.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_VOLUME_UP)) {
                onPickFunctionToPosition(itemPosition, SELECT_VOLUME_UP);
            } else {
                swapFunction(itemPosition, SELECT_VOLUME_UP);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionSoundMode.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_CONTROL_SOUND_MODE)) {
                onPickFunctionToPosition(itemPosition, SELECT_CONTROL_SOUND_MODE);
            } else {
                swapFunction(itemPosition, SELECT_CONTROL_SOUND_MODE);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionRotationMode.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_CONTROL_ROTATION_MODE)) {
                onPickFunctionToPosition(itemPosition, SELECT_CONTROL_ROTATION_MODE);
            } else {
                swapFunction(itemPosition, SELECT_CONTROL_ROTATION_MODE);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionActiveFlash.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_ACTIVE_FLASH)) {
                onPickFunctionToPosition(itemPosition, SELECT_ACTIVE_FLASH);
            } else {
                swapFunction(itemPosition, SELECT_ACTIVE_FLASH);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionScreenShot.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_SCREEN_SHOT)) {
                onPickFunctionToPosition(itemPosition, SELECT_SCREEN_SHOT);
            } else {
                swapFunction(itemPosition, SELECT_SCREEN_SHOT);
            }
            allMenuItemDialog.dismiss();
        });
        llItemFunctionVisualPowerButton.setOnClickListener(v -> {
            if (isNoPick(itemPosition, SELECT_VISUAL_POWER_BUTTON)) {
                onPickFunctionToPosition(itemPosition, SELECT_VISUAL_POWER_BUTTON);
            } else {
                swapFunction(itemPosition, SELECT_VISUAL_POWER_BUTTON);
            }
            allMenuItemDialog.dismiss();
        });

        llItemFunctionNone.setOnClickListener(v -> onPickFunctionToPosition(itemPosition,SELECT_NONE));
        allMenuItemDialog.show();

    }

//    private void createFunctionUsedInAnotherPositionDialog(int itemPosition, int functionType) {
//        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(AdjustMenuActivity.this);
//        builderDialog.setTitle(R.string.item_function_alert_title)
//                .setMessage(R.string.item_function_alert_message)
//                .setPositiveButton(R.string.all_dialog_yes, (dialog, which) -> swapFunction(itemPosition, functionType))
//                .setNegativeButton(R.string.all_dialog_no, (dialog, which) -> dialog.dismiss());
//        builderDialog.create().show();
//    }

    private void swapFunction(int itemPosition, int functionType) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        int[] functionMap = new int[9];
        for (int i = 1; i <= 8; i++) {
            functionMap[i] = sharedPreferences.getInt(BASE_FUNCTION_KEY + i, i);
        }
        int currentPosition = 0;
        int currentFunctionType;

        for (int i = 1; i <= 8; i++) {

            if (functionMap[i] == functionType) {
                currentPosition = i;
                break;
            }
        }
        currentFunctionType = functionMap[itemPosition];
        onPickFunctionToPosition(currentPosition, currentFunctionType);
        onPickFunctionToPosition(itemPosition, functionType);

    }

    private void onPickFunctionToPosition(int itemPosition, int functionType) {
        switch (functionType) {
            case SELECT_NONE:
                onPickNothing(itemPosition);
            case SELECT_CONTROL_WIFI:
                onPickControlWifi(itemPosition);
                break;
            case SELECT_CONTROL_BLUE_TOOTH:
                onPickControlBlueTooth(itemPosition);
                break;
            case SELECT_CONTROL_LOCATION:
                onPickControlLocation(itemPosition);
                break;
            case SELECT_DATA_SUMMARY:
                onPickOpenDataSummary(itemPosition);
                break;
            case SELECT_ACTIVE_AIRPLANE:
                onPickControlAirPlane(itemPosition);
                break;
            case SELECT_VOLUME_DOWN:
                onPickControlVolumeDown(itemPosition);
                break;
            case SELECT_VOLUME_UP:
                onPickControlVolumeUp(itemPosition);
                break;
            case SELECT_CONTROL_SOUND_MODE:
                onPickControlSoundMode(itemPosition);
                break;
            case SELECT_CONTROL_ROTATION_MODE:
                onPickControlRotationMode(itemPosition);
                break;
            case SELECT_ACTIVE_FLASH:
                onPickActiveFlash(itemPosition);
                break;
            case SELECT_SCREEN_SHOT:
                onPickScreenShot(itemPosition);
                break;
            case SELECT_VISUAL_POWER_BUTTON:
                onPickVisualPowerButton(itemPosition);
                break;
            default:
                break;
        }

    }

    private void onPickNothing(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_add);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_NONE);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_add);
                break;
        }
    }

    private void onPickVisualPowerButton(int itemPosition) {


        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_power_settings);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_VISUAL_POWER_BUTTON);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_power_settings);
                break;
        }

    }

    private boolean isNoPick(int itemPosition, int functionType) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        String functionKey = "k_setting_function_for_item_";
        int[] functionMap = new int[9];
        for (int i = 1; i <= 8; i++) {
            functionMap[i] = sharedPreferences.getInt(functionKey + i, i);
        }
        for (int i = 1; i <= 8; i++) {
            if (functionMap[i] == functionType && i != itemPosition) {
                return false;
            }
        }

        return true;
    }

    private void onPickScreenShot(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_screen_shot);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_screen_shot);
                break;
        }

    }

    private void onPickActiveFlash(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_ACTIVE_FLASH);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_highlight_black);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_SCREEN_SHOT);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_highlight_black);
                break;
        }

    }

    private void onPickControlSoundMode(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case 2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_SOUND_MODE);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
        }


    }

    private void onPickControlVolumeUp(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case 2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_VOLUME_UP);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
        }

    }

    private void onPickControlVolumeDown(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_VOLUME_DOWN);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
        }

    }

    private void onPickControlAirPlane(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_ACTIVE_AIRPLANE);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
        }

    }

    private void onPickOpenDataSummary(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_DATA_SUMMARY);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
        }

    }

    private void onPickControlBlueTooth(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_CONTROL_BLUE_TOOTH);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
        }

    }

    private void onPickControlWifi(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_CONTROL_WIFI);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
        }

    }

    private void onPickControlLocation(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_CONTROL_LOCATION);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
        }

    }

    private void onPickControlRotationMode(int itemPosition) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.K_ADJUST_SETTING_FUNCTION, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (itemPosition) {
            case ITEM_AT_POSITION_1:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_1, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction1.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_2:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_2, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction2.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_3:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_3, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction3.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_4:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_4, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction4.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_5:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_5, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction5.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_6:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_6, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction6.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_7:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_7, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction7.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case ITEM_AT_POSITION_8:
                editor.putInt(Constants.K_SETTING_FUNCTION_FOR_ITEM_8, Constants.K_FUNCTION_CONTROL_ROTATION_MODE);
                editor.apply();
                ivPickSettingFunction8.setImageResource(R.drawable.ic_screen_rotation);
                break;
        }

    }

    private void updateItemIconByFunctionType(ImageView view, int functionType) {
        switch (functionType) {
            case SELECT_NONE:
                view.setImageResource(R.drawable.ic_add);
                break;
            case SELECT_CONTROL_WIFI:
                view.setImageResource(R.drawable.ic_wifi_black_24dp);
                break;
            case SELECT_CONTROL_BLUE_TOOTH:
                view.setImageResource(R.drawable.ic_bluetooth_black_24dp);
                break;
            case SELECT_CONTROL_LOCATION:
                view.setImageResource(R.drawable.ic_location_on_black_24dp);
                break;
            case SELECT_DATA_SUMMARY:
                view.setImageResource(R.drawable.ic_swap_vert_black_24dp);
                break;
            case SELECT_ACTIVE_AIRPLANE:
                view.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case SELECT_VOLUME_DOWN:
                view.setImageResource(R.drawable.ic_volume_down_black_24dp);
                break;
            case SELECT_VOLUME_UP:
                view.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;
            case SELECT_CONTROL_SOUND_MODE:
                view.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                break;
            case SELECT_CONTROL_ROTATION_MODE:
                view.setImageResource(R.drawable.ic_screen_rotation);
                break;
            case SELECT_ACTIVE_FLASH:
                view.setImageResource(R.drawable.ic_highlight_black);
                break;
            case SELECT_SCREEN_SHOT:
                view.setImageResource(R.drawable.ic_screen_shot);
                break;
            case SELECT_VISUAL_POWER_BUTTON:
                view.setImageResource(R.drawable.ic_power_settings);
                break;
        }
    }

}
