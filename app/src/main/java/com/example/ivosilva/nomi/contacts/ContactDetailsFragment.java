package com.example.ivosilva.nomi.contacts;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.tkeunebr.gravatar.Gravatar;

public class ContactDetailsFragment extends Fragment {

    TextView name;
    private RequestQueue mQueue;
    SharedPreferences shared_preferences;
    public static final String REQUEST_TAG = "ContactDetailsFragment";


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


            String gravatar_url = Gravatar.init().with(profile.getString("email")).force404().size(250).build();
            Log.d("ContactDetailsFragment", gravatar_url);

            // put gravatar image in details
            Glide.with(getContext())
                    .load(gravatar_url).error(R.drawable.user_placeholder)
                    .into((CircularImageView) view.findViewById(R.id.details_photo));

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
                        number.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) number.getLayoutParams();
                            params.topMargin = i*60;
                            number.setLayoutParams(params);
                        }
                        break;
                    case "EMAIL":
                        IconTextView email = (IconTextView) view.findViewById(R.id.email);
                        email.setText("{fa-envelope-o}  " + contacts.getString(key));
                        email.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) email.getLayoutParams();
                            params.topMargin = i*60;
                            email.setLayoutParams(params);

                        }
                        break;
                    case "FACEBOOK":
                        final IconTextView facebook = (IconTextView) view.findViewById(R.id.facebook);
                        facebook.setText("{fa-facebook}  " + contacts.getString(key));
                        facebook.setVisibility(View.VISIBLE);
                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Open facebook on browser
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + facebook.getText().toString().split("  ")[1])));
                            }
                        });
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) facebook.getLayoutParams();
                            params.topMargin = i*60;
                            facebook.setLayoutParams(params);
                        }
                        break;
                    case "INSTAGRAM":
                        final IconTextView instagram = (IconTextView) view.findViewById(R.id.instagram);
                        instagram.setText("{fa-instagram}  " + contacts.getString(key));
                        instagram.setVisibility(View.VISIBLE);
                        instagram.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse("http://instagram.com/_u/" + instagram.getText().toString().split("  ")[1]);
                                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                                likeIng.setPackage("com.instagram.android");

                                try {
                                    startActivity(likeIng);
                                } catch (ActivityNotFoundException e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://instagram.com/"+instagram.getText().toString().split("  ")[1])));
                                }                            }
                        });
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) instagram.getLayoutParams();
                            params.topMargin = i*60;
                            instagram.setLayoutParams(params);
                        }
                        break;
                    case "LINKEDIN":
                        IconTextView linkedin = (IconTextView) view.findViewById(R.id.linkedin);
                        linkedin.setText("{fa-linkedin}  " + contacts.getString(key));
                        linkedin.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linkedin.getLayoutParams();
                            params.topMargin = i*60;
                            linkedin.setLayoutParams(params);
                        }
                        break;
                    case "GOOGLE":
                        IconTextView google = (IconTextView) view.findViewById(R.id.googleplus);
                        google.setText("{fa-google-plus}  " + contacts.getString(key));
                        google.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) google.getLayoutParams();
                            params.topMargin = i*60;
                            google.setLayoutParams(params);
                        }
                        break;
                    case "TWITTER":
                        IconTextView twitter = (IconTextView) view.findViewById(R.id.twitter);
                        twitter.setText("{fa-twitter}  @" + contacts.getString(key));
                        twitter.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) twitter.getLayoutParams();
                            params.topMargin = i*60;
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


}
