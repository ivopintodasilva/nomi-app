package com.example.ivosilva.nomi.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ivosilva.nomi.R;

/**
 * Created by silva on 28-10-2015.
 */
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment settings_fragment = new SettingsFragment();
        fm.beginTransaction().add(android.R.id.content, settings_fragment).commit();
    }
}
