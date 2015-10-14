package com.example.ivosilva.nomi.menu;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ivosilva.nomi.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // transition animation from login to NFC Activity
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_menu);

        FragmentManager fm = getSupportFragmentManager();
        MenuFragment menu_fragment = new MenuFragment();
        fm.beginTransaction().add(android.R.id.content, menu_fragment).commit();
    }
}
