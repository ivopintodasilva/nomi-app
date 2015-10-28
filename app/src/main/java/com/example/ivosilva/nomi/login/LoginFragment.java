package com.example.ivosilva.nomi.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.registration.RegisterActivity;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;


import mehdi.sakout.fancybuttons.FancyButton;

public class LoginFragment extends Fragment{
    FancyButton btn_login;
    FancyButton btn_register;
    private RequestQueue mQueue;
    public static final String LOGINPREFS = "LoginPrefs" ;
    public static final String USERID = "idKey";
    public static final String REQUEST_TAG = "LoginFragment";
    KenBurnsView kbv;
    SharedPreferences shared_preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        kbv = (KenBurnsView) view.findViewById(R.id.image);

        btn_login = (FancyButton) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(loginHandler);

        btn_register = (FancyButton) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(registerHandler);

        return view;
    }


    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {


            /*
            *
            *   Insert login logic!
            *
            */
            EditText username = (EditText) getActivity().findViewById(R.id.email);
            EditText password = (EditText) getActivity().findViewById(R.id.password);


            mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
            String url = "http://192.168.160.56:8000/api/user/login/?email=" + username.getText().toString() + "&password=" + password.getText();
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

                            kbv.setImageDrawable(getResources().getDrawable(R.drawable.login_background));
                            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
                            getActivity().startActivity(menu_intent);
                            getActivity().finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            kbv.setImageDrawable(getResources().getDrawable(R.drawable.new_bg));
                            Toast.makeText(getActivity(), "You shall not pass!", Toast.LENGTH_SHORT).show();
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
