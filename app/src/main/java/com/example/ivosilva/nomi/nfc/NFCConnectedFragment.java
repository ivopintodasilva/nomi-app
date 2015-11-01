package com.example.ivosilva.nomi.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.menu.MenuActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import fr.tkeunebr.gravatar.Gravatar;
import mehdi.sakout.fancybuttons.FancyButton;

public class NFCConnectedFragment extends Fragment {

    FancyButton btn_menu;
    FancyButton btn_details;
    TextView nfc_connected;

    int conn_id;
    String conn_name;
    String conn_email;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nfc_connect, container, false);

        Bundle b = getActivity().getIntent().getExtras();
        String new_conn_str = b.getString("new_connection");

        try{
            JSONObject new_conn = new JSONObject(new_conn_str);
            conn_id = new_conn.getInt("id");
            conn_name = new_conn.getString("first_name") + " " + new_conn.getString("last_name");
            conn_email = new_conn.getString("email");

        }catch (JSONException e){
            Log.d("JSONException", e.toString());
        }

        String gravatar_url = Gravatar.init().with(conn_email).force404().size(200).build();

        // Settings for user avatar
        CircularImageView circularImageView = (CircularImageView) view.findViewById(R.id.avatar);
        circularImageView.setBorderWidth(2);
        circularImageView.addShadow();

        // put gravatar image in details
        Glide.with(getContext())
                .load(gravatar_url).error(R.drawable.user_placeholder)
                .into(circularImageView);

        nfc_connected = (TextView) view.findViewById(R.id.text_nfc_connected);
        nfc_connected.setText(getResources().getString(R.string.you_just_met) + " " + conn_name + "!");

        btn_menu = (FancyButton) view.findViewById(R.id.btn_go_to_menu);
        btn_menu.setOnClickListener(menuHandler);

        btn_details = (FancyButton) view.findViewById(R.id.btn_go_to_contact);
        btn_menu.setOnClickListener(detailsHandler);

        return view;
    }

    View.OnClickListener menuHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(menu_intent);

        }
    };

    View.OnClickListener detailsHandler = new View.OnClickListener() {
        public void onClick(View v) {

            Intent menu_intent = new Intent(getActivity(), MenuActivity.class);
            getActivity().startActivity(menu_intent);

        }
    };

}
