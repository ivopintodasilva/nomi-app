package com.example.ivosilva.nomi.nfc;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.profiles.*;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NFCUpFragment extends Fragment {

    private static final String REQUEST_TAG = "NFCUpFragment";
    SharedPreferences shared_preferences;
    private RequestQueue mQueue;
    List<Profile> profiles_list = new ArrayList<Profile>();
    private RotateLoading rotateLoading;
    Spinner spinner;
    Context context;
    ArrayAdapter<Profile> adapter;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        activity = getActivity();

        shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
        String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

        shared_preferences = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(LoginFragment.USERID, -1) == -1){
            Log.d("onCreate", "Não tem shared preferences wtf");
            Toast.makeText(getActivity(), R.string.please_login_toast,
                    Toast.LENGTH_LONG).show();
            Intent login_intent = new Intent(getActivity(), MainActivity.class);
            startActivity(login_intent);
            getActivity().finish();
        }

        mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        String url = "http://"+serverIp+"/api/profile/user/" + shared_preferences.getInt(LoginFragment.USERID, -1);

        Log.d("URL: ", url);

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        // do the Json parsing in a different thread
                        new LongOperation().execute(jsonObject);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Crouton.makeText(activity, R.string.error_json_profiles, Style.ALERT).show();
                    }
                });

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);
    }


    // Async task in order no to parse the JSON response in the UI thread
    class LongOperation extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected String doInBackground(JSONObject... params) {
            try{
                JSONObject jsonObject = params[0];

                JSONArray results = jsonObject.getJSONArray("results");
                JSONObject profile;
                JSONArray attributes;

                profiles_list = new ArrayList<Profile>();

                for(int i = 0; i < results.length(); i++){
                    profile = results.getJSONObject(i); // get profile

                    int profile_id = profile.getInt("id");
                    String name = profile.getString("name");
                    String color = profile.getString("color");

                    profiles_list.add(new Profile(profile_id, name, color));

                    attributes = profile.getJSONArray("attributes"); // get attributes profile
                    for(int j=0; j<attributes.length(); j++) {
                        Log.d("PROFILEATTR",attributes.getJSONObject(j).getString("name"));
                        String attrName = attributes.getJSONObject(j).getString("name");
                        String attrValue = attributes.getJSONObject(j).getString("value");
                        profiles_list.get(i).addAttr(attrName, attrValue);
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



            if (profiles_list.size() == 0) {


            }else {
                Log.d("ProfilesList", profiles_list.toString());

            }

            if (rotateLoading.isStart()) {
                rotateLoading.stop();
            }

            adapter = new ArrayAdapter<>(context, R.layout.spinner_nfc, profiles_list);
            adapter.setDropDownViewResource(R.layout.spinner_nfc_list);
            spinner.setAdapter(adapter);

            spinner.setVisibility(View.VISIBLE);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Log.d("PROFILE_SELECTED", parentView.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


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
        View view = inflater.inflate(R.layout.fragment_nfcup, container, false);

        // get the loading wheel and start it
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading_nfc);
        rotateLoading.start();

        spinner = (Spinner) view.findViewById(R.id.spinner_profile);
        spinner.setVisibility(View.INVISIBLE);



        return view;
    }

//
//    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
//
//        public void onItemSelected(AdapterView<?> parent, View view, int pos,
//                                   long id) {
//
//            Toast.makeText(parent.getContext(),
//                    "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
//                    Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg0) {
//            // TODO Auto-generated method stub
//
//        }
    //   }



}
