package com.example.ivosilva.nomi;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NFCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // transition animation from login to NFC Activity
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_nfc);

        //  let's check if NFC is enabled!
        NfcManager manager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();

        if (adapter != null && adapter.isEnabled()) {

            /*
            *   NFC DOESN'T CARE ABOUT US
            */

            FragmentManager fm = getSupportFragmentManager();
            NFCUpFragment nfc_up_fragment = new NFCUpFragment();
            fm.beginTransaction().add(android.R.id.content, nfc_up_fragment).commit();
        }
        else{

            /*
            *   NFC SHOWS LOVE
            */

            FragmentManager fm = getSupportFragmentManager();
            NFCDownFragment nfc_down_fragment = new NFCDownFragment();
            fm.beginTransaction().add(android.R.id.content, nfc_down_fragment).commit();
        }


    }

}
