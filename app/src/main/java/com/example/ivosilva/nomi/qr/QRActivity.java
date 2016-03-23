package com.example.ivosilva.nomi.qr;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Iconify.with(new FontAwesomeModule());


        FragmentManager fm = getSupportFragmentManager();
        Fragment qr_main_fragment = new QRFragment();
        fm.beginTransaction().add(android.R.id.content, qr_main_fragment).commit();
    }

}
