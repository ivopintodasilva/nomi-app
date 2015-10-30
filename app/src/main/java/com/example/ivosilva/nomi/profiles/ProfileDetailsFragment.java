package com.example.ivosilva.nomi.profiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by silva on 19-10-2015.
 */
public class ProfileDetailsFragment extends Fragment {

    TextView profile_name;
    private IconTextView number;
    private IconTextView email;
    private IconTextView facebook;
    private IconTextView instagram;
    private IconTextView linkedin;
    private IconTextView googleplus;
    private IconTextView twitter;


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

        // substitute by real user image url
        CircularImageView imageView = (CircularImageView) view.findViewById(R.id.profile_photo);
        Glide.with(this).load("https://scontent-mxp1-1.xx.fbcdn.net/hphotos-xfl1/t31.0-8/1268990_10200674928977262_714053855_o.jpg").into(imageView);


        Log.d("ProfileDetailsFragmentP", getArguments().getString("PROFILE", ""));
        Log.d("ProfileDetailsFragmentA", getArguments().getString("ATTRIBUTES", ""));

        /****** code for floating button ******/
        FloatingActionButton multipleActions = new FloatingActionButton(getActivity().getBaseContext());
//        multipleActions.setTitle(getContext().getString(R.string.add_profile));
        multipleActions.setOnClickListener(multipleActionsHandler);
        /****** end of code for floating button ******/

        try {
            JSONObject profile = new JSONObject(getArguments().getString("PROFILE", ""));
            profile_name = (TextView) view.findViewById(R.id.profile_name);
            profile_name.setText(profile.getString("name"));

            JSONObject contacts = new JSONObject(getArguments().getString("ATTRIBUTES", ""));

            /*  to organize the profile contacts in the view  */
            int i = 0;
            int padding = 110;
            int amountPadding = 0;
            String key;
            Iterator<String> it = contacts.keys();
            while(it.hasNext()){
                key = it.next();
                Log.d("KEYS", key);

                switch (key){
                    case "NUMBER":
                        number = (IconTextView) view.findViewById(R.id.profile_phone);
                        number.setText("{fa-phone}  " + contacts.getString(key));
                        number.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) number.getLayoutParams();
                            params.topMargin = amountPadding; //i*padding;
                            number.setLayoutParams(params);
                        }
                        number.setOnClickListener(numberHandler);
                        break;
                    case "EMAIL":
                        email = (IconTextView) view.findViewById(R.id.profile_email);
                        email.setText("{fa-envelope-o}  " + contacts.getString(key));
                        email.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) email.getLayoutParams();
                            params.topMargin = amountPadding;
                            email.setLayoutParams(params);
                        }
                        email.setOnClickListener(emailHandler);
                        break;
                    case "FACEBOOK":
                        facebook = (IconTextView) view.findViewById(R.id.profile_facebook);
                        facebook.setText("{fa-facebook}   /" + contacts.getString(key));
                        facebook.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) facebook.getLayoutParams();
                            params.topMargin = amountPadding;
                            facebook.setLayoutParams(params);
                        }
                        facebook.setOnClickListener(facebookHandler);
                        break;
                    case "INSTAGRAM":
                        instagram = (IconTextView) view.findViewById(R.id.profile_instagram);
                        instagram.setText("{fa-instagram}  @" + contacts.getString(key));
                        instagram.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) instagram.getLayoutParams();
                            params.topMargin = amountPadding;
                            instagram.setLayoutParams(params);
                        }
                        instagram.setOnClickListener(instagramHandler);
                        break;
                    case "LINKEDIN":
                        linkedin = (IconTextView) view.findViewById(R.id.profile_linkedin);
                        linkedin.setText("{fa-linkedin}  /" + contacts.getString(key));
                        linkedin.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linkedin.getLayoutParams();
                            params.topMargin = amountPadding;
                            linkedin.setLayoutParams(params);
                        }
                        linkedin.setOnClickListener(linkedinHandler);
                        break;
                    case "GOOGLEPLUS":
                        googleplus = (IconTextView) view.findViewById(R.id.profile_googleplus);
                        googleplus.setText("{fa-google-plus}  +" + contacts.getString(key));
                        googleplus.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) googleplus.getLayoutParams();
                            params.topMargin = amountPadding;
                            googleplus.setLayoutParams(params);
                        }
                        googleplus.setOnClickListener(googleplusHandler);
                        break;
                    case "TWITTER":
                        twitter = (IconTextView) view.findViewById(R.id.profile_twitter);
                        twitter.setText("{fa-twitter}  @" + contacts.getString(key));
                        twitter.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) twitter.getLayoutParams();
                            params.topMargin = amountPadding;
                            twitter.setLayoutParams(params);
                        }
                        twitter.setOnClickListener(twitterHandler);
                        break;
                }
                amountPadding += padding;
            }
        }
        catch (org.json.JSONException e){
            Log.d("onCreateView", "No data");
        }

        return view;
    }


    View.OnClickListener numberHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_number, R.id.attribute_number, "{fa-phone}  ", "number");
        }
    };


    View.OnClickListener emailHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_email, R.id.attribute_email, "{fa-envelope-o}  ", "email");
        }
    };


    View.OnClickListener facebookHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_facebook, R.id.attribute_facebook, "{fa-facebook}   /", "facebook");
        }
    };


    View.OnClickListener instagramHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_instagram, R.id.attribute_instagram, "{fa-instagram}  @", "instagram");
        }
    };


    View.OnClickListener linkedinHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_linkedin, R.id.attribute_linkedin, "{fa-linkedin}  /", "linkedin");
        }
    };


    View.OnClickListener googleplusHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_googleplus, R.id.attribute_googleplus, "{fa-google-plus}  +", "googleplus");
        }
    };


    View.OnClickListener twitterHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showEditDialog(R.layout.dialog_profile_detail_edit_twitter, R.id.attribute_twitter, "{fa-twitter}  @", "twitter");
        }
    };


    private void showEditDialog(int dialog_layout, int editText, final String icon, String attrType) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(getContext().getString(R.string.attribute_dialog_title));

        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getActivity());

        // Pass null as the parent view because its going in the dialog layout
        final View inflator = linf.inflate(dialog_layout, null);

        final IconTextView oldValue;
        switch (attrType) {
            case "number":
                oldValue = number;
                break;
            case "email":
                oldValue = email;
                break;
            case "facebook":
                oldValue = facebook;
                break;
            case "instagram":
                oldValue = instagram;
                break;
            case "linkedin":
                oldValue = linkedin;
                break;
            case "googleplus":
                oldValue = googleplus;
                break;
            case "twitter":
                oldValue = twitter;
                break;
            default:
                oldValue = email;
                break;
        }

        final EditText newValue = (EditText) inflator.findViewById(editText);

        // Inflate and set the layout for the dialog
        builder.setView(inflator)
            // Add action buttons
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Log.i("DIALOG", "YES");
                    Log.i("DIALOG", newValue.getText().toString());
                    Log.i("DIALOG",oldValue.getText().toString());

                    oldValue.setText(icon + newValue.getText());
                }
            })
            .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i("DIALOG", "CANCEL");
                    dialog.cancel();
                }
            });

        builder.create();
        builder.show();
    }

    View.OnClickListener multipleActionsHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final View action_phone = getActivity().findViewById(R.id.action_phone);
            action_phone.setVisibility(action_phone.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            Log.d("FLOTINGACTION","action_phone");

            ///TODO change the target of the intent. Just for testing
            Intent new_profile_intent = new Intent(getActivity(), NewProfileActivity.class);
            getActivity().startActivity(new_profile_intent);
        }
    };

}
