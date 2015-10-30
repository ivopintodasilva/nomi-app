package com.example.ivosilva.nomi.profiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by silva on 30-10-2015.
 */
public class NewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_profile_create);
        Iconify.with(new FontAwesomeModule());


        FragmentManager fm = getSupportFragmentManager();
        Fragment newprofile_fragment = new NewProfileFragment();
        fm.beginTransaction().add(android.R.id.content, newprofile_fragment).commit();
    }
}
