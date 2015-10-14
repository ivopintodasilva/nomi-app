package com.example.ivosilva.nomi.login;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginFragment extends Fragment {
    FancyButton btn_login;
    FancyButton btn_register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = (FancyButton) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(loginHandler);

        btn_register = (FancyButton) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(registerHandler);

        return view;
    }


    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Insert login logic!
            *
            */
            EditText username = (EditText) getActivity().findViewById(R.id.username);
            EditText password = (EditText) getActivity().findViewById(R.id.password);




            Intent MenuIntent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(MenuIntent);
        }
    };


    View.OnClickListener registerHandler = new View.OnClickListener() {
        public void onClick(View v) {

            /*
            *
            *   Insert registration logic!
            *
            */

        }
    };

}
