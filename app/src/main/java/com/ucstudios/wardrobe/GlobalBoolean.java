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
    }

    private  boolean mGlobalAdMob;

    public boolean getTimerAdMob(){
        return mGlobalAdMob;
    }

    public void setmGlobalAdMob(boolean stimerflag){
        mGlobalAdMob=stimerflag;
    }

    private String mClickedCategory;

    public String getClickedCategory(){
        return mClickedCategory;
    }

    public void setmClickedCategory(String cliccata){
        mClickedCategory = cliccata;
    }


    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }






}
