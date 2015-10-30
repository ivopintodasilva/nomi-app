package com.example.ivosilva.nomi.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joanzapata.iconify.widget.IconTextView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.tkeunebr.gravatar.Gravatar;

/**
 * Created by ivosilva on 17/10/15.
 */
public class RVPContactsAdapter extends RecyclerView.Adapter<RVPContactsAdapter.ProfileViewHolder> {

    List<CollectedContacts> user_profiles = new ArrayList<>();
    public String gravatarUrl;

    RVPContactsAdapter(List<CollectedContacts> user_profiles){
        this.user_profiles = user_profiles;
    }



    public static class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView personName;
        public IconTextView shared_contacts;
        public ProfileViewHolderClicks mListener;
        public CircularImageView mPhoto;
        public Context context;


        //ImageView personPhoto;

        public ProfileViewHolder(View itemView, ProfileViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            cv = (CardView)itemView.findViewById(R.id.profile_card);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            shared_contacts = (IconTextView) itemView.findViewById(R.id.shared_contacts);
            mPhoto = (CircularImageView) itemView.findViewById(R.id.person_photo);
            context = itemView.getContext();



            itemView.setOnClickListener(this);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }

        @Override
        public void onClick(View v) {
            //Log.d("VIEWHOLDER", new Integer(this.getLayoutPosition()).toString());
            mListener.openDetails(v, this.getLayoutPosition());
        }


        public interface ProfileViewHolderClicks {
            void openDetails(View v, int position);
        }

    }


    public CollectedContacts getItem(int position) {
        return user_profiles.get(position);
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        //ProfileViewHolder pvh = new ProfileViewHolder(v);

        ProfileViewHolder vh = new ProfileViewHolder(v, new ProfileViewHolder.ProfileViewHolderClicks() {
            public void openDetails(View v, int position) {

                /*  create new activity and display details  */
                /*  put selected object on intent extras  */


                Gson gson = new GsonBuilder().
                        registerTypeAdapter(CollectedContacts.class, new CollectedContactsSerializer())
                        .create();
                String profile_json = gson.toJson(user_profiles.get(position));
                String contacts_json = gson.toJson(user_profiles.get(position).getAllContacts());

                Intent contact_details = new Intent(v.getContext(), ContactDetailsActivity.class);
                contact_details.putExtra("PROFILE", profile_json);
                contact_details.putExtra("CONTACTS", contacts_json);
                v.getContext().startActivity(contact_details);


            }
        });
        return vh;
    }



    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(CollectedContacts.class, new CollectedContactsSerializer())
                .create();
        String contacts_json = gson.toJson(user_profiles.get(position).getAllContacts());

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
            holder.shared_contacts.setText(setter);
        }catch (org.json.JSONException e){
            Log.d("JSONException", e.toString());
        }

        holder.personName.setText(user_profiles.get(position).getName());


        gravatarUrl = Gravatar.init().with(user_profiles.get(position).getEmail()).force404().size(60).build();
        Log.d("gravatarUrl", gravatarUrl);
        // substitute by real user image url
        Glide.with(holder.context).load(gravatarUrl).into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        if(user_profiles != null){
            return user_profiles.size();
        }
        return 0;
    }


}
