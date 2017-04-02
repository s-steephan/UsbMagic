package com.projects.steev.usbmagic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsbStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        boolean is_torch_on_enabled= CoreUtilities.getTorchSwitchState(ControlPanelActivity.instance,
                true);
        boolean is_torch_off_enabled= CoreUtilities.getTorchSwitchState(ControlPanelActivity.instance,
                false);

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            if(is_torch_on_enabled){
                CoreUtilities.turnOnFlashLight();
            }
        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            if(is_torch_off_enabled){
                CoreUtilities.turnOffFlashLight();
            }
        }
    }
}
