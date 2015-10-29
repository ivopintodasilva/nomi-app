package com.example.ivosilva.nomi.contacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;

import org.buraktamturk.loadingview.LoadingView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ContactListFragment extends Fragment {

    public static final String REQUEST_TAG = "ContactListFragment";
    public static final String LOGINPREFS = "LoginPrefs" ;
    public static final String USERID = "idKey";
    private RequestQueue mQueue;
    SharedPreferences shared_preferences;

    LoadingView load;

    List<CollectedContacts> user_contacts;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shared_preferences = getActivity().getSharedPreferences(LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(USERID, -1) == -1){
            Log.d("onCreate", "Não tem shared preferences wtf");
            Toast.makeText(getActivity(), "Please login before using this functionality.",
                    Toast.LENGTH_LONG).show();
            Intent login_intent = new Intent(getActivity(), MainActivity.class);
            startActivity(login_intent);
            getActivity().finish();
        }

        mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        String url = "http://192.168.160.56:8000/api/profile/relation/user/" + shared_preferences.getInt(USERID, -1) + "/";
        Log.d("URL", url);

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            JSONArray results = jsonObject.getJSONArray("results");
                            JSONObject contact;
                            JSONObject user_info;

                            user_contacts = new ArrayList<CollectedContacts>();

                            for(int i = 0; i < results.length(); i++){
                                contact = results.getJSONObject(i);
                                user_info = contact.getJSONObject("user");
                                Log.d("results", contact.getJSONObject("user").toString());
                                user_contacts.add(new CollectedContacts(user_info.getInt("id"), user_info.getString("first_name") + " " + user_info.getString("last_name")));
                            }

                            RecyclerView recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view);
                            recycler_view.setHasFixedSize(true);

                            mLayoutManager = new LinearLayoutManager(getActivity());
                            recycler_view.setLayoutManager(mLayoutManager);

                            RVPContactsAdapter adapter = new RVPContactsAdapter(user_contacts);
                            recycler_view.setAdapter(adapter);

                            load.setVisibility(View.INVISIBLE);
                            //load.setLoading(false);

                        }
                        catch (JSONException e){
                            Log.d("JSONException", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Crouton.makeText(getActivity(), "Error fetching contact data.", Style.ALERT).show();
                    }
                });

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);


        load = (LoadingView) view.findViewById(R.id.loading);
//
//
//
//
//        user_contacts.get(0).addContact("FACEBOOK", "/swag/sempreaandar");
//        user_contacts.add(new CollectedContacts(1, "Emma Wilson"));
//        user_contacts.get(1).addContact("INSTAGRAM", "swagger");
//        user_contacts.add(new CollectedContacts(2, "Lavery Maiss"));
//        user_contacts.get(2).addContact("FACEBOOK", "/venancio");
//        user_contacts.add(new CollectedContacts(3, "Lillie Watts"));
//        user_contacts.get(3).addContact("NUMBER", "915944584");
//        user_contacts.add(new CollectedContacts(4, "Emma Wilson"));
//        user_contacts.get(4).addContact("LINKEDIN", "bino_o_trabalhador");
//        user_contacts.add(new CollectedContacts(5, "Lavery Maiss"));
//        user_contacts.get(5).addContact("EMAIL", "ivopintodasilva@gmail.com");
//        user_contacts.get(5).addContact("GOOGLE", "ivopintodasilva@gmail.com");
//        user_contacts.get(5).addContact("FACEBOOK", "/diogo");
//        user_contacts.get(5).addContact("TWITTER", "diogo");
//        user_contacts.add(new CollectedContacts(6, "Lillie Watts"));
//        user_contacts.add(new CollectedContacts(7, "Emma Wilson"));
//        user_contacts.add(new CollectedContacts(8, "Lavery Maiss"));
//        user_contacts.add(new CollectedContacts(9, "Lillie Watts"));
//        user_contacts.add(new CollectedContacts(10, "Emma Wilson"));
//        user_contacts.add(new CollectedContacts(11, "Lavery Maiss"));
//        user_contacts.add(new CollectedContacts(12, "Lillie Watts"));
//        user_contacts.add(new CollectedContacts(13, "Emma Wilson"));
//        user_contacts.add(new CollectedContacts(14, "Lavery Maiss"));
//        user_contacts.add(new CollectedContacts(15, "Lillie Watts"));


        return view;
    }

}
