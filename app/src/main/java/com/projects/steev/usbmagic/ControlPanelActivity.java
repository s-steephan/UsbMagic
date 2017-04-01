package com.projects.steev.usbmagic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ControlPanelActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static ControlPanelActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        instance =  this;

        Switch flashlight_switch = (Switch) findViewById(R.id.flashlight_switch);
        flashlight_switch.setChecked(CoreUtilities.isTorchEnabled(this));
        CoreUtilities.setTextForSwitch(getString(R.string.flash_light_label), getString(R.string.flash_light_explanation), flashlight_switch);
        flashlight_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences settings;
        SharedPreferences.Editor editor = null;

        switch (buttonView.getId()){
            case R.id.flashlight_switch:
                String TORCH_PREFS_NAME = "flashlight_status";
                settings = getSharedPreferences(TORCH_PREFS_NAME, MODE_PRIVATE);
                editor = settings.edit();
                editor.putString("is_torch_enabled", String.valueOf(isChecked));
                break;

            default:
                break;
        }

        editor.apply();
    }


}
