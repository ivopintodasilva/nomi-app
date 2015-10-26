package com.example.ivosilva.nomi.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import mehdi.sakout.fancybuttons.FancyButton;

public class NFCConnectedFragment extends Fragment {

    FancyButton btn_menu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nfc_connect, container, false);

        // Settings for user avatar
        CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.avatar);
        circularImageView.setBorderWidth(2);
        circularImageView.addShadow();

        btn_menu = (FancyButton) view.findViewById(R.id.btn_go_to_menu);
        btn_menu.setOnClickListener(menuHandler);

        return view;
    }

    View.OnClickListener menuHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(menu_intent);

        }
    };

}
