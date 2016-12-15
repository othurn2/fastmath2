package com.example.oliverthurn.fastmath2;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AchievementsActivity extends AppCompatActivity {

    protected static TextView golds;
    protected static TextView silvers;
    protected static TextView bronzes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        golds = (TextView)findViewById(R.id.goldAchTV);
        silvers = (TextView)findViewById(R.id.silverAchTV);
        bronzes = (TextView)findViewById(R.id.bronzeAchTV);
        setStars();
    }

    public void setStars(){

        String a = Integer.toString(Game.goldCounter);
        golds.setText(a);

        String b = Integer.toString(Game.silverCounter);
        silvers.setText(b);

        String c = Integer.toString(Game.bronzeCounter);
        bronzes.setText(c);
    }
}
