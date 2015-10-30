package com.example.ivosilva.nomi.registration;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterFragment extends Fragment {
    // UA
    // public static final String base_url = "http://192.168.160.56:8000/";
    // CASA
    public static final String base_url = "http://192.168.0.24:8000/";

    public static final String REQUEST_TAG = "RegisterFragment";
    FancyButton btn_register;
    private RequestQueue mQueue;
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText password1;
    private EditText password2;



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btn_register = (FancyButton) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(registerHandler);

        first_name = (EditText) view.findViewById(R.id.register_first_name);
        last_name = (EditText) view.findViewById(R.id.register_last_name);
        email = (EditText) view.findViewById(R.id.register_email);
        password1 = (EditText) view.findViewById(R.id.register_password1);
        password2 = (EditText) view.findViewById(R.id.register_password2);

        return view;
    }

    View.OnClickListener registerHandler = new View.OnClickListener() {
        public void onClick(View v) {

            boolean valid = true;

            if (email.getText().toString().trim().equals("")){
                email.setError("E-mail cannot be empty");
                valid = false;
            }
            else{
                email.setError(null);
            }

            if (last_name.getText().toString().trim().equals("")){
                last_name.setError("Last name cannot be empty");
                valid = false;
            }
            else{
                last_name.setError(null);
            }

            if (first_name.getText().toString().trim().equals("")){
                first_name.setError("First name cannot be empty");
                valid = false;
            }
            else{
                first_name.setError(null);
            }

            if (password1.getText().toString().trim().equals("")){
                password1.setError("Password cannot be empty");
                valid = false;
            }else{
                password1.setError(null);
            }

            if (password2.getText().toString().trim().equals("")){
                password2.setError("Password confirmation cannot be empty");
                valid = false;
            }
            else{
                if(!password1.getText().toString().trim().equals(password2.getText().toString().trim())){
                    password1.setError("Passwords do not match");
                    password2.setError("Passwords do not match");
                    Crouton.makeText(getActivity(), "You shall not pass!", Style.ALERT).show();
                    return;
                }
                else{
                    password1.setError(null);
                    password2.setError(null);
                    valid = true;
                }
            }

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                email.setError("E-mail is not valid");
                Crouton.makeText(getActivity(), "You shall not pass!", Style.ALERT).show();
                return;
            }
            else{
                email.setError(null);
            }

            if(!valid){
                Crouton.makeText(getActivity(), "You shall not pass!", Style.ALERT).show();
                return;
            }


            try {

                JSONObject jsonBody = new JSONObject("{" +
                        "\"email\": \"" + email.getText().toString() + "\"," +
                        "\"password\": \"" + password1.getText().toString() + "\"," +
                        "\"first_name\": \"" + first_name.getText().toString() + "\"," +
                        "\"last_name\": \"" + last_name.getText().toString() + "\"" +
                        "}");

                Log.d("JSON", jsonBody.toString());


                mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
                String url = base_url + "api/user/";

                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                        Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.d("onResponse", jsonObject.toString());

                                Toast.makeText(getActivity(), "User registered!",
                                        Toast.LENGTH_LONG).show();

                                Intent register_intent = new Intent(getActivity(),
                                        MainActivity.class);
                                getActivity().startActivity(register_intent);
                                getActivity().finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                NetworkResponse networkResponse = volleyError.networkResponse;
                                if (networkResponse != null && networkResponse.statusCode == 401) {
                                    Crouton.makeText(getActivity(), "E-mail is already registered.", Style.ALERT).show();
                                }else{
                                    Crouton.makeText(getActivity(), "You shall not pass!", Style.ALERT).show();
                                }
                            }
                        });
                jsonRequest.setTag(REQUEST_TAG);

                mQueue.add(jsonRequest);


            } catch (JSONException e) {
                Log.d("RegisterFragment", e.toString());
            }
        }
    };

}
