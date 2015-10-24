package com.example.ivosilva.nomi.profiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivosilva.nomi.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;


public class ProfileListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profiles, container, false);


        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(llm);


        ///TODO  FAKE DATA, REMOVE!
        List<Profile> profiles_list;

        profiles_list = new ArrayList<>();
        profiles_list.add(new Profile(0, "Work", ColorsEnum.BLUE));
        profiles_list.get(0).addAttr(new Atributos(0, AtributosEnum.FACEBOOK, "/danifss"));
        profiles_list.get(0).addAttr(new Atributos(1, AtributosEnum.EMAIL, "daniel.silva@ua.pt"));
        profiles_list.get(0).addAttr(new Atributos(2, AtributosEnum.NUMBER, "912345678"));
        profiles_list.get(0).addAttr(new Atributos(3, AtributosEnum.LINKEDIN, "danifss"));
        profiles_list.get(0).addConn("0","1");

        profiles_list.add(new Profile(1, "Personal", ColorsEnum.GREEN));
        profiles_list.get(0).addAttr(new Atributos(0, AtributosEnum.EMAIL, "daniel@todo.nu"));
        profiles_list.get(0).addAttr(new Atributos(1, AtributosEnum.FACEBOOK, "/danifss"));
        profiles_list.get(0).addAttr(new Atributos(2, AtributosEnum.INSTAGRAM, "@swagger"));
        profiles_list.get(0).addAttr(new Atributos(3, AtributosEnum.TWITTER, "ivinho"));
        profiles_list.get(0).addAttr(new Atributos(4, AtributosEnum.NUMBER, "915555555"));

        profiles_list.add(new Profile(2, "Swagg", ColorsEnum.RED));
        profiles_list.add(new Profile(3, "Engate", ColorsEnum.WHITE));
        profiles_list.add(new Profile(4, "Minimal", ColorsEnum.BLACK));


        RVPProfilesAdapter adapter = new RVPProfilesAdapter(profiles_list);
        recycler_view.setAdapter(adapter);

        return view;
    }

}
