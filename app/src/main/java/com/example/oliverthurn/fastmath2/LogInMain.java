package com.example.oliverthurn.fastmath2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LogInMain extends AppCompatActivity {
    protected Button fbSignInBtn;
    protected Button gmailSignInBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_main);

        fbSignInBtn = (Button)findViewById(R.id.fbSignInButton);
        fbSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), FBSignInActivity.class));
            }
        });

        gmailSignInBtn = (Button)findViewById(R.id.gmailSignInButton);
        gmailSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GmailLogIn.class));
            }
        });


    }
}
