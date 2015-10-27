package com.example.ivosilva.nomi.profiles;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by silva on 19-10-2015.
 */
public class ProfileDetailsFragment extends Fragment {

    TextView profile_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        Log.d("ProfileDetailsFragmentP", getArguments().getString("PROFILE", ""));
        Log.d("ProfileDetailsFragmentA", getArguments().getString("ATTRIBUTES", ""));

        try {
            JSONObject profile = new JSONObject(getArguments().getString("PROFILE", ""));
            profile_name = (TextView) view.findViewById(R.id.profile_name);
            profile_name.setText(profile.getString("name"));

            JSONObject contacts = new JSONObject(getArguments().getString("ATTRIBUTES", ""));

            /*  to organize the profile contacts in the view  */
            int i = 0;
            String key;
            Iterator<String> it = contacts.keys();
            while(it.hasNext()){
                key = it.next();
                Log.d("KEYS", key);
//                Log.d("i", Integer.toString(i));

                switch (key){
                    case "NUMBER":
                        IconTextView number = (IconTextView) view.findViewById(R.id.profile_phone);
                        number.setText("{fa-phone}  " + contacts.getString(key));
                        number.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) number.getLayoutParams();
                            params.topMargin = i*120;
                            number.setLayoutParams(params);
                        }
                        number.setOnClickListener(numberHandler);
                        break;
                    case "EMAIL":
                        IconTextView email = (IconTextView) view.findViewById(R.id.profile_email);
                        email.setText("{fa-envelope-o}  " + contacts.getString(key));
                        email.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) email.getLayoutParams();
                            params.topMargin = i*120;
                            email.setLayoutParams(params);
                        }
                        break;
                    case "FACEBOOK":
                        IconTextView facebook = (IconTextView) view.findViewById(R.id.profile_facebook);
                        facebook.setText("{fa-facebook}  " + contacts.getString(key));
                        facebook.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) facebook.getLayoutParams();
                            params.topMargin = i*120;
                            facebook.setLayoutParams(params);
                        }
                        break;
                    case "INSTAGRAM":
                        IconTextView instagram = (IconTextView) view.findViewById(R.id.profile_instagram);
                        instagram.setText("{fa-instagram}  " + contacts.getString(key));
                        instagram.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) instagram.getLayoutParams();
                            params.topMargin = i*120;
                            instagram.setLayoutParams(params);
                        }
                        break;
                    case "LINKEDIN":
                        IconTextView linkedin = (IconTextView) view.findViewById(R.id.profile_linkedin);
                        linkedin.setText("{fa-linkedin}  " + contacts.getString(key));
                        linkedin.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linkedin.getLayoutParams();
                            params.topMargin = i*120;
                            linkedin.setLayoutParams(params);
                        }
                        break;
                    case "GOOGLE":
                        IconTextView google = (IconTextView) view.findViewById(R.id.profile_googleplus);
                        google.setText("{fa-google-plus}  " + contacts.getString(key));
                        google.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) google.getLayoutParams();
                            params.topMargin = i*120;
                            google.setLayoutParams(params);
                        }
                        break;
                    case "TWITTER":
                        IconTextView twitter = (IconTextView) view.findViewById(R.id.profile_twitter);
                        twitter.setText("{fa-twitter}  @" + contacts.getString(key));
                        twitter.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) twitter.getLayoutParams();
                            params.topMargin = i*120;
                            twitter.setLayoutParams(params);
                        }
                        break;
                }

            }
        }
        catch (org.json.JSONException e){
            Log.d("onCreateView", "No data");
        }

        return view;
    }

    View.OnClickListener numberHandler = new View.OnClickListener() {
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle(getContext().getString(R.string.attribute_dialog_title));

            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_profile_detail_edit, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("DIALOG","YES");
                        /// TODO making change the value
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("DIALOG", "CANCEL");
                        dialog.cancel();
                    }
                });

            builder.create();
//            alert.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(getContext().getColor(R.color.colorPrimaryDark));
//            alert.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundColor(getContext().getColor(R.color.colorPrimaryDark));

            builder.show();
        }
    };

}
