package com.example.ivosilva.nomi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.fancybuttons.FancyButton;

public class NFCDownFragment extends Fragment {
    FancyButton btn_turn_on_nfc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfcdown, container, false);

        btn_turn_on_nfc = (FancyButton) view.findViewById(R.id.btn_turn_on_nfc);
        btn_turn_on_nfc.setOnClickListener(turnOnNFCHandler);

        return view;
    }

    View.OnClickListener turnOnNFCHandler = new View.OnClickListener() {
        public void onClick(View v) {

            // Open settings to turn on NFC

            startActivityForResult(new Intent(Settings.ACTION_NFC_SETTINGS), 0);
        }
    };

}
