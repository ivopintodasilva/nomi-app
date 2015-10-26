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
        resumed = true;
        NFCEnabled();
        resumed = false;

    }

    protected void NFCEnabled() {

        /// TODO: Verificar se tem de se fazer add ou replace do fragment

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


            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            mNfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {

                /*
                 * (non-Javadoc)
                 * @see android.nfc.NfcAdapter.CreateNdefMessageCallback#createNdefMessage(android.nfc.NfcEvent)
                 */
                @Override
                public NdefMessage createNdefMessage(NfcEvent event) {
                    NdefRecord message = NdefRecord.createMime("text/plain", "5".getBytes());
                    return new NdefMessage(new NdefRecord[]{message});
                }

            }, this, this);

            if (resumed) {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            }

            if (active_fragment == null) {
                active_fragment = new NFCUpFragment();
                fm.beginTransaction().add(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "add");
            } else {
                active_fragment = new NFCUpFragment();
                fm.beginTransaction().replace(R.id.nfc_fragment_container, active_fragment).commit();
                Log.d("NFCEnabled_true", "replace");
            }
        } else {

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



    @Override
    protected void onNewIntent(Intent intent) {
		/*
		 * This method gets called, when a new Intent gets associated with the current activity instance.
		 * Instead of creating a new activity, onNewIntent will be called. For more information have a look
		 * at the documentation.
		 *
		 * In our case this method gets called, when the user attaches a Tag to the device.
		 */


        Parcelable[] rawMsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];

        byte[] payload = msg.getRecords()[0].getPayload();

        try{

            // THIS GETS THE BEAMED MESSAGE!!! WOHOOOO!
            Log.d("onNewIntent", new String(payload, 0, payload.length, "UTF-8"));}
        catch (UnsupportedEncodingException e){
            Log.e("onNewIntent", e.toString());
        }

    }
}
