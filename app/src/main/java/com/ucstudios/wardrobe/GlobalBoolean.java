package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("Registered")
public class GlobalBoolean extends Application {

    private boolean mGlobalBoolean;

    public boolean getTimerBoolean(){
        return mGlobalBoolean;
    }

    public void setmGlobalBoolean(boolean timerflag){
        mGlobalBoolean = timerflag;
    }}
