package com.example.ivosilva.nomi.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by silva on 19-10-2015.
 */
public class ProfileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profiles);
        Iconify.with(new FontAwesomeModule());


        FragmentManager fm = getSupportFragmentManager();
        Fragment profiles_list_fragment = new ProfileListFragment();
        fm.beginTransaction().add(android.R.id.content, profiles_list_fragment).commit();
    }


    public void new_profile_action(View view) {
        final View action = (View) findViewById(R.id.action_new_profile);
//        action_phone.setVisibility(action.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

        Log.d("NEWPROFILEACTION", "new_profile_button");
        Intent new_profile_intent = new Intent(this, NewProfileActivity.class);
        this.startActivity(new_profile_intent);
    }
}
