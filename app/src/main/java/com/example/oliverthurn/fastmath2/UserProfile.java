package com.example.oliverthurn.fastmath2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {

    protected Button logoutButton;
    protected FirebaseAuth mFBAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected Button save;

    protected TextView accoutName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        accoutName = (TextView)findViewById(R.id.userNameTVProf);

        accoutName.setText(MainActivity.uniqUID);

        logoutButton = (Button) findViewById(R.id.logoutButtonProf);
        mFBAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFBAuth.signOut();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFBAuth.addAuthStateListener(mAuthListener);
    }
}
