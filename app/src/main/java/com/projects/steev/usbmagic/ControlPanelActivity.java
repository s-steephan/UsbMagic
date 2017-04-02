package com.projects.steev.usbmagic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class ControlPanelActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    public static ControlPanelActivity instance = null;
    Boolean TORCH_ON_SWITCH_VALUE = true;
    Boolean TORCH_OFF_SWITCH_VALUE = false;
    Switch flashlight_on_switch, flashlight_off_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        instance =  this;

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Card card = new Card(this);
        CardHeader header = new CardHeader(this);
        header.setButtonExpandVisible(true);
        card.addCardHeader(header);

        header.setTitle(getResources().getString(R.string.flashlight_card_heater));
        card.setTitle(getResources().getString(R.string.flashlight_card_explanation));

        CardViewNative cardView = (CardViewNative) findViewById(R.id.usb_torch_cardview);

        CustomExpandCard expand = new CustomExpandCard(this);
        card.addCardExpand(expand);
        cardView.setCard(card);

        flashlight_on_switch = (Switch) findViewById(R.id.flashlight_on_switch);
        flashlight_off_switch = (Switch) findViewById(R.id.flashlight_off_switch);

        flashlight_on_switch.setChecked(CoreUtilities.getTorchSwitchState(this, TORCH_ON_SWITCH_VALUE));
        flashlight_off_switch.setChecked(CoreUtilities.getTorchSwitchState(this, TORCH_OFF_SWITCH_VALUE));

        CoreUtilities.setSingleLabelTextForSwitch(getString(R.string.flashlight_on_explanation),
                flashlight_on_switch);
        CoreUtilities.setSingleLabelTextForSwitch(getString(R.string.flashlight_off_explanation),
                flashlight_off_switch);

        flashlight_on_switch.setOnCheckedChangeListener(this);
        flashlight_off_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences settings;
        SharedPreferences.Editor editor = null;
        String TORCH_PREFS_NAME = "flashlight_settings";

        switch (buttonView.getId()){
            case R.id.flashlight_on_switch:
                settings = getSharedPreferences(TORCH_PREFS_NAME, MODE_PRIVATE);
                editor = settings.edit();
                editor.putBoolean("is_torch_on_feature_enabled", isChecked);
                if(!isChecked){
                    Boolean is_flashlight_on = CoreUtilities.isFlashLightStateUp(this);
                    if(is_flashlight_on){
                        CoreUtilities.turnOffFlashLight();
                    }

                    if(CoreUtilities.getTorchSwitchState(this, false)) {
                        editor.putBoolean("is_torch_off_feature_enabled", isChecked);
                        flashlight_off_switch.setChecked(TORCH_OFF_SWITCH_VALUE);
                    }
                }
                break;

            case R.id.flashlight_off_switch:
                settings = getSharedPreferences(TORCH_PREFS_NAME, MODE_PRIVATE);
                editor = settings.edit();
                editor.putBoolean("is_torch_off_feature_enabled", isChecked);

                if(!CoreUtilities.getTorchSwitchState(this, true)){
                    editor.putBoolean("is_torch_on_feature_enabled", isChecked);
                    flashlight_on_switch.setChecked(TORCH_ON_SWITCH_VALUE);
                }
                break;

            default:
                break;
        }

        editor.apply();
    }


}
