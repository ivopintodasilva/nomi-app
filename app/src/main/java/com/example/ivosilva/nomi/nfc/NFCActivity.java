package com.example.ivosilva.nomi.nfc;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ivosilva.nomi.R;

public class NFCActivity extends AppCompatActivity {

    Fragment active_fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // transition animation from login to NFC Activity
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_nfc);

        NFCEnabled();

    }

    @Override
    protected void onResume() {
        super.onResume();

        NFCEnabled();
    }

    protected void NFCEnabled() {

        /// TODO: Verificar se tem de se fazer add ou replace do fragment

        //  let's check if NFC is enabled!
        NfcManager manager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();

        FragmentManager fm = getSupportFragmentManager();

        if (adapter != null && adapter.isEnabled()) {

            /*
            *   NFC DOESN'T CARE ABOUT US
            */

            active_fragment = new NFCUpFragment();

            if(active_fragment == null) {
                fm.beginTransaction().add(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "add");
            }
            else{
                fm.beginTransaction().replace(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "replace");
            }
        }
        else{

            /*
            *   NFC SHOWS LOVE
            */

            active_fragment = new NFCDownFragment();

            if(active_fragment == null){
                fm.beginTransaction().add(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_false", "add");
            }
            else{
                fm.beginTransaction().replace(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_false", "replace");
            }
        }
    }
}
