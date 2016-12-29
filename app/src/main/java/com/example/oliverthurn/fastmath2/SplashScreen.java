package com.example.oliverthurn.fastmath2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "fGBD8ojWxNytjnO7Y1Bg0dV6o";
    private static final String TWITTER_SECRET = "	XSVDR0F5J9OsEgGFKa57BDVZVylLaU4KIpda1SOuIpcflRFZwa";
    private static final float SCREEN_DP_THRESHOLD = 16.0f;


    protected float imageWidth = 76;
    protected final long  FIRST_TIME = 2000;
    protected final long SECOND_TIME = 2250;
    protected final long THIRD_TIME = 2500;
    protected final long FOURTH_TIME = 2750;

    protected ImageView fButton;
    protected ImageView aButtonFast;
    protected ImageView sButton;
    protected ImageView tButtonFast;
    protected ImageView mButton;
    protected ImageView aButtonMath;
    protected ImageView tButtonMath;
    protected ImageView hButton;
    protected ImageView otApps;

    protected float screenWidth;
    protected float screenHeight;
    protected float offScreenRightX;
    protected float offScreenHeight;
    protected float offScreenLeftX;
    protected static float scale;


    protected float firstButtonLocX;
    protected float firstButtonLocY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_splash_screen);


        // Creating all the buttons to come into the view
        fButton = (ImageView) findViewById(R.id.fImageView);
        aButtonFast = (ImageView) findViewById(R.id.aFastImageView);
        sButton = (ImageView) findViewById(R.id.sImageView);
        tButtonFast = (ImageView) findViewById(R.id.tFastImageView);
        mButton = (ImageView) findViewById(R.id.mImageView);
        aButtonMath = (ImageView) findViewById(R.id.aMathImageView);
        tButtonMath = (ImageView) findViewById(R.id.tMathImageView);
        hButton = (ImageView) findViewById(R.id.hImageView);
        otApps = (ImageView) findViewById(R.id.otappsView);

        // Starting a DisplayMetrics to use for positioning throughout
        scale = getResources().getDisplayMetrics().density;
        MainActivity.printLog("scale" + scale);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = (int) (displayMetrics.widthPixels);
        screenHeight = (int) (displayMetrics.heightPixels);

        // Have to multiply the image width by the scale (screens DPI)
        imageWidth = imageWidth * scale;

        Log.i("MyInfoSplash", "screenWidth = " + screenWidth);
        Log.i("MyInfoSplash", "screenHeight = " + screenHeight);


        firstButtonLocX = ((screenWidth / 4) * 2) - (2 * imageWidth);
        firstButtonLocY = ((screenHeight / 4) * 2 - (4 * imageWidth));
        Log.i("MyInfoSplash", "firstButLocX = " + firstButtonLocX);
        Log.i("MyInfoSplash", "firstButLocY = " + firstButtonLocY);


        fButton.setImageResource(R.drawable.flarge);
        aButtonFast.setImageResource(R.drawable.alarge);
        sButton.setImageResource(R.drawable.slarge);
        tButtonFast.setImageResource(R.drawable.tlarge);

        mButton.setImageResource(R.drawable.mlarge);
        aButtonMath.setImageResource(R.drawable.alarge);
        tButtonMath.setImageResource(R.drawable.tlarge);
        hButton.setImageResource(R.drawable.hlarge);


        // Starting all letter ImageViews off the screen
        offScreenRightX = 0 - (imageWidth * 2);
        offScreenLeftX = screenWidth + (imageWidth * 2);
        offScreenHeight = screenHeight / 4;  // Because there are four views on each side off the screen
        MainActivity.printLog("imageWidth" + imageWidth);


        fButton.setX(offScreenRightX - 200);
        fButton.setY(offScreenHeight); // will be at the top left 2x ImageView width

        aButtonFast.setX(offScreenRightX);
        aButtonFast.setY(offScreenHeight * 2); // 2x ImageView width,  1/4 down the height

        sButton.setX(offScreenRightX);
        sButton.setY(offScreenHeight * 3);

        tButtonFast.setX(offScreenRightX);
        tButtonFast.setY(offScreenHeight * 4);

        mButton.setX(offScreenLeftX);
        mButton.setY(offScreenHeight);

        aButtonMath.setX(offScreenLeftX);
        aButtonMath.setY(offScreenHeight * 2);

        tButtonMath.setX(offScreenLeftX);
        tButtonMath.setY(offScreenHeight * 3);

        hButton.setX(offScreenLeftX);
        hButton.setY(offScreenHeight * 4);


        // Creating a animationThread to run the splash screens animation that should happen before the main menu screen is loaded
        // the thread needs to be run on the main UI thread below.
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2500); // Had to change the times of sleep because it was taking a long time to open the app
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                imageAnimation();

            }
        });


        // Creating a thread that will open the games main menu screen after the animations have been completed
        // This thread can be ran on its own. Its only purpose is to delay the opening of the MainActivity intent.
        final Thread openGame = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000); // Had to change the times of sleep because it was taking a long time to open the app
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        runOnUiThread(animationThread); // Running the animation thread on the UI thread.
        openGame.start(); // Running the thread that will open the main menu.
    }


    public void imageAnimation() {

        // Creating an animatorSet to contain all the animations on the splash screen
        AnimatorSet animatorThreadSet = new AnimatorSet();

        /* Creating all the animations for FAST     *       *       *       *     *       *       *     *       *       */
        ObjectAnimator fButtonAnimX = ObjectAnimator.ofFloat(fButton, "X", fButton.getX(), firstButtonLocX );
        fButtonAnimX.setDuration(FOURTH_TIME);
        ObjectAnimator fButtonAnimY = ObjectAnimator.ofFloat(fButton, "Y", fButton.getY(), firstButtonLocY);
        fButtonAnimY.setDuration(FOURTH_TIME);

        ObjectAnimator aButtonFastAnimX = ObjectAnimator.ofFloat(aButtonFast, "X", aButtonFast.getX(), firstButtonLocX + (imageWidth));
        aButtonFastAnimX.setDuration(THIRD_TIME);
        ObjectAnimator aButtonFastAnimY = ObjectAnimator.ofFloat(aButtonFast, "Y", aButtonFast.getY(), firstButtonLocY);
        aButtonFastAnimY.setDuration(THIRD_TIME);

        ObjectAnimator sButtonAnimX = ObjectAnimator.ofFloat(sButton, "X", sButton.getX(), firstButtonLocX + (imageWidth * 2));
        sButtonAnimX.setDuration(SECOND_TIME);
        ObjectAnimator sButtonAnimY = ObjectAnimator.ofFloat(sButton, "Y", sButton.getY(), firstButtonLocY);
        sButtonAnimY.setDuration(SECOND_TIME);

        ObjectAnimator tFastButtonAnimX = ObjectAnimator.ofFloat(tButtonFast, "X", tButtonFast.getX(), firstButtonLocX + (imageWidth * 3));
        tFastButtonAnimX.setDuration(FIRST_TIME);
        ObjectAnimator tFastButtonAnimY = ObjectAnimator.ofFloat(tButtonFast, "Y", tButtonFast.getY(), firstButtonLocY);
        tFastButtonAnimY.setDuration(FIRST_TIME);

        /*      *       *       *       *       *       *       *       *      *       *       *       *       *       *        */




        /* Creating all the animations for MATH     *       *       *       *     *       *       *     *       *       *       */

        ObjectAnimator mButtonAnimX = ObjectAnimator.ofFloat(mButton, "X", mButton.getX(), firstButtonLocX);
        mButtonAnimX.setDuration(FIRST_TIME);
        ObjectAnimator mButtonAnimY = ObjectAnimator.ofFloat(mButton, "Y", mButton.getY(), firstButtonLocY + (imageWidth));
        mButtonAnimY.setDuration(FIRST_TIME);

        ObjectAnimator aMathButtonAnimX = ObjectAnimator.ofFloat(aButtonMath, "X", aButtonMath.getX(), firstButtonLocX + (imageWidth));
        aMathButtonAnimX.setDuration(SECOND_TIME);
        ObjectAnimator aMathButtonAnimY = ObjectAnimator.ofFloat(aButtonMath, "Y", aButtonMath.getY(), firstButtonLocY + (imageWidth));
        aMathButtonAnimY.setDuration(SECOND_TIME);

        ObjectAnimator tMathButtonAnimX = ObjectAnimator.ofFloat(tButtonMath, "X", tButtonMath.getX(), firstButtonLocX + (imageWidth * 2));
        tMathButtonAnimX.setDuration(THIRD_TIME);
        ObjectAnimator tMathButtonAnimY = ObjectAnimator.ofFloat(tButtonMath, "Y", tButtonMath.getY(), firstButtonLocY + (imageWidth));
        tMathButtonAnimY.setDuration(THIRD_TIME);

        ObjectAnimator hButtonAnimX = ObjectAnimator.ofFloat(hButton, "X", hButton.getX(), firstButtonLocX + (imageWidth * 3));
        hButtonAnimX.setDuration(FOURTH_TIME);
        ObjectAnimator hButtonAnimY = ObjectAnimator.ofFloat(hButton, "Y", hButton.getY(), firstButtonLocY + (imageWidth));
        hButtonAnimY.setDuration(FOURTH_TIME);

        /*      *       *       *       *       *       *       *       *      *       *       *       *       *       *        */

//        ObjectAnimator appsAlpha = ObjectAnimator.ofFloat(otApps, "Alpha", 1f);
//        appsAlpha.setDuration(2500);


        animatorThreadSet.play(fButtonAnimX).with(fButtonAnimY);
        animatorThreadSet.play(aButtonFastAnimX).with(aButtonFastAnimY);
        animatorThreadSet.play(sButtonAnimX).with(sButtonAnimY);
        animatorThreadSet.play(tFastButtonAnimX).with(tFastButtonAnimY);
        animatorThreadSet.play(mButtonAnimX).with(mButtonAnimY);
        animatorThreadSet.play(aMathButtonAnimX).with(aMathButtonAnimY);
        animatorThreadSet.play(tMathButtonAnimX).with(tMathButtonAnimY);
        animatorThreadSet.play(hButtonAnimX).with(hButtonAnimY);
//        animatorThreadSet.play(appsAlpha).after(1000);


        animatorThreadSet.start();
    }
}

