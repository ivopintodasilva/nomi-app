package com.example.ivosilva.nomi.contacts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ivosilva.nomi.R;

public class ContactDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //Log.d("DETAILS", getIntent().getSerializableExtra("PROFILE").toString());
        //Log.d("CONTACTS", getIntent().getSerializableExtra("CONTACTS").toString());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment contact_details = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString("PROFILE", getIntent().getSerializableExtra("PROFILE").toString());
        args.putString("CONTACTS", getIntent().getSerializableExtra("CONTACTS").toString());
        contact_details.setArguments(args);
        ft.add(android.R.id.content, contact_details).commit();

    }

}
