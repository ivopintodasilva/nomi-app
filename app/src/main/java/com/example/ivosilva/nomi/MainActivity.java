package com.example.ivosilva.nomi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ivosilva.nomi.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {
    public static final String LOGINPREFS = "LoginPrefs";
    public static final String USERID = "idKey";
    SharedPreferences shared_preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // shared_preferences(LOGINPREFS) stores the user id
        // so if it exists, there's no need to display the login screen
        shared_preferences = getSharedPreferences(LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(USERID, -1) != -1){
            Intent menu_intent = new Intent(this, MenuActivity.class);
            startActivity(menu_intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
