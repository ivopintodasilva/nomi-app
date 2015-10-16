package com.example.ivosilva.nomi.registration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuFragment;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // transition animation from login to NFC Activity
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_register);

        FragmentManager fm = getSupportFragmentManager();
        Fragment menu_fragment = new RegisterFragment();
        fm.beginTransaction().add(android.R.id.content, menu_fragment).commit();
    }
}
