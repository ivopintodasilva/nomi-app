package com.example.ivosilva.nomi.contacts;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivosilva.nomi.R;

import java.util.List;

/**
 * Created by ivosilva on 17/10/15.
 */
public class RVPContactsAdapter extends RecyclerView.Adapter<RVPContactsAdapter.ProfileViewHolder>{

    List<CollectedProfiles> user_profiles;

    RVPContactsAdapter(List<CollectedProfiles> user_profiles){
        this.user_profiles = user_profiles;
    }


    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView personName;
        //ImageView personPhoto;

        ProfileViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.profile_card);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }


    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        ProfileViewHolder pvh = new ProfileViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        //ProfileViewHolder.personName.setText(user_profiles.get(position).name);
        holder.personName.setText(user_profiles.get(position).name);
    }

    @Override
    public int getItemCount() {
        return user_profiles.size();
    }

}
