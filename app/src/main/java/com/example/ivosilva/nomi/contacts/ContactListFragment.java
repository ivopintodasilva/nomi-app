package com.example.ivosilva.nomi.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(llm);





        /*  FAKE DATA, REMOVE!  */
        List<CollectedProfiles> user_profiles;

        user_profiles = new ArrayList<CollectedProfiles>();
        user_profiles.add(new CollectedProfiles("Emma Wilson"));
        user_profiles.add(new CollectedProfiles("Lavery Maiss"));
        user_profiles.add(new CollectedProfiles("Lillie Watts"));
        user_profiles.add(new CollectedProfiles("Emma Wilson"));
        user_profiles.add(new CollectedProfiles("Lavery Maiss"));
        user_profiles.add(new CollectedProfiles("Lillie Watts"));
        user_profiles.add(new CollectedProfiles("Emma Wilson"));
        user_profiles.add(new CollectedProfiles("Lavery Maiss"));
        user_profiles.add(new CollectedProfiles("Lillie Watts"));
        user_profiles.add(new CollectedProfiles("Emma Wilson"));
        user_profiles.add(new CollectedProfiles("Lavery Maiss"));
        user_profiles.add(new CollectedProfiles("Lillie Watts"));
        user_profiles.add(new CollectedProfiles("Emma Wilson"));
        user_profiles.add(new CollectedProfiles("Lavery Maiss"));
        user_profiles.add(new CollectedProfiles("Lillie Watts"));





        RVPContactsAdapter adapter = new RVPContactsAdapter(user_profiles);
        recycler_view.setAdapter(adapter);

        return view;
    }

}
