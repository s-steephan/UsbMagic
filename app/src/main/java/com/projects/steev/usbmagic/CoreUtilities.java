package com.projects.steev.usbmagic;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class CoreUtilities {

    public static boolean getTorchSwitchState(Activity activity, Boolean state){
        String TORCH_PREFS_NAME = "flashlight_settings";
        Boolean torch_switch_state;
        SharedPreferences settings = activity.getSharedPreferences(TORCH_PREFS_NAME, MODE_PRIVATE);
        if(state) {
            torch_switch_state = settings.getBoolean("is_torch_on_feature_enabled", false);
        } else {
            torch_switch_state = settings.getBoolean("is_torch_off_feature_enabled", false);
        }
        return torch_switch_state;
    }

    public static void saveFlashLightState(String state){
        String FLASHLIGHT_PREFS_NAME = "flashlight_state";
        SharedPreferences settings= ControlPanelActivity.instance.getSharedPreferences(FLASHLIGHT_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("current_flashlight_state", state);
        editor.apply();
    }

    public static boolean isFlashLightStateUp(Activity activity){
        String FLASHLIGHT_PREFS_NAME = "flashlight_state";
        SharedPreferences settings = activity.getSharedPreferences(FLASHLIGHT_PREFS_NAME, MODE_PRIVATE);
        String current_flashlight_state = settings.getString("current_flashlight_state", "");
        if (current_flashlight_state.equals("on")){
            return true;
        }
        return false;
    }

    public static void turnOnFlashLight(){
        try {
            if (!isFlashLightStateUp(ControlPanelActivity.instance)) {
                Camera camera;
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.release();
                saveFlashLightState("on");
                Toast.makeText(ControlPanelActivity.instance, "Flashlight is on", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(ControlPanelActivity.instance, "Unable to turn on flashlight", Toast.LENGTH_SHORT).show();
        }
    }

    public static void turnOffFlashLight(){
        try {
            if (isFlashLightStateUp(ControlPanelActivity.instance)) {
                Camera camera;
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.startPreview();
                camera.release();
                saveFlashLightState("off");
                Toast.makeText(ControlPanelActivity.instance, "Flashlight is off", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ControlPanelActivity.instance, "Unable to turn off flashlight", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setSingleLabelTextForSwitch(String explanation_text, Switch new_switch){
        SpannableString explanation_span = new SpannableString(explanation_text);
        explanation_span.setSpan(new AbsoluteSizeSpan(25), 0, explanation_text.length(), SPAN_INCLUSIVE_INCLUSIVE);
        explanation_span.setSpan(new ForegroundColorSpan(Color.parseColor("#585858")), 0, explanation_text.length(), 0);

        new_switch.setText(explanation_span);
    }
}
