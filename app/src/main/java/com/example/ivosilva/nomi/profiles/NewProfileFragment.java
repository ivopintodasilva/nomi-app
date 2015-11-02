package com.example.ivosilva.nomi.profiles;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Arrays;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by silva on 30-10-2015.
 */
public class NewProfileFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_profile, container, false);


        Spinner lstColors = (Spinner) view.findViewById(R.id.new_profile_listColors);
        final String[] colors = new String[] { "BLUE", "BLACK", "RED", "GREEN", "WHITE" };
        final ArrayList<String> lista = new ArrayList<>();

        for(String str: colors)
            lista.add(str);

        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_nfc, lista);

        adapter.setDropDownViewResource(R.layout.spinner_nfc_list);
        lstColors.setAdapter(adapter);


        final FancyButton btnCreateProfile = (FancyButton) view.findViewById(R.id.btn_create_new_profile);
        btnCreateProfile.setOnClickListener(btnCreateProfileHandler);

        return view;
    }

    View.OnClickListener btnCreateProfileHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("NEWPROFILE", "Created new Profile");

            ///TODO create new profile
            Toast.makeText(getContext(), "Criaste um puto dum profile!", Toast.LENGTH_LONG).show();
        }
    };
}
