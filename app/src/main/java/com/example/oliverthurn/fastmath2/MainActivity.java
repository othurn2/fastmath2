package com.example.oliverthurn.fastmath2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static boolean isMute;
    public static boolean toMute;
    public static String uniqUID = "";

    public static int loadedGolds;
    public static int loadedSilvers;
    public static int loadedBronze;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isMute = false;

        ImageButton next = (ImageButton)findViewById(R.id.playButton);
        ImageButton ach = (ImageButton)findViewById(R.id.achievButton);
        ImageButton set = (ImageButton)findViewById(R.id.settingsButton);
        ImageButton prof = (ImageButton)findViewById(R.id.profileButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Game.class));
            }
        });
        ach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.fromSettings = false;
                startActivity(new Intent(MainActivity.this, AchievementsActivity.class));
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });

    }


    public static void printLog(String newIn){

        Log.i("MyInfo", " " + newIn);
    }



}
