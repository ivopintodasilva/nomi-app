package com.example.ivosilva.nomi.nfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.ivosilva.nomi.contacts.CollectedContacts;
import com.example.ivosilva.nomi.contacts.CollectedContactsSerializer;
import com.example.ivosilva.nomi.contacts.ContactDetailsActivity;
import com.example.ivosilva.nomi.contacts.RVPContactsAdapter;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.tkeunebr.gravatar.Gravatar;
import mehdi.sakout.fancybuttons.FancyButton;

public class NFCConnectedFragment extends Fragment {

    FancyButton btn_menu;
    FancyButton btn_details;
    TextView nfc_connected;
    CircularImageView circularImageView;

    int conn_id;
    String conn_name;
    String conn_email;

    CollectedContacts new_connection;

    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "NFCConnectedFragment";
    SharedPreferences shared_preferences;

    private RotateLoading rotateLoading;

    Activity activity = getActivity();

    String new_profile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        btn_details.setVisibility(View.VISIBLE);
        btn_menu.setVisibility(View.VISIBLE);
        nfc_connected.setVisibility(View.VISIBLE);
        circularImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nfc_connect, container, false);

        Bundle b = getActivity().getIntent().getExtras();
        String new_conn_str = b.getString("new_connection");
        new_profile = b.getString("new_profile");

        try{
            JSONObject new_conn = new JSONObject(new_conn_str);
            conn_id = new_conn.getInt("id");
            conn_name = new_conn.getString("first_name") + " " + new_conn.getString("last_name");
            conn_email = new_conn.getString("email");

        }catch (JSONException e){
            Log.d("JSONException", e.toString());
        }

        String gravatar_url = Gravatar.init().with(conn_email).force404().size(200).build();

        // Settings for user avatar
        circularImageView = (CircularImageView) view.findViewById(R.id.avatar);
        circularImageView.setBorderWidth(2);
        circularImageView.addShadow();

        // put gravatar image in details
        Glide.with(getContext())
                .load(gravatar_url).error(R.drawable.user_placeholder)
                .into(circularImageView);

        nfc_connected = (TextView) view.findViewById(R.id.text_nfc_connected);
        nfc_connected.setText(getResources().getString(R.string.you_just_met) + " " + conn_name + "!");

        btn_menu = (FancyButton) view.findViewById(R.id.btn_go_to_menu);
        btn_menu.setOnClickListener(menuHandler);

        btn_details = (FancyButton) view.findViewById(R.id.btn_go_to_contact);
        btn_details.setOnClickListener(detailsHandler);

        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading_nfc_connect);

        return view;
    }

    View.OnClickListener menuHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(menu_intent);

        }
    };

    View.OnClickListener detailsHandler = new View.OnClickListener() {
        public void onClick(View v) {

            shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER,
                    Context.MODE_PRIVATE);
            String serverIp = shared_preferences.getString(LoginFragment.SERVERIP,
                    "localhost:8000");

            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://"+serverIp+"/api/profile/" + new_profile;

            Log.d("NFCConnectedFragment", url);

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                    Request.Method.GET, url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            try{

                                JSONArray results = jsonObject.getJSONArray("results");
                                JSONObject contact;
                                JSONObject user_info;
                                JSONArray attributes;


                                contact = results.getJSONObject(0);
                                user_info = contact.getJSONObject("user");
                                Log.d("results", contact.toString());

                                new_connection = new CollectedContacts(user_info.getInt("id"),
                                        user_info.getString("first_name") + " " +
                                                user_info.getString("last_name"),
                                        user_info.getString("email"));

                                attributes = contact.getJSONArray("attributes");
                                for (int j = 0; j < attributes.length(); j++) {
                                    Log.d("ATRIB", attributes.get(j).toString());
                                    new_connection.addContact(attributes.getJSONObject(j)
                                            .getString("name"), attributes.getJSONObject(j)
                                            .getString("value"));
                                }


                                Gson gson = new GsonBuilder().
                                        registerTypeAdapter(CollectedContacts.class,
                                                new CollectedContactsSerializer()).create();
                                String profile_json = gson.toJson(new_connection);
                                String contacts_json = gson.toJson(new_connection.getAllContacts());

                                Intent details_intent = new Intent(getActivity(),
                                        ContactDetailsActivity.class);

                                details_intent.putExtra("PROFILE", profile_json);
                                details_intent.putExtra("CONTACTS", contacts_json);

                                if(rotateLoading.isStart()){
                                    rotateLoading.stop();
                                }

                                getActivity().startActivity(details_intent);
                            } catch (JSONException e){
                                Log.d("JSONException", e.toString());
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Crouton.makeText(getActivity(), R.string.error_json_contacts,
                                    Style.ALERT).show();

                            if(rotateLoading.isStart()){
                                rotateLoading.stop();
                            }

                            btn_details.setVisibility(View.VISIBLE);
                            btn_menu.setVisibility(View.VISIBLE);
                            nfc_connected.setVisibility(View.VISIBLE);
                            circularImageView.setVisibility(View.VISIBLE);


                        }
                    });

            jsonRequest.setTag(REQUEST_TAG);
            mQueue.add(jsonRequest);

            btn_details.setVisibility(View.INVISIBLE);
            btn_menu.setVisibility(View.INVISIBLE);
            nfc_connected.setVisibility(View.INVISIBLE);
            circularImageView.setVisibility(View.INVISIBLE);

            rotateLoading.start();

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
