package com.example.ivosilva.nomi.nfc;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.util.Log;

import com.example.ivosilva.nomi.R;

public class NFCShareActivity extends AppCompatActivity {

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

        //  let's check if NFC is enabled!
        NfcManager manager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();

        if (adapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, R.string.nfc_not_supported, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        FragmentManager fm = getSupportFragmentManager();

        if (adapter.isEnabled()) {
            /*
            *   NFC SHOWS LOVE
            */
            if(active_fragment == null) {
                active_fragment = new NFCUpFragment();
                fm.beginTransaction().add(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "add");
            }
            else{
                active_fragment = new NFCUpFragment();
                fm.beginTransaction().replace(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "replace");
            }
        }
        else{
            /*
            *   NFC DOESN'T CARE ABOUT US
            */
            if(active_fragment == null){
                active_fragment = new NFCDownFragment();
                fm.beginTransaction().add(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_false", "add");
            }
            else{
                active_fragment = new NFCDownFragment();
                fm.beginTransaction().replace(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_false", "replace");
            }
        }
    }
}
