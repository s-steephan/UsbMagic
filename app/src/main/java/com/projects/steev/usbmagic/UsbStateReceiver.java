package com.projects.steev.usbmagic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;

public class UsbStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        boolean is_torch_enabled= CoreUtilities.isTorchEnabled(ControlPanelActivity.instance);

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            if(is_torch_enabled){
                CoreUtilities.changeFlashLightState(true);
            }
        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            if(is_torch_enabled){
                CoreUtilities.changeFlashLightState(false);
            }
        }
    }
}
