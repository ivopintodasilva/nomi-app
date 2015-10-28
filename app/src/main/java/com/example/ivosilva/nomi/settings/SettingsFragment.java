package com.example.ivosilva.nomi.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;

import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by silva on 28-10-2015.
 */
public class SettingsFragment extends Fragment {
    FancyButton btn_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        btn_logout = (FancyButton) view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(btn_logoutHandler);

        return view;
    }

    View.OnClickListener btn_logoutHandler = new View.OnClickListener() {
        public void onClick(View v) {
            SharedPreferences settings = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(LoginFragment.USERID);
            editor.commit();

            Intent login_fragment = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(login_fragment);

            getActivity().finish();
        }
    };

}
