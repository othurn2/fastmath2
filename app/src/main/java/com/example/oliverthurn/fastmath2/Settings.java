package com.example.oliverthurn.fastmath2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SearchViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by oliverthurn on 12/8/16.
 */
public class Settings extends DialogFragment {

    protected static final String MUTE_ON = "ON";
    protected static final String MUTE_OFF = "OFF";

    protected ImageButton mute;
    protected ImageButton troph;
    protected TextView muteTV;
    protected int clickCounter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_settings, container, false);
        mute = (ImageButton) rootView.findViewById(R.id.muteButton);
        troph = (ImageButton) rootView.findViewById(R.id.trophButton);
        muteTV = (TextView) rootView.findViewById(R.id.muteSettingsTV);

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                muteButtonSettings();
            }
        });

        troph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(rootView.getContext(), AchievementsActivity.class));
            }
        });
        muteTV.setText(MUTE_ON);
        getDialog().setTitle("This is working");
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (MainActivity.isMute){
            MainActivity.toMute = true;
        } else {
            MainActivity.toMute = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (MainActivity.toMute){
            mute.setImageResource(R.drawable.volumeoff);
            muteTV.setText(MUTE_OFF);
        } else {
            mute.setImageResource(R.drawable.volumeon);
            muteTV.setText(MUTE_ON);
        }
    }

    public void muteButtonSettings(){
        clickCounter++;
        if (clickCounter % 2 == 0){
            mute.setImageResource(R.drawable.volumeon);
            mute.setBackgroundColor(Color.WHITE);
            muteTV.setText(MUTE_ON);
            MainActivity.isMute = false;
        } else {
            mute.setImageResource(R.drawable.volumeoff);
            mute.setBackgroundColor(Color.WHITE);
            muteTV.setText(MUTE_OFF);
            MainActivity.isMute = true;
        }
    }
}
