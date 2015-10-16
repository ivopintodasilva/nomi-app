package com.example.ivosilva.nomi.registration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;

import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterFragment extends Fragment {

    FancyButton btn_register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btn_register = (FancyButton) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(registerHandler);

        return view;
    }

    View.OnClickListener registerHandler = new View.OnClickListener() {
        public void onClick(View v) {

            /*
            *
            *   Insert registration logic!
            *
            */

            Intent register_intent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(register_intent);

        }
    };

}
