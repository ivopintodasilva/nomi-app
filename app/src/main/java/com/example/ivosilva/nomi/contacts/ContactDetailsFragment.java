package com.example.ivosilva.nomi.contacts;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONObject;

import java.util.Iterator;

public class ContactDetailsFragment extends Fragment {

    TextView name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);

        Log.d("ContactDetailsFragment", getArguments().getString("PROFILE", ""));
        Log.d("ContactDetailsFragment", getArguments().getString("CONTACTS", ""));

        try {
            JSONObject profile = new JSONObject(getArguments().getString("PROFILE", ""));
            name = (TextView) view.findViewById(R.id.name);
            name.setText(profile.getString("name"));

            JSONObject contacts = new JSONObject(getArguments().getString("CONTACTS", ""));

            /*  to organize the contacts in the view  */
            int i = 0;
            String key;
            Iterator<String> it = contacts.keys();
            while(it.hasNext()){
                key = it.next();
                Log.d("KEYS", key);
                Log.d("i", Integer.toString(i));

                switch (key){
                    case "NUMBER":
                        IconTextView number = (IconTextView) view.findViewById(R.id.phone);
                        number.setText("{fa-phone}  " + contacts.getString(key));
                        number.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) number.getLayoutParams();
                            params.topMargin = i*120;
                            number.setLayoutParams(params);
                        }
                        continue;
                    case "EMAIL":
                        IconTextView email = (IconTextView) view.findViewById(R.id.email);
                        email.setText("{fa-envelope-o}  " + contacts.getString(key));
                        email.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) email.getLayoutParams();
                            params.topMargin = i*120;
                            email.setLayoutParams(params);

                        }
                        continue;
                    case "FACEBOOK":
                        IconTextView facebook = (IconTextView) view.findViewById(R.id.facebook);
                        facebook.setText("{fa-facebook}  " + contacts.getString(key));
                        facebook.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) facebook.getLayoutParams();
                            params.topMargin = i*120;
                            facebook.setLayoutParams(params);
                        }
                        continue;
                    case "INSTAGRAM":
                        IconTextView instagram = (IconTextView) view.findViewById(R.id.instagram);
                        instagram.setText("{fa-instagram}  " + contacts.getString(key));
                        instagram.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) instagram.getLayoutParams();
                            params.topMargin = i*120;
                            instagram.setLayoutParams(params);
                        }
                        continue;
                    case "LINKEDIN":
                        IconTextView linkedin = (IconTextView) view.findViewById(R.id.linkedin);
                        linkedin.setText("{fa-linkedin}  " + contacts.getString(key));
                        linkedin.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linkedin.getLayoutParams();
                            params.topMargin = i*120;
                            linkedin.setLayoutParams(params);
                        }
                        continue;
                    case "GOOGLE":
                        IconTextView google = (IconTextView) view.findViewById(R.id.googleplus);
                        google.setText("{fa-google-plus}  " + contacts.getString(key));
                        google.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) google.getLayoutParams();
                            params.topMargin = i*120;
                            google.setLayoutParams(params);
                        }
                        continue;
                }


            }
        }
        catch (org.json.JSONException e){
            Log.d("onCreateView", "No data");
        }
        return view;
    }


}
