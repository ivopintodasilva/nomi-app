package com.example.ivosilva.nomi.contacts;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.victor.loading.rotate.RotateLoading;

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

    private RotateLoading rotateLoading;

    List<CollectedContacts> user_contacts;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shared_preferences = getActivity().getSharedPreferences(LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(USERID, -1) == -1){
            Toast.makeText(getActivity(), "Please login before using this functionality.",
                    Toast.LENGTH_LONG).show();
            Intent login_intent = new Intent(getActivity(), MainActivity.class);
            startActivity(login_intent);
            getActivity().finish();
        }

        mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        String url = "http://192.168.160.56:8000/api/profile/relation/user/" + shared_preferences.getInt(USERID, -1) + "/";

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        // do the Json parsing in a different thread
                        new ParseJSON().execute(jsonObject);

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


    // Async task in order no to parse the JSON response in the UI thread
    class ParseJSON extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected String doInBackground(JSONObject... params) {
            try{
                JSONObject jsonObject = params[0];

                JSONArray results = jsonObject.getJSONArray("results");
                JSONObject contact;
                JSONObject user_info;
                JSONArray attributes;

                user_contacts = new ArrayList<CollectedContacts>();

                for(int i = 0; i < results.length(); i++){
                    contact = results.getJSONObject(i);
                    user_info = contact.getJSONObject("user");
                    Log.d("results", contact.toString());
                    user_contacts.add(new CollectedContacts(user_info.getInt("id"), user_info.getString("first_name") + " " + user_info.getString("last_name"), user_info.getString("email")));

                    attributes = contact.getJSONArray("attributes");
                    for(int j = 0; j < attributes.length(); j++){
                        Log.d("ATRIB", attributes.get(j).toString());
                        user_contacts.get(i).addContact(attributes.getJSONObject(j).getString("name"), attributes.getJSONObject(j).getString("value"));
                    }
                }
            }
            catch (JSONException e){
                Log.d("JSONException", e.toString());
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            RecyclerView recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            recycler_view.setLayoutManager(mLayoutManager);

            RVPContactsAdapter adapter = new RVPContactsAdapter(user_contacts);
            recycler_view.setAdapter(adapter);

            if(rotateLoading.isStart()){
                rotateLoading.stop();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        // get the loading wheel and start it
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        rotateLoading.start();

        return view;
    }

}
