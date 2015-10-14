package com.example.ivosilva.nomi.nfc;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class NFCPairFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nfcpair, container, false);

        // Settings for user avatar
        CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.avatar);
        circularImageView.setBorderWidth(0);

        return view;
    }

}
