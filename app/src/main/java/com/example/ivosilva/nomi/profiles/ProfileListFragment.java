package com.example.ivosilva.nomi.profiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class ProfileListFragment extends Fragment {

    private RequestQueue mQueue;

    SharedPreferences shared_preferences;

    private RotateLoading rotateLoading;

    List<Profile> profiles_list;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());

        shared_preferences = getActivity().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
        String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

        shared_preferences = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);

        if (shared_preferences.getInt(LoginFragment.USERID, -1) == -1){
            Log.d("onCreate", "Não tem shared preferences wtf");
            Toast.makeText(getActivity(), "Please login before using this functionality.",
                    Toast.LENGTH_LONG).show();
            Intent login_intent = new Intent(getActivity(), MainActivity.class);
            startActivity(login_intent);
            getActivity().finish();
        }

        mQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        String url = "http://"+serverIp+"/api/profile/user/" + shared_preferences.getInt(LoginFragment.USERID, -1);

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
                        Crouton.makeText(getActivity(), "Error fetching profiles data.", Style.ALERT).show();
                    }
                });

        jsonRequest.setTag(LoginFragment.REQUEST_TAG);
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
                    profile = results.getJSONObject(i); // obter profile

                    int profile_id = profile.getInt("id");
                    String name = profile.getString("name");
                    String color = profile.getString("color");

                    profiles_list.add(new Profile(profile_id, name, color));

                    attributes = profile.getJSONArray("attributes"); // obter attributes do profile
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

            RecyclerView recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            recycler_view.setLayoutManager(mLayoutManager);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (profiles_list.size() == 0) {
                        TextView textView = (TextView) getActivity().findViewById(R.id.profiles_empty_label);
                        textView.setVisibility(1);
                    }
                    if (rotateLoading.isStart()) {
                        rotateLoading.stop();
                    }
                }
            }, 2000);


            RVPProfilesAdapter adapter = new RVPProfilesAdapter(profiles_list);
            recycler_view.setAdapter(adapter);

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
        View view = inflater.inflate(R.layout.fragment_profiles_list, container, false);

        // get the loading wheel and start it
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading_profiles);
        rotateLoading.start();


        /****** code for floating button ******/
        final View actionA = getActivity().findViewById(R.id.action_a);

        FloatingActionButton actionB = new FloatingActionButton(getActivity().getBaseContext());
        actionB.setTitle(getContext().getString(R.string.add_profile));
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionA.setVisibility(actionA.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        /****** end of code for floating button ******/


        return view;
    }

}
