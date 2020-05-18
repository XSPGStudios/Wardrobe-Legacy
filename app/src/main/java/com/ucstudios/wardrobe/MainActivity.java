package com.ucstudios.wardrobe;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String Name;

    BottomNavigationView bottomNavigation;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> Categories ;
    private InterstitialAd mInterstitialAd;
    GlobalBoolean globaladMob;

    public boolean flagLaundry;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseHelper = new DatabaseHelper(this);
        boolean InterrupedLaundry=false;

        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5285115076303860/5320925198");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        globaladMob = ((GlobalBoolean)this.getApplicationContext());
        if(!globaladMob.getTimerAdMob()){

        new CountDownTimer(150000, 1000) {

            public void onTick(long millisUntilFinished) {

                globaladMob.setmGlobalAdMob(true);
            }

            public void onFinish() {


                if(GlobalBoolean.isActivityVisible()){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    globaladMob.setmGlobalAdMob(false);

                }
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

                this.start();

            }
        }.start();}





        final Cursor data1 = mDatabaseHelper.getData();
        final ArrayList<String> TotalCategories = new ArrayList<>();

        while(data1.moveToNext()){

            TotalCategories.add(data1.getString(0));
        }

        ArrayList<String> Finitodilavare = new ArrayList<>();
        for(int i=0;i<TotalCategories.size();i++){
            Cursor c = mDatabaseHelper.GetWashed(TotalCategories.get(i));
            while(c.moveToNext()) {
                Finitodilavare.add(c.getString(0));

            }
        }
        for(int is=0;is<TotalCategories.size();is++){

            for(int check=0;check<Finitodilavare.size();check++){
                //get the table name
                mDatabaseHelper.toLaundry(TotalCategories.get(is),Finitodilavare.get(check));
                InterrupedLaundry=true;

            }
        }





        Cursor C = mDatabaseHelper.getData();
        int controlloiniziale=0;
        while (C.moveToNext()){
            controlloiniziale++;
        }
        if(controlloiniziale==0){
            FirstOpenDialog dialog = new FirstOpenDialog(this);
            dialog.show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(SmsFragment.newInstance("", ""));
        bottomNavigation.setSelectedItemId(R.id.navigation_sms);

        startService(new Intent(this, KillNotificationsService.class));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            } //master?
        });
        mInterstitialAd = new InterstitialAd(this); //vediamo se qualche bugggi l'ha già fatto
        mInterstitialAd.setAdUnitId("ca-app-pub-5285115076303860/5320925198");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return onOptionsItemSelected(item);
    }

    public void adClock ()  {
        int progress = 0;
        while (progress <= 5 ) {
            SystemClock.sleep(1000);
            Log.i("", " " + progress); //thx
            progress++;
        }
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {




                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance("", ""));

                            return true;
                        case R.id.navigation_sms:
                            openFragment(SmsFragment.newInstance("", ""));

                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalBoolean.activityResumed();
        Log.i("msg","Ripartito");
    }

    @Override
    protected void onPause() {
        super.onPause();
        GlobalBoolean.activityPaused();
        Log.i("msg","bloccato");
    }



}
