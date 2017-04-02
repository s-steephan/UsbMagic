package com.projects.steev.usbmagic;

import android.content.Context;

import it.gmariotti.cardslib.library.internal.CardExpand;

public class CustomExpandCard extends CardExpand {

    public CustomExpandCard(Context context) {
        super(context, R.layout.usb_torchlight_switch_layout);
    }
}