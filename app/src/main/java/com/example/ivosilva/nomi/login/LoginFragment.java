package com.example.ivosilva.nomi.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.registration.RegisterActivity;
import com.example.ivosilva.nomi.registration.RegisterFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginFragment extends Fragment{
    // UA
    // public static final String base_url = "http://192.168.160.56:8000/";

    // CASA
    public static final String base_url = "http://192.168.0.24:8000/";

    FancyButton btn_login;
    FancyButton btn_register;
    private RequestQueue mQueue;
    public static final String LOGINPREFS = "LoginPrefs" ;
    public static final String USERID = "idKey";
    public static final String REQUEST_TAG = "LoginFragment";
    public static final String SERVER = "ServerPrefs";
    public static final String SERVERIP = "ServerIP";
    public static final String IP = "192.168.1.102:8000"; //"192.168.160.56:8000";

    SharedPreferences shared_preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy(){
        Crouton.cancelAllCroutons();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("ONRESUME","YES");
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("event")){
            if(intent.hasExtra("event") && intent.getStringExtra("event").equals("userRegistered")) {
                Log.i("ONRESUME","Has event with user registered");
                Crouton.makeText(getActivity(), "User registered!", Style.CONFIRM).show();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);


        btn_login = (FancyButton) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(loginHandler);

        btn_register = (FancyButton) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(registerHandler);

        shared_preferences = getActivity().getSharedPreferences(SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString(SERVERIP, IP); // Defining server ip
        editor.commit();

        return view;
    }


    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {

            EditText username = (EditText) getActivity().findViewById(R.id.email);
            EditText password = (EditText) getActivity().findViewById(R.id.password);

            shared_preferences = getActivity().getSharedPreferences(SERVER, Context.MODE_PRIVATE);
            String serverIp = shared_preferences.getString(SERVERIP, "localhost");


            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://"+serverIp+"/api/user/login/?email=" + username.getText().toString() + "&password=" + password.getText();
            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse", jsonObject.toString());

                            shared_preferences = getActivity().getSharedPreferences(LOGINPREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared_preferences.edit();
                            try {
                                editor.putInt(USERID, jsonObject.getInt("id"));
                                editor.commit();
                            }
                            catch (JSONException e){
                                Log.d("LoginOnResponse", e.toString());
                            }

                            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
                            getActivity().startActivity(menu_intent);
                            getActivity().finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Crouton.makeText(getActivity(), "You shall not pass!", Style.ALERT).show();
                        }
                    });
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);

        }
    };


    View.OnClickListener registerHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent register_intent = new Intent(getActivity(), RegisterActivity.class);
            getActivity().startActivity(register_intent);

        }
    };

}
