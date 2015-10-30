package com.example.ivosilva.nomi.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ivosilva.nomi.R;

/**
 * Created by silva on 24-10-2015.
 */
public class ProfileDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        //Log.d("DETAILS", getIntent().getSerializableExtra("PROFILE").toString());
        //Log.d("CONTACTS", getIntent().getSerializableExtra("CONTACTS").toString());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment profile_details = new ProfileDetailsFragment();
        Bundle args = new Bundle();
        args.putString("PROFILE", getIntent().getSerializableExtra("PROFILE").toString());
        args.putString("ATTRIBUTES", getIntent().getSerializableExtra("ATTRIBUTES").toString());
        profile_details.setArguments(args);
        ft.add(android.R.id.content, profile_details).commit();

    }

    public void profile_add_phone(View view) {
        final View action_phone = (View) findViewById(R.id.action_phone);
        action_phone.setVisibility(action_phone.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

        Log.d("FLOTINGACTION", "profile_add_phone");
        Intent new_profile_intent = new Intent(this, NewProfileActivity.class);
        this.startActivity(new_profile_intent);
    }
}
