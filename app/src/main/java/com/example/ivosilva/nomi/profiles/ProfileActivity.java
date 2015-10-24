package com.example.ivosilva.nomi.profiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by silva on 19-10-2015.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profiles);
        Iconify.with(new FontAwesomeModule());


        FragmentManager fm = getSupportFragmentManager();
        Fragment profiles_list_fragment = new ProfileListFragment();
        fm.beginTransaction().add(android.R.id.content, profiles_list_fragment).commit();
    }
}
