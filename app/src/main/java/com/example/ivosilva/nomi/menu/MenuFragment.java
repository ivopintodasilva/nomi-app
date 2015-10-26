package com.example.ivosilva.nomi.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.nfc.NFCShareActivity;
import com.example.ivosilva.nomi.contacts.ContactActivity;
import com.example.ivosilva.nomi.profiles.ProfileActivity;

import mehdi.sakout.fancybuttons.FancyButton;

public class MenuFragment extends Fragment {
    FancyButton btn_share;
    FancyButton btn_contacts;
    FancyButton btn_profiles;
    FancyButton btn_settings;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        btn_share = (FancyButton) view.findViewById(R.id.btn_share);
        btn_contacts = (FancyButton) view.findViewById(R.id.btn_contacts);
        btn_profiles = (FancyButton) view.findViewById(R.id.btn_profiles);
        btn_settings = (FancyButton) view.findViewById(R.id.btn_settings);

        btn_share.setOnClickListener(shareHandler);
        btn_contacts.setOnClickListener(contactsHandler);
        btn_profiles.setOnClickListener(profilesHandler);
        btn_settings.setOnClickListener(settingsHandler);


        return view;
    }

    View.OnClickListener shareHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Insert login logic!
            *
            */


            Intent NFCIntent = new Intent(getActivity(), NFCShareActivity.class);
            getActivity().startActivity(NFCIntent);
        }
    };

    View.OnClickListener contactsHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Connect to Contacts activity
            *
            */

            Intent contacts_intent = new Intent(getActivity(), ContactActivity.class);
            getActivity().startActivity(contacts_intent);

        }
    };

    View.OnClickListener profilesHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Connect to Profiles activity
            *
            */

            Intent profile_intent = new Intent(getActivity(), ProfileActivity.class);
            getActivity().startActivity(profile_intent);

        }
    };

    View.OnClickListener settingsHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Connect to settings activity
            *
            */

        }
    };

}
