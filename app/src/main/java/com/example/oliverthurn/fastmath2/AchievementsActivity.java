package com.example.oliverthurn.fastmath2;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AchievementsActivity extends AppCompatActivity {

    private static final String SP_ERROR = "There was an error getting data";
    protected static TextView golds;
    protected static TextView silvers;
    protected static TextView bronzes;

    protected int goldTotal;
    protected int silverTotal;
    protected int bronzeTotal;

    protected String loadGolds = "";
    protected String loadSilvers = "";
    protected String loadBronzes = "";

    protected TextView userIDACH;

    protected ValueEventListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        if (MainActivity.connection == true) {
            userIDACH = (TextView) findViewById(R.id.userTVACH);
            userIDACH.setText(MainActivity.firstAuth.getCurrentUser().getEmail());
            userIDACH.setTextSize(Game.SMALL_TEXT_SIZE);

            golds = (TextView) findViewById(R.id.goldAchTV);
            golds.setTextSize(Game.LARGE_TEXT_SIZE);

            silvers = (TextView) findViewById(R.id.silverAchTV);
            silvers.setTextSize(Game.LARGE_TEXT_SIZE);

            bronzes = (TextView) findViewById(R.id.bronzeAchTV);
            bronzes.setTextSize(Game.LARGE_TEXT_SIZE);

        } else {
            userIDACH = (TextView) findViewById(R.id.userTVACH);
            userIDACH.setText("Current User");
            userIDACH.setTextSize(Game.SMALL_TEXT_SIZE);

            golds = (TextView) findViewById(R.id.goldAchTV);
            golds.setTextSize(Game.LARGE_TEXT_SIZE);
            golds.setText(MainActivity.sp.getString("Golds", SP_ERROR));

            silvers = (TextView) findViewById(R.id.silverAchTV);
            silvers.setTextSize(Game.LARGE_TEXT_SIZE);
            silvers.setText(MainActivity.sp.getString("Silvers", SP_ERROR));

            bronzes = (TextView) findViewById(R.id.bronzeAchTV);
            bronzes.setTextSize(Game.LARGE_TEXT_SIZE);
            bronzes.setText(MainActivity.sp.getString("Bronzes", SP_ERROR));

        }

        // Listener for value changes to DB
        setListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // If the last activity was from the settings fragment that means the game score could have been updated
                // the if here adds the counters from the game activity
                if (Settings.fromSettings) {

                    // Tests to see if the snapshot has any children, if it doesnt that could mean that the DB has not been set up
                    // for that user. Settings up data base is done in the else part of this chain
                    if (dataSnapshot.hasChildren()) {

                        Map<String, Object> setMap = (Map<String, Object>) dataSnapshot.getValue();

                        // After getting the children from the DB, they are put into a map and then converted from objects to strings
                        // any stars that have been accumulated in the last game play are added to the score pulled from the database
                        // then displayed on the screen

                        String g = setMap.get("Gold").toString();
                        goldTotal = Integer.parseInt(g);
                        goldTotal += Game.goldCounter;
                        loadGolds = Integer.toString(goldTotal);
                        golds.setText(loadGolds);

                        String s = setMap.get("Silver").toString();
                        silverTotal = Integer.parseInt(s);
                        silverTotal += Game.silverCounter;
                        loadSilvers = Integer.toString(silverTotal);
                        silvers.setText(loadSilvers);

                        String b = setMap.get("Bronze").toString();
                        bronzeTotal = Integer.parseInt(b);
                        bronzeTotal += Game.bronzeCounter;
                        loadBronzes = Integer.toString(bronzeTotal);
                        bronzes.setText(loadBronzes);


                    } else {

                        // If there was no children in the DB make them and store the last scores from the game counter
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Gold").setValue(Game.goldCounter);
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Silver").setValue(Game.silverCounter);
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Bronze").setValue(Game.bronzeCounter);

                    }
                } else {

                    // This is mostly the same as the if statement but if the activity to send it to achievements was not the settings fragment
                    // then there is no score added from the last game play.
                    if (dataSnapshot.hasChildren()) {

                        Map<String, Object> setMap = (Map<String, Object>) dataSnapshot.getValue();

                        String g = setMap.get("Gold").toString();
                        goldTotal = Integer.parseInt(g);
                        loadGolds = Integer.toString(goldTotal);
                        golds.setText(loadGolds);

                        String s = setMap.get("Silver").toString();
                        silverTotal = Integer.parseInt(s);
                        loadSilvers = Integer.toString(silverTotal);
                        silvers.setText(loadSilvers);

                        String b = setMap.get("Bronze").toString();
                        bronzeTotal = Integer.parseInt(b);
                        loadBronzes = Integer.toString(bronzeTotal);
                        bronzes.setText(loadBronzes);



                    } else {

                        // This may actually crash the game. If the activity was opened from the main activity without there being children in the
                        // DB there will be nothing stored in the Game.counter variables. If crash happens set these values to 0
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Gold").setValue(Game.goldCounter);
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Silver").setValue(Game.silverCounter);
                        MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Bronze").setValue(Game.bronzeCounter);

                        MainActivity.printLog("setListener else");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        if (MainActivity.connection == true) {
            // Adding the listener to the star child of each users uniq ID if there was an internet connection when app started
            MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").addValueEventListener(setListener);
        }
    }

    // onStop - gets the scores that were last display in the textviews converts to ints then sends them into the DB.
    // may not be necessary to have the call to change before super, figured super had a form of garbage collection
    // that deleted stored values.
    @Override
    protected void onStop() {

        if (MainActivity.connection == true) {
            loadBronzes = bronzes.getText().toString();
            int b = Integer.parseInt(loadBronzes);
            loadSilvers = silvers.getText().toString();
            int s = Integer.parseInt(loadSilvers);
            loadGolds = golds.getText().toString();
            int g = Integer.parseInt(loadGolds);

            MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Gold").setValue(g);
            MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Silver").setValue(s);
            MainActivity.firstDBREF.child(MainActivity.firstAuth.getCurrentUser().getUid()).child("Stars").child("Bronze").setValue(b);


        }

        super.onStop();

    }

}

