package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MainMenuFragments.HomeFragment;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MainMenuFragments.LogFragment;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MainMenuFragments.SettingsFragment;

import java.util.ArrayList;

public class MainNavActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private HomeFragment homeFragment;
    private LogFragment logFragment;
    private SettingsFragment settingsFragment;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAges = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.id_container, homeFragment);
                    ft.commit();
                    setTitle("Home");
                    return true;
                }

                case R.id.navigation_log: {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.id_container, logFragment);
                    ft.commit();
                    setTitle("Log");

                    return true;
                }

                case R.id.navigation_settings: {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.id_container, settingsFragment);
                    ft.commit();
                    setTitle("Settings");
                    return true;
                }

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        homeFragment = new HomeFragment();
        logFragment = new LogFragment();
        settingsFragment = new SettingsFragment();

        ft.add(R.id.id_container, homeFragment);
        ft.commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {

    }

}
