package com.example.ivosilva.nomi.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.example.ivosilva.nomi.MainActivity;
import com.example.ivosilva.nomi.R;
import com.example.ivosilva.nomi.login.LoginFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mikhaellopez.circularimageview.CircularImageView;

import fr.tkeunebr.gravatar.Gravatar;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by silva on 28-10-2015.
 */
public class SettingsFragment extends Fragment {
    FancyButton btn_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btn_logout = (FancyButton) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(btn_logoutHandler);

        String gravatar_url1 = Gravatar.init().with("ivosilva@ua.pt").force404().size(250).build();
        Log.d("SettingsFragment", gravatar_url1);
        String gravatar_url2 = Gravatar.init().with("daniel.silva@ua.pt").force404().size(250).build();
        Log.d("SettingsFragment", gravatar_url2);

        // put gravatar image in details
        Glide.with(getContext())
                .load(gravatar_url1).error(R.drawable.user_placeholder)
                .into((CircularImageView) view.findViewById(R.id.settings_photo_ivo));

        Glide.with(getContext())
                .load(gravatar_url2).error(R.drawable.user_placeholder)
                .into((CircularImageView) view.findViewById(R.id.settings_photo_daniel));

        return view;
    }


    View.OnClickListener btn_logoutHandler = new View.OnClickListener() {
        public void onClick(View v) {
            SharedPreferences settings = getActivity().getSharedPreferences(LoginFragment.LOGINPREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(LoginFragment.USERID);
            editor.commit();

            Intent login_fragment = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(login_fragment);

            getActivity().finish();
        }
    };

}
