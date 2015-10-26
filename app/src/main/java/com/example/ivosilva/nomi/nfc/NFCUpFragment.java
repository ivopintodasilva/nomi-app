package com.example.ivosilva.nomi.nfc;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ivosilva.nomi.R;

public class NFCUpFragment extends Fragment {
    ImageView nfc_up_img;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfcup, container, false);

        nfc_up_img = (ImageView) view.findViewById(R.id.gito_image);

        // only for testing purposes, has to be removed!
        nfc_up_img.setOnClickListener(temporaryHandler);

        return view;
    }


    // to be removed!
    View.OnClickListener temporaryHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent nfc_connected_intent = new Intent(getActivity(), NFCConnectedActivity.class);
            getActivity().startActivity(nfc_connected_intent);

        }
    };


}
