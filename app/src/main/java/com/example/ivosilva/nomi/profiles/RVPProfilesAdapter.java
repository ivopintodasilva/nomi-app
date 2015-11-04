package com.example.ivosilva.nomi.profiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.example.ivosilva.nomi.volley.CustomJSONObjectRequest;
import com.example.ivosilva.nomi.volley.CustomVolleyRequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by silva on 24/10/15.
 */
public class RVPProfilesAdapter extends RecyclerView.Adapter<RVPProfilesAdapter.ProfileViewHolder> {

    public static final String REQUEST_TAG = "ProfileListAdapter";

    public static class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        public CardView cv;
        public TextView profileName;
        public IconTextView shared_profile_contacts;
        public ProfileViewHolderClicks mListener;


        public ProfileViewHolder(View itemView, ProfileViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            cv = (CardView)itemView.findViewById(R.id.profile_card);
            profileName = (TextView)itemView.findViewById(R.id.profile_name);
            shared_profile_contacts = (IconTextView) itemView.findViewById(R.id.shared_profile_contacts);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Log.d("VIEWHOLDER", new Integer(this.getLayoutPosition()).toString());
            mListener.openDetails(v, this.getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
//            Log.d("VIEWHOLDER", new Integer(this.getLayoutPosition()).toString());
            mListener.deleteProfile(v, this.getLayoutPosition());
            return true;
        }

        public interface ProfileViewHolderClicks {
            void openDetails(View v, int position);
            void deleteProfile(final View v, int position);
        }

    }

    List<Profile> user_profiles;

    RVPProfilesAdapter(List<Profile> user_profiles){
        this.user_profiles = user_profiles;
    }


    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profiles_card, parent, false);
        //ProfileViewHolder pvh = new ProfileViewHolder(v);

        ProfileViewHolder vh = new ProfileViewHolder(v, new ProfileViewHolder.ProfileViewHolderClicks() {
            public void openDetails(View v, int position) {

                /*  create new activity and display details  */
                /*  put selected object on intent extras  */
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(Profile.class, new ProfilesSerializer())
                        .create();
                String profile_json = gson.toJson(user_profiles.get(position));
                String attributes_json = gson.toJson(user_profiles.get(position).getAllAttr());

                Intent profile_details = new Intent(v.getContext(), ProfileDetailsActivity.class);
                profile_details.putExtra("PROFILE", profile_json);
                profile_details.putExtra("ATTRIBUTES", attributes_json);
                v.getContext().startActivity(profile_details);
            }

            public void deleteProfile(final View v, int position) {
                SharedPreferences shared_preferences = v.getContext().getSharedPreferences(LoginFragment.SERVER, Context.MODE_PRIVATE);
                String serverIp = shared_preferences.getString(LoginFragment.SERVERIP, "localhost:8000");

                Log.d("DELETEPROFILE", String.valueOf(position));

                try {
                    JSONObject jsonBody = new JSONObject("{}");

                    RequestQueue mQueue = CustomVolleyRequestQueue.getInstance(v.getContext()).getRequestQueue();
                    String url = "http://"+serverIp+"/api/profile/" + user_profiles.get(position).getId();

                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(
                            Request.Method.DELETE, url, jsonBody,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.d("onResponse", jsonObject.toString());

                                    Toast.makeText(v.getContext(), R.string.deleted_profile,
                                            Toast.LENGTH_LONG).show();

                                    Intent profilelist_intent = new Intent(v.getContext(), MenuActivity.class);
                                    v.getContext().startActivity(profilelist_intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    NetworkResponse networkResponse = volleyError.networkResponse;
                                    if (networkResponse != null && networkResponse.statusCode == 401) {
//                                        Crouton.makeText(v.getContext(), R.string.delete_attribute_error, Style.ALERT).show();
                                        Toast.makeText(v.getContext(), R.string.deleted_profile_error,
                                                Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(v.getContext(), R.string.you_shall_not_pass,
                                                Toast.LENGTH_LONG).show();
//                                        Crouton.makeText(getActivity(), R.string.you_shall_not_pass, Style.ALERT).show();
                                    }
                                }
                            });
                    jsonRequest.setTag(REQUEST_TAG);

                    mQueue.add(jsonRequest);

                } catch (JSONException e) {
                    Log.e("DELETEATTREXCEPTION", e.toString());
                }
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Profile.class, new ProfilesSerializer())
                .create();
        String contacts_json = gson.toJson(user_profiles.get(position).getAllAttr());

        try {
            JSONObject contacts = new JSONObject(contacts_json);
            Iterator<String> it = contacts.keys();
            String key;
            String setter = "";
            while(it.hasNext()){
                key = it.next();
                if(key.equals("NUMBER")){
                    setter += "{fa-phone}  ";
                }
                else if(key.equals("EMAIL")){
                    setter += "{fa-envelope-o}  ";
                }
                else if(key.equals("FACEBOOK")){
                    setter += "{fa-facebook}  ";
                }
                else if(key.equals("INSTAGRAM")){
                    setter += "{fa-instagram}  ";
                }
                else if(key.equals("LINKEDIN")){
                    setter += "{fa-linkedin}  ";
                }
                else if(key.equals("GOOGLE")){
                    setter += "{fa-google-plus}  ";
                }
                else if(key.equals("TWITTER")){
                    setter += "{fa-twitter}  ";
                }

            }
            holder.shared_profile_contacts.setText(setter);
        }catch (org.json.JSONException e){
            Log.d("JSONException", e.toString());
        }

        holder.profileName.setText(user_profiles.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return user_profiles.size();
    }

}
