package com.ucstudios.wardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
            .withFullScreen()
                .withTargetActivity(MainActivity.class)
                    .withSplashTimeOut(5000)
                        .withBackgroundColor(Color.parseColor("#1a1b29"))
                            .withLogo(R.drawable.locker);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }
}
//commit test