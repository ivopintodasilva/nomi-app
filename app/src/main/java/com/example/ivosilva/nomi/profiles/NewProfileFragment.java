package com.example.ivosilva.nomi.profiles;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by silva on 30-10-2015.
 */
public class NewProfileFragment extends Fragment {

    public static final String REQUEST_TAG = "NewProfileFragment";

    SharedPreferences shared_preferences;

    private RequestQueue mQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_profile, container, false);


        Spinner lstColors = (Spinner) view.findViewById(R.id.new_profile_listColors);
        final String[] colors = new String[] { "BLUE", "BLACK", "RED", "GREEN", "WHITE" };
        final ArrayList<String> lista = new ArrayList<>();

        for(String str: colors)
            lista.add(str);

        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_nfc, lista);

        adapter.setDropDownViewResource(R.layout.spinner_nfc_list);
        lstColors.setAdapter(adapter);


        final FancyButton btnCreateProfile = (FancyButton) view.findViewById(R.id.btn_create_new_profile);
        btnCreateProfile.setOnClickListener(btnCreateProfileHandler);

        return view;
    }

    View.OnClickListener btnCreateProfileHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("NEWPROFILE", "Created new Profile");

            shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
            String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

            shared_preferences = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);
            int userId = shared_preferences.getInt(LoginFragment.USERID, -1);

            final EditText name = (EditText) getActivity().findViewById(R.id.new_profile_name);
            final Spinner color = (Spinner) getActivity().findViewById(R.id.new_profile_listColors);

            boolean valid = true;

            if (name.getText().toString().trim().equals("")) {
                name.setError(getResources().getString(R.string.empty_profile_name));
                valid = false;
            } else
                name.setError(null);

            if (!valid) {
                Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                return;
            }

            try {
                JSONObject jsonBody = new JSONObject("{" +
                        "\"name\":" + "\"" + name.getText().toString() + "\"," +
                        "\"user\":" + "\"" + String.valueOf(userId).toString() + "\"," +
                        "\"color\":" + "\"" + color.getSelectedItem().toString() + "\"" +
                        "}");
                Log.d("NEWPROFILE", jsonBody.toString());

                mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
                String url = "http://"+serverIp+"/api/profile/user/";

                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                    Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse", jsonObject.toString());

                            Toast.makeText(getActivity(), R.string.created_new_profile,
                                    Toast.LENGTH_LONG).show();


                            Intent profilelist_intent = new Intent(getActivity(),
                                    ProfileListActivity.class);
                            profilelist_intent.putExtra("event","newProfileCreated");
                            getActivity().startActivity(profilelist_intent);
                            getActivity().finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse = volleyError.networkResponse;
                            if (networkResponse != null && networkResponse.statusCode == 401) {
                                Crouton.makeText(getActivity(), R.string.already_registered_email, Style.ALERT).show();
                            }else{
                                Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                            }
                        }
                    });
                jsonRequest.setTag(REQUEST_TAG);

                mQueue.add(jsonRequest);

            } catch (JSONException e) {
                Log.e("NEWPROFILEEXCEPTION", e.toString());
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
