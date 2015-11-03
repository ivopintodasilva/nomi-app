package com.example.ivosilva.nomi.profiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.getbase.floatingactionbutton.FloatingActionButton;
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

/**
 * Created by silva on 19-10-2015.
 */
public class ProfileDetailsFragment extends Fragment {

    public static final String REQUEST_TAG = "ProfileDetailsFragment";

    private RequestQueue mQueue;

    TextView profile_name;
    private int profile_id;
    private IconTextView number;
    private IconTextView email;
    private IconTextView facebook;
    private IconTextView instagram;
    private IconTextView linkedin;
    private IconTextView googleplus;
    private IconTextView twitter;

    SharedPreferences shared_preferences;
    String gravatar_url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shared_preferences = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);
        String user_email = shared_preferences.getString(LoginFragment.USEREMAIL, "");

        Log.d("ProfileDetailsFragment", user_email);

        if(!user_email.equals("")){
            gravatar_url = Gravatar.init().with(user_email).force404().size(250).build();
        }

        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        // substitute by real user image url
        CircularImageView imageView = (CircularImageView) view.findViewById(R.id.profile_photo);
        Glide.with(getContext()).load(gravatar_url)
                .error(R.drawable.user_placeholder).into(imageView);


        Log.d("ProfileDetailsFragmentP", getArguments().getString("PROFILE", ""));
        Log.d("ProfileDetailsFragmentA", getArguments().getString("ATTRIBUTES", ""));

        /****** code for floating button ******/
        view.findViewById(R.id.action_phone).setOnClickListener(newPhoneHandler);
        view.findViewById(R.id.action_email).setOnClickListener(newEmailHandler);
        view.findViewById(R.id.action_facebook).setOnClickListener(newFacebookHandler);
        view.findViewById(R.id.action_instagram).setOnClickListener(newInstagramHandler);
        view.findViewById(R.id.action_linkedin).setOnClickListener(newLinkedinHandler);
        view.findViewById(R.id.action_googleplus).setOnClickListener(newGoogleplusHandler);
        view.findViewById(R.id.action_twitter).setOnClickListener(newTwitterHandler);
        /****** end of code for floating button ******/

        try {
            JSONObject profile = new JSONObject(getArguments().getString("PROFILE", ""));
            profile_name = (TextView) view.findViewById(R.id.profile_name);
            profile_name.setText(profile.getString("name"));

            profile_id = profile.getInt("id");

            JSONObject contacts = new JSONObject(getArguments().getString("ATTRIBUTES", ""));
//            Log.d("PROFILEDETAILS", contacts.toString());

            /*  to organize the profile contacts in the view  */
            int i = 0;
            int padding = 60;
            int countAttributes = 0;
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
                            params.topMargin = i*padding; //i*padding;
                            number.setLayoutParams(params);
                        }
                        number.setOnClickListener(numberHandler);
                        number.setOnLongClickListener(numberLongHandler);
                        view.findViewById(R.id.action_phone).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "EMAIL":
                        email = (IconTextView) view.findViewById(R.id.profile_email);
                        email.setText("{fa-envelope-o}  " + contacts.getString(key));
                        email.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) email.getLayoutParams();
                            params.topMargin = i*padding;
                            email.setLayoutParams(params);
                        }
                        email.setOnClickListener(emailHandler);
                        email.setOnLongClickListener(emailLongHandler);
                        view.findViewById(R.id.action_email).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "FACEBOOK":
                        facebook = (IconTextView) view.findViewById(R.id.profile_facebook);
                        facebook.setText("{fa-facebook}   /" + contacts.getString(key));
                        facebook.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) facebook.getLayoutParams();
                            params.topMargin = i*padding;
                            facebook.setLayoutParams(params);
                        }
                        facebook.setOnClickListener(facebookHandler);
                        facebook.setOnLongClickListener(facebookLongHandler);
                        view.findViewById(R.id.action_facebook).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "INSTAGRAM":
                        instagram = (IconTextView) view.findViewById(R.id.profile_instagram);
                        instagram.setText("{fa-instagram}  @" + contacts.getString(key));
                        instagram.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) instagram.getLayoutParams();
                            params.topMargin = i*padding;
                            instagram.setLayoutParams(params);
                        }
                        instagram.setOnClickListener(instagramHandler);
                        instagram.setOnLongClickListener(instagramLongHandler);
                        view.findViewById(R.id.action_instagram).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "LINKEDIN":
                        linkedin = (IconTextView) view.findViewById(R.id.profile_linkedin);
                        linkedin.setText("{fa-linkedin}  /" + contacts.getString(key));
                        linkedin.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linkedin.getLayoutParams();
                            params.topMargin = i*padding;
                            linkedin.setLayoutParams(params);
                        }
                        linkedin.setOnClickListener(linkedinHandler);
                        linkedin.setOnLongClickListener(linkedinLongHandler);
                        view.findViewById(R.id.action_linkedin).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "GOOGLE":
                        googleplus = (IconTextView) view.findViewById(R.id.profile_googleplus);
                        googleplus.setText("{fa-google-plus}  +" + contacts.getString(key));
                        googleplus.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) googleplus.getLayoutParams();
                            params.topMargin = i*padding;
                            googleplus.setLayoutParams(params);
                        }
                        googleplus.setOnClickListener(googleplusHandler);
                        googleplus.setOnLongClickListener(googleplusLongHandler);
                        view.findViewById(R.id.action_googleplus).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                    case "TWITTER":
                        twitter = (IconTextView) view.findViewById(R.id.profile_twitter);
                        twitter.setText("{fa-twitter}  @" + contacts.getString(key));
                        twitter.setVisibility(View.VISIBLE);
                        i++;
                        if(i!=0){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) twitter.getLayoutParams();
                            params.topMargin = i*padding;
                            twitter.setLayoutParams(params);
                        }
                        twitter.setOnClickListener(twitterHandler);
                        twitter.setOnLongClickListener(twitterLongHandler);
                        view.findViewById(R.id.action_twitter).setVisibility(View.GONE);
                        countAttributes++;
                        break;
                }
                if (countAttributes == 7)
                    view.findViewById(R.id.multiple_actions).setVisibility(View.GONE);
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


    private void showEditDialog(int dialog_layout, int editText, final String icon, final String attrType) {
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
                    Log.i("DIALOG", newValue.getText().toString());
                    Log.i("DIALOG",oldValue.getText().toString());

                    boolean valid = true;

                    if (newValue.getText().toString().trim().equals("")) {
                        newValue.setError(getResources().getString(R.string.empty_attribute_detail));
                        valid = false;
                    } else
                        newValue.setError(null);

                    if (!valid) {
                        Crouton.makeText(getActivity(), R.string.empty_attribute_detail, Style.ALERT).show();
                        return;
                    }

                    editAttribute(newValue, attrType);

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

    private void editAttribute(EditText newValue, String attrType) {
        shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
        String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

        try {
            JSONObject jsonBody = new JSONObject("{" +
                    "\"name\":" + "\"" + attrType.toUpperCase() + "\"," +
                    "\"value\":" + "\"" + newValue.getText().toString() + "\"" +
                    "}");
            Log.d("EDITATTRIBUTE", jsonBody.toString());

            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://"+serverIp+"/api/attribute/profile/"+ String.valueOf(profile_id) +"/";

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                    Request.Method.PUT, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse", jsonObject.toString());

                            Toast.makeText(getActivity(), R.string.edited_attribute,
                                    Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse = volleyError.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 401) {
                                Crouton.makeText(getActivity(), R.string.edit_attribute_error, Style.ALERT).show();
                            }else{
                                Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                            }
                        }
                    });
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);

        } catch (JSONException e) {
            Log.e("EDITATTREXCEPTION", e.toString());
        }
    }




    View.OnClickListener newPhoneHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_phone, R.id.add_attribute_number, "NUMBER", "{fa-phone}");
        }
    };

    View.OnClickListener newEmailHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_email, R.id.add_attribute_email, "EMAIL", "{fa-envelope-o}");
        }
    };

    View.OnClickListener newFacebookHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_generalist, R.id.add_attribute_text, "FACEBOOK", "{fa-facebook}");
        }
    };

    View.OnClickListener newInstagramHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_generalist, R.id.add_attribute_text, "INSTAGRAM", "{fa-instagram}");
        }
    };

    View.OnClickListener newLinkedinHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_generalist, R.id.add_attribute_text, "LINKEDIN", "{fa-linkedin}");
        }
    };

    View.OnClickListener newGoogleplusHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_email, R.id.add_attribute_email, "GOOGLE", "{fa-google-plus}");
        }
    };

    View.OnClickListener newTwitterHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAddDialog(R.layout.dialog_add_attr_generalist, R.id.add_attribute_text, "TWITTER", "{fa-twitter}");
        }
    };

    private void showAddDialog(int dialog_layout, int editText, final String attrType, String iconText) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getActivity());

        // Pass null as the parent view because its going in the dialog layout
        final View inflator = linf.inflate(dialog_layout, null);

        final IconTextView dialogText = (IconTextView) inflator.findViewById(R.id.dialog_image);
        dialogText.setText(iconText);

        final EditText newValue = (EditText) inflator.findViewById(editText);

        // Inflate and set the layout for the dialog
        builder.setView(inflator)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addAttribute(newValue, attrType);

                        Intent profilelist_intent = new Intent(getActivity(), ProfileListActivity.class);
                        getActivity().startActivity(profilelist_intent);
                        getActivity().finish();
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

    private void addAttribute(EditText newValue, String attrType) {
        shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
        String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

        try {
            JSONObject jsonBody = new JSONObject("{" +
                    "\"name\":" + "\"" + attrType.toUpperCase() + "\"," +
                    "\"value\":" + "\"" + newValue.getText().toString() + "\"," +
                    "\"profile\":" + "\"" + String.valueOf(profile_id) + "\"" +
                    "}");
            Log.d("ADDATTRIBUTE", jsonBody.toString());

            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://"+serverIp+"/api/attribute/profile/";

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                    Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse", jsonObject.toString());

                            Toast.makeText(getActivity(), R.string.added_attribute,
                                    Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse = volleyError.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 401) {
                                Crouton.makeText(getActivity(), R.string.add_attribute_error, Style.ALERT).show();
                            }else{
                                Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                            }
                        }
                    });
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);

        } catch (JSONException e) {
            Log.e("ADDATTREXCEPTION", e.toString());
        }
    }




    View.OnLongClickListener numberLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("NUMBER");
            return true;
        }
    };

    View.OnLongClickListener emailLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("EMAIL");
            return true;
        }
    };

    View.OnLongClickListener facebookLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("FACEBOOK");
            return true;
        }
    };

    View.OnLongClickListener instagramLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("INSTAGRAM");
            return true;
        }
    };

    View.OnLongClickListener linkedinLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("LINKEDIN");
            return true;
        }
    };

    View.OnLongClickListener googleplusLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("GOOGLE");
            return true;
        }
    };

    View.OnLongClickListener twitterLongHandler = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            deleteAttribute("TWITTER");
            return true;
        }
    };

    private void deleteAttribute(String attrType) {
        shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
        String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

        try {
            JSONObject jsonBody = new JSONObject("{" +
                    "\"name\":" + "\"" + attrType + "\"" +
                    "}");
            Log.d("DELETEATTRIBUTE", jsonBody.toString());

            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://"+serverIp+"/api/attribute/profile/"+ String.valueOf(profile_id) +"/";

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                    Request.Method.DELETE, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse", jsonObject.toString());

                            Toast.makeText(getActivity(), R.string.deleted_attribute,
                                    Toast.LENGTH_LONG).show();

                            Intent profilelist_intent = new Intent(getActivity(), ProfileListActivity.class);
                            getActivity().startActivity(profilelist_intent);
                            getActivity().finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse = volleyError.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 401) {
                                Crouton.makeText(getActivity(), R.string.delete_attribute_error, Style.ALERT).show();
                            }else{
                                Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                            }
                        }
                    });
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);

        } catch (JSONException e) {
            Log.e("DELETEATTREXCEPTION", e.toString());
        }
    }

}
