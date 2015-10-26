package com.example.ivosilva.nomi.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;

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


        ///TODO  FAKE DATA, REMOVE!
        List<CollectedContacts> user_contacts;

        user_contacts = new ArrayList<CollectedContacts>();
        user_contacts.add(new CollectedContacts(0, "Emma Wilson"));
        user_contacts.get(0).addContact("FACEBOOK", "/swag/sempreaandar");
        user_contacts.add(new CollectedContacts(1, "Emma Wilson"));
        user_contacts.get(1).addContact("INSTAGRAM", "swagger");
        user_contacts.add(new CollectedContacts(2, "Lavery Maiss"));
        user_contacts.get(2).addContact("FACEBOOK", "/venancio");
        user_contacts.add(new CollectedContacts(3, "Lillie Watts"));
        user_contacts.get(3).addContact("NUMBER", "915944584");
        user_contacts.add(new CollectedContacts(4, "Emma Wilson"));
        user_contacts.get(4).addContact("LINKEDIN", "bino_o_trabalhador");
        user_contacts.add(new CollectedContacts(5, "Lavery Maiss"));
        user_contacts.get(5).addContact("EMAIL", "ivopintodasilva@gmail.com");
        user_contacts.get(5).addContact("GOOGLE", "ivopintodasilva@gmail.com");
        user_contacts.get(5).addContact("FACEBOOK", "/diogo");
        user_contacts.get(5).addContact("TWITTER", "diogo");
        user_contacts.add(new CollectedContacts(6, "Lillie Watts"));
        user_contacts.add(new CollectedContacts(7, "Emma Wilson"));
        user_contacts.add(new CollectedContacts(8, "Lavery Maiss"));
        user_contacts.add(new CollectedContacts(9, "Lillie Watts"));
        user_contacts.add(new CollectedContacts(10, "Emma Wilson"));
        user_contacts.add(new CollectedContacts(11, "Lavery Maiss"));
        user_contacts.add(new CollectedContacts(12, "Lillie Watts"));
        user_contacts.add(new CollectedContacts(13, "Emma Wilson"));
        user_contacts.add(new CollectedContacts(14, "Lavery Maiss"));
        user_contacts.add(new CollectedContacts(15, "Lillie Watts"));


        RVPContactsAdapter adapter = new RVPContactsAdapter(user_contacts);
        recycler_view.setAdapter(adapter);

        return view;
    }

}
