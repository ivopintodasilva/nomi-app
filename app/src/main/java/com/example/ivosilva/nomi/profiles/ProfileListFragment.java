package com.example.ivosilva.nomi.profiles;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ivosilva.nomi.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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


        /* code for floating button */
        final View actionA = getActivity().findViewById(R.id.action_a);

        FloatingActionButton actionB = new FloatingActionButton(getActivity().getBaseContext());
        actionB.setTitle(getContext().getString(R.string.add_profile));
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionA.setVisibility(actionA.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        /* end of code for floating button */


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
        profiles_list.get(0).addConn("0", "1");

        profiles_list.add(new Profile(1, "Personal", ColorsEnum.GREEN));
        profiles_list.get(1).addAttr(new Atributos(0, AtributosEnum.EMAIL, "daniel@todo.nu"));
        profiles_list.get(1).addAttr(new Atributos(1, AtributosEnum.FACEBOOK, "/danifss"));
        profiles_list.get(1).addAttr(new Atributos(2, AtributosEnum.INSTAGRAM, "@swagger"));
        profiles_list.get(1).addAttr(new Atributos(3, AtributosEnum.TWITTER, "ivinho"));
        profiles_list.get(1).addAttr(new Atributos(4, AtributosEnum.NUMBER, "915555555"));

        profiles_list.add(new Profile(2, "Swagg", ColorsEnum.WHITE));
        profiles_list.get(2).addAttr(new Atributos(0, AtributosEnum.EMAIL, "daniel@mantorras.comebebe"));

        profiles_list.add(new Profile(3, "Engate", ColorsEnum.RED));
        profiles_list.get(3).addAttr(new Atributos(0, AtributosEnum.EMAIL, "daniel@todo.nu"));

        profiles_list.add(new Profile(4, "Minimal", ColorsEnum.BLACK));
        profiles_list.get(4).addAttr(new Atributos(0, AtributosEnum.NUMBER, "915555555"));


        RVPProfilesAdapter adapter = new RVPProfilesAdapter(profiles_list);
        recycler_view.setAdapter(adapter);

        return view;
    }

}