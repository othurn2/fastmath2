package com.example.oliverthurn.fastmath2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;

    public static boolean isMute;
    public static boolean toMute;
    public static String uniqUID = "";
    public static FirebaseAuth firstAuth;
    public static DatabaseReference firstDBREF;
    public static boolean connection;
    public static final String SP_NAME = "OfflinePrefs";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isMute = false;
        connection = isNetConnected();
        printLog("networkConn " + connection);

        sp = getSharedPreferences(SP_NAME, 0);
        editor = sp.edit();

        if (connection == true) {
            firstAuth = FirebaseAuth.getInstance();
            firstDBREF = FirebaseDatabase.getInstance().getReference();
        } else {

        }

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


        /* * * * Facebook crap * * * * */

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        /* * * * Facebook crap * * * * */

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (connection == true) {
            if (firstAuth.getCurrentUser() != null) {
                //startActivity(new Intent(LogInMain.this, MainActivity.class));
            } else {

                // Need to set smart lock to true when relesed
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders(
                        AuthUI.FACEBOOK_PROVIDER,
                        AuthUI.EMAIL_PROVIDER,
                        AuthUI.GOOGLE_PROVIDER
                ).setIsSmartLockEnabled(false).build(), RC_SIGN_IN);

            }
        } else {
            printLog("no internet else");
            // Create a new pers state here and use the achievement class to save all acievements.
            // also create pop up frag that says you are not connected to the internet and all scores may not be saved
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                // user logged in
                //startActivity(new Intent(LogInMain.this, MainActivity.class));

            } else {
                // user not authenticated
            }
        }
    }


    public static void printLog(String newIn){

        Log.i("MyInfo", " " + newIn);
    }

    // Method to check if there is a network connection
    public boolean isNetConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



}
