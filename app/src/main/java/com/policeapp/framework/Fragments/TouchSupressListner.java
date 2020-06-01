package com.policeapp.framework.Fragments;

import android.view.MotionEvent;
import android.view.View;

public class TouchSupressListner implements View.OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}