package com.projects.steev.usbmagic;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class CoreUtilities {

    public static boolean isTorchEnabled(Activity activity){
        String TORCH_PREFS_NAME = "flashlight_status";
        SharedPreferences settings = activity.getSharedPreferences(TORCH_PREFS_NAME, MODE_PRIVATE);
        String is_torch_enabled = settings.getString("is_torch_enabled", "");
        return Boolean.parseBoolean(is_torch_enabled);
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

    public static void changeFlashLightState(Boolean state){
        try {
            Camera camera;
            if (state && !isFlashLightStateUp(ControlPanelActivity.instance)) {
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.release();
                saveFlashLightState("on");
                Toast.makeText(ControlPanelActivity.instance, "Flashlight is on", Toast.LENGTH_SHORT).show();

            } else if (isFlashLightStateUp(ControlPanelActivity.instance)){
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
        } catch (Exception e){
            
        }
    }

    public static void setTextForSwitch(String title_text, String explanation_text, Switch new_switch){
        SpannableString title_span = new SpannableString(title_text);
        title_span.setSpan(new AbsoluteSizeSpan(35), 0, title_text.length(), SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString explanation_span = new SpannableString(explanation_text);
        explanation_span.setSpan(new AbsoluteSizeSpan(25), 0, explanation_text.length(), SPAN_INCLUSIVE_INCLUSIVE);
        explanation_span.setSpan(new ForegroundColorSpan(Color.parseColor("#585858")), 0, explanation_text.length(), 0);

        CharSequence finalText = TextUtils.concat(title_span, "\n", explanation_span);
        new_switch.setText(finalText);
    }
}
