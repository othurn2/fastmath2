package com.example.oliverthurn.fastmath2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {


    /* ================================= CLASS CONSTANTS ================================= */

    private static final String TOTAL_SCORE_TEXT = "Total: ";
    private static final int NUM_COLUMNS_ROWS = 4;
    public static final float LARGE_TEXT_SIZE = 45;
    public static final float SMALL_TEXT_SIZE = 25;
    private static final int TEXT_COLOR = Color.BLACK;
    private static final String HEADER_STAR_TEXT = "x";
    private static final int TOUCH_SOUND = 1;
    private static final int LEVEL_UP_SOUND = 2;
    public static final int GOLD_POINTS = 3;
    public static final int SILVER_POINTS = 2;
    public static final int BRONZE_POINTS = 1;
    public static int levelTracker;
    protected static int goldCounter;
    protected static int silverCounter;
    protected static int bronzeCounter;

    /* ============================== CLASS VIEW VARIABLES ================================ */

    protected GridLayout displayGrid;
    protected GridLayout buttonGrid;
    protected GridLayout starGrid;
    protected TextView lvlDisplay;
    protected TextView totalScoreDisplay;
    protected TextView numGoldStars;
    protected TextView numSilverStars;
    protected TextView numBronzeStars;
    protected Button addingDisplay;
    protected ImageButton deleteButton;
    protected ImageButton settingsButton;
    protected TextView clickCounterDisplay;
    protected ImageView goldStar;
    protected ImageView silverStar;
    protected ImageView bronzeStar;


    protected static Sounds sounds;
    protected AudioManager audioManager;

    /* ============================ CLASS VALUE HOLDER VARIABLES ============================ */
    protected String levelText = "Lvl:";
    protected String scoreText;
    protected String displayText;
    protected String clickText;

    protected int highestNum;
    protected int clickCounter;
    protected int sinceWinCounter;
    protected int lvl;
    protected int displayNum;
    protected int score = 0;
    protected int totalScore = 0;
    protected int lastButtonPressedId;
    protected int deleteCount;

    protected int arrayPlace = 0;

    protected boolean twoMatch;
    protected boolean threeMatch;
    protected boolean fourMatch;
    protected boolean hasDelete;

    protected ArrayList<Integer> buttonsPressed = new ArrayList<>();


    /* ================================= RESOURCE ARRAYS ==================================== */

    private static final int[] smBlockImg = {R.drawable.lightblue50, R.drawable.greenblock50, R.drawable.redblock50, R.drawable.yellowblock50, R.drawable.purpleblock50};
    private static final int[] lgBlockImg ={R.drawable.lightblueblock150, R.drawable.greenblock150, R.drawable.redblock150, R.drawable.yellowblock150, R.drawable.purpleblock150};
    private static final int[] starBlock15 = {R.drawable.bronzestar15, R.drawable.silverstar15, R.drawable.goldstar15};
    private static final int[] starBlock25 = {R.drawable.bronzestar25, R.drawable.silverstar25, R.drawable.goldstar25};
    private static final int[] starBlock50 = {R.drawable.bronzestar50, R.drawable.silverstar50, R.drawable.goldstar50};

    /* ================================= START OF CLASS ==================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // Initializing the UI elements
        displayGrid = (GridLayout)findViewById(R.id.numDisplayGrid);
        buttonGrid = (GridLayout)findViewById(R.id.buttonGrid);
        lvlDisplay = (TextView)findViewById(R.id.lvlTV);
        totalScoreDisplay = (TextView)findViewById(R.id.topScoreTV);
        numGoldStars = (TextView)findViewById(R.id.goldTV);
        numSilverStars = (TextView)findViewById(R.id.silverTV);
        numBronzeStars = (TextView)findViewById(R.id.bronzeTV);
        addingDisplay = (Button)findViewById(R.id.additionButton);
        deleteButton = (ImageButton)findViewById(R.id.deleteButton);
        settingsButton = (ImageButton)findViewById(R.id.settingsButton);
        clickCounterDisplay = (TextView)findViewById(R.id.clickCounterTV);
        starGrid = (GridLayout)findViewById(R.id.starGridView);
        goldStar = new ImageView(this);
        silverStar = new ImageView(this);
        bronzeStar = new ImageView(this);
        sounds = new Sounds(this);

        clickCounter = 0;
        sinceWinCounter = 0;
        goldCounter = 0;
        silverCounter = 0;
        bronzeCounter = 0;
        lvl = 0;
        scoreText = Integer.toString(score);
        highestNum = 99;
        audioManager = (AudioManager)Game.this.getSystemService(Context.AUDIO_SERVICE);


        createNumDisplayGrid(highestNum);
        createButtonGrid(highestNum);
        createAdditionDisplay();
        createLevelDisplay();
        createTotalScoreDisplay();
        createClickCounterDisplay();
        createSettingsButton();
        createStarDisplay();
        createDeleteButton();


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        InstructionsFrag inst = new InstructionsFrag();
        inst.show(fm, "Instructions");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.isMute){
            audioManager.setMode(AudioManager.ADJUST_MUTE);
        } else {
            audioManager.setMode(AudioManager.ADJUST_UNMUTE);
        }
    }

    /* ================================= METHODS TO SET UI ELEMENTS ==================================== */

    /* The display that will hold the number to add to */
    public void createNumDisplayGrid(int high){

        int digits = -1;
        Random randDisplay = new Random();
        displayNum = randDisplay.nextInt(high) + 10;

        // Replace finalDisplayNumber with displayNum finalDisplayNumber = displayNum;
        String highString = Integer.toString(high);

        if (highString.length() == 1 ){
                digits = 1;
            } else if ( highString.length() == 2){
                digits = 2;
            } else if( highString.length() == 3) {
                digits = 3;
        }


        Log.i("Info: randDisplayNum", "" + displayNum);
        displayText = Integer.toString(displayNum);
        Log.i("Info: displayText", displayText);
        Log.i("displaytextlength", "" + displayText.length());

        displayGrid.removeAllViews();
        displayGrid.setRowCount(1);
        displayGrid.setColumnCount(digits);

        Log.i("Info: randDisplayNum", "" + displayNum);
        displayText = Integer.toString(displayNum);
        Log.i("Info: displayText", displayText);
        Log.i("displaytextlength", "" + displayText.length());

        Random random = new Random();
        int numToGet = random.nextInt(lgBlockImg.length);


        String holder = Integer.toString(displayNum);
        Log.i("Info Holder", holder);

        TextView textView = new TextView(this);
        textView.setBackgroundResource(lgBlockImg[numToGet]);

        // Setting text in image
        textView.setTextSize(LARGE_TEXT_SIZE);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(TEXT_COLOR);

        // Setting that char into text
        textView.setText(holder);

        displayGrid.addView(textView);
    }

    /* The display that will hold the buttons */

    public void createButtonGrid(int high){

        twoMatch = false;
        threeMatch = false;
        fourMatch = false;

        buttonGrid.removeAllViews();
        buttonGrid.setRowCount(NUM_COLUMNS_ROWS);
        buttonGrid.setColumnCount(NUM_COLUMNS_ROWS);

        int[] sortableArray = new int[(NUM_COLUMNS_ROWS * NUM_COLUMNS_ROWS)];


        for (int i = 0; i < (buttonGrid.getRowCount() * buttonGrid.getColumnCount()); i++){

            final Button button = new Button(this);
            button.setId(i);

            Random rand = new Random();
            int a = rand.nextInt(smBlockImg.length);
            int buttonNum = (displayNum) % (rand.nextInt(displayNum) + 1) + 1;

            sortableArray[i] = buttonNum;

            button.setBackgroundResource(smBlockImg[a]);
            button.setTextSize(LARGE_TEXT_SIZE);
            button.setTextColor(TEXT_COLOR);
            button.setId(i);

            button.setText(Integer.toString(buttonNum));

            /* This and check for match is where most of the work happens*/
            /* */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastButtonPressedId = button.getId();
                    buttonsPressed.add(lastButtonPressedId);
                    score += Integer.parseInt(button.getText().toString());
                    addingDisplay.setText(Integer.toString(score));
                    clickCounter++;
                    sinceWinCounter++;
                    clickText = Integer.toString(clickCounter);
                    clickCounterDisplay.setText(clickText);
                    button.setClickable(false);
                    button.animate().alpha(0);
                    starAnimation();
                    checkForMatch();



                }

            });
            buttonGrid.addView(button);
        }

        bubble(sortableArray);
        findAdditionMatch(sortableArray, displayNum);


    }

    /* Display for the click counter */
    public void createClickCounterDisplay(){
        clickCounterDisplay.setTextColor(TEXT_COLOR);
        clickCounterDisplay.setTextSize(SMALL_TEXT_SIZE);
        clickCounterDisplay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        clickText = "" + clickCounter;
        clickCounterDisplay.setText(clickText);
    }

    /* Display for top level counter */
    public void createLevelDisplay(){
        lvlDisplay.setTextSize(SMALL_TEXT_SIZE);
        lvlDisplay.setTextColor(TEXT_COLOR);
        lvlDisplay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        lvlDisplay.setText(levelText + lvl);

    }

    /* Display for adding */
    public void createAdditionDisplay(){

        // this needs to be set to gold
        addingDisplay.setBackgroundResource(R.drawable.goldblock50);
        addingDisplay.setTextColor(TEXT_COLOR);
        addingDisplay.setTextSize(LARGE_TEXT_SIZE);
        addingDisplay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        addingDisplay.setText(Integer.toString(score));
    }

    public void createDeleteButton(){

        deleteButton.setAlpha(0f);
        deleteButton.setClickable(false);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasDelete){
                    runDeleteButton(lastButtonPressedId);
                    MainActivity.printLog("delete count" + deleteCount);
                } else {
                    deleteButton.setAlpha(0f);
                    deleteButton.setClickable(false);
                }
            }
        });
    }

    public void runDeleteButton(int buttonID){

        if (deleteCount > 0) {
            deleteCount--;
            printArrray();
            //int b = buttonsPressed.get(arrayPlace);
            Button temp = (Button) buttonGrid.getChildAt(lastButtonPressedId);
            String holder = temp.getText().toString();
            int toDel = Integer.parseInt(holder);
            score -= toDel;
            holder = Integer.toString(score);
            addingDisplay.setText(holder);
            arrayPlace++;
            clickCounter --;
            clickText = Integer.toString(clickCounter);
            clickCounterDisplay.setText(clickText);

            if (deleteCount == 0){
                //buttonsPressed.clear();
                arrayPlace = 0;
                temp.setAlpha(0f);
                temp.setClickable(false);
            } else {
                temp.setAlpha(1f);
                temp.setClickable(true);
            }


        } else {
            deleteButton.setClickable(false);
            deleteButton.setAlpha(0f);
        }
    }

    // Need creative ways to earn deletes
    // also need correct placement of this method to ony change when level changes
    public void checkForDeletes(){

        if (lvl > 0) {
            deleteButton.setClickable(true);
            deleteButton.setAlpha(1f);
            if (lvl > 0 && lvl < 6) {
                hasDelete = true;
                deleteCount++;
            } else {
                // When over level 5 this takes away the delete button when deletes are still available.
                hasDelete = false;
            }
        }
    }
    /* Display for bottom score */
    public void createSettingsButton(){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                Settings settings = new Settings();
                settings.show(fm, "Settings Fragment");
            }
        });

    }

    /* Display for top score */
    public void createTotalScoreDisplay(){
        scoreText = Integer.toString(totalScore);
        totalScoreDisplay.setText(TOTAL_SCORE_TEXT + scoreText);
        totalScoreDisplay.setTextColor(TEXT_COLOR);
        totalScoreDisplay.setTextSize(SMALL_TEXT_SIZE);
    }

    /* Display for star grid */
    public void createStarDisplay(){

        starGrid.removeAllViews();

        goldStar.setImageResource(R.drawable.goldstar25);
        silverStar.setImageResource(R.drawable.silverstar25);
        bronzeStar.setImageResource(R.drawable.bronzestar25);

        starGrid.addView(goldStar, 0);
        starGrid.addView(silverStar,1);
        starGrid.addView(bronzeStar, 2);

        if (!twoMatch){
            goldStar.setAlpha(0f);
        } else {
            goldStar.setAlpha(1f);
        }
        if (!threeMatch){
            silverStar.setAlpha(0f);
        } else {
            silverStar.setAlpha(1f);
        }
        if (!fourMatch){
            bronzeStar.setAlpha(0f);
        } else {
            bronzeStar.setAlpha(1f);
        }

    }

    /* ================================= BEHIND THE SCENES METHODS ==================================== */

    /* The other place a lot of the work happens */
    public void checkForMatch() {



        if (score == displayNum){
            lvl++;
            checkForDeletes();
            lvlDisplay.setText(levelText + lvl);
            totalScore += clickCounter;
            scoreText = TOTAL_SCORE_TEXT + Integer.toString(totalScore);
            totalScoreDisplay.setText(scoreText);
            score = 0;
            displayNum = 0;
            trackHeaderStars();
            answerViewAnimation(displayGrid, highestNum * ((lvl + 1 )/ 2));
            createButtonGrid(highestNum * ((lvl + 1) / 2));
            createAdditionDisplay();
            createStarDisplay();
            sounds.playSound(LEVEL_UP_SOUND);
            clickCounter = 0;
            sinceWinCounter = 0;

        } else if (score > displayNum){

            createAdditionViewAnimation(addingDisplay);
            levelTracker = lvl;
            lvl = 0;
            lvlDisplay.setText(levelText + lvl);
            score = 0;
            displayNum = 0;
            clickCounter = 0;
            clickText = Integer.toString(clickCounter);
            clickCounterDisplay.setText(clickText);
            createNumDisplayGrid(highestNum);
            createButtonGrid(highestNum);
            createAdditionDisplay();
            createStarDisplay();
            sinceWinCounter = 0;

        } else {
            sounds.playSound(TOUCH_SOUND);
        }
    }

    /**
     * Bubble sort method to be used inside of the createButtonGrid
     * */

    public static void bubble(int[] array){

        for(int count = 0; count < array.length - 1; count++){
            boolean swapped = false;

            for(int i = 1; i < array.length; i++){
                int a = array[i-1];
                int b = array[i];
                if (a < b ){
                    int c = a;
                    array[i-1] = b;
                    array[i] = c;
                    swapped = true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }

    public void findAdditionMatch(int[] array, int match){

        for(int i = 0; i < array.length - 3; i++){
            int a = array[i];
            if(twoMatch && threeMatch){
                break;
            }
            for(int j = 1; j < array.length - 2; j++){
                int b = array[j];
                if (a + b == match){
                    //Log.i("MyInfo"," There is a match in a + b");
                    twoMatch = true;
                }
                if(twoMatch && threeMatch && fourMatch){
                    break;
                }
                for (int k = 2; k < array.length - 1; k++){
                    int c = array[k];
                    if (a + b + c == match){
                        //Log.i("MyInfo"," There is a match in a + b + c");
                        threeMatch = true;
                    }
                    if(twoMatch && threeMatch && fourMatch){
                        break;
                    }

                    for (int l = 3; l < array.length; l++){
                        int d = array[l];
                        if (a + b + c + d == match){
                            fourMatch = true;
                        }
                        if (twoMatch && threeMatch && fourMatch){
                            break;
                        }
                    }

                }
            }
        }
        Log.i("MyInfo","twoMatch" + twoMatch);
        Log.i("MyInfo","threeMatch" + threeMatch);
        Log.i("MyInfo","fourMatch" + fourMatch);

    }

    public void trackHeaderStars(){

        if (sinceWinCounter == 2){
            goldCounter++;
            numGoldStars.setText(HEADER_STAR_TEXT + goldCounter);
            //String a = Integer.toString(goldCounter);
            //AchievementsActivity.golds.setText(a);
        }
        if (sinceWinCounter == 3){
            silverCounter++;
            numSilverStars.setText(HEADER_STAR_TEXT + silverCounter);
            //String b = Integer.toString(silverCounter);
            //AchievementsActivity.silvers.setText(b);
        }
        if (sinceWinCounter == 4){
            bronzeCounter++;
            numBronzeStars.setText(HEADER_STAR_TEXT + bronzeCounter);
            //String c = Integer.toString(bronzeCounter);
            //AchievementsActivity.bronzes.setText(c);
        }
    }


    /* ================================= METHODS TO SET UI ANIMATIONS ==================================== */

    public void answerViewAnimation(View view, int high){

        float originalX = view.getX();
        float originalY = view.getY();

        Log.i("ScoreViewOGX", "" + originalX);
        Log.i("ScoreViewOGY", "" + originalY);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        final float endX = (float) dm.widthPixels;
        final float endY = (float) dm.heightPixels;

        AnimatorSet answerSet = new AnimatorSet();

        ValueAnimator moveXOne =ObjectAnimator.ofFloat(view, "X", (float) (endX + view.getWidth()));
        moveXOne.setDuration(500);

        ValueAnimator moveXTwo = ObjectAnimator.ofFloat(view, "X", (float) 0 - view.getWidth(), originalX);
        moveXTwo.setDuration(500);

        answerSet.play(moveXOne);
        createNumDisplayGrid(high);
        answerSet.play(moveXTwo).after(moveXOne);
        answerSet.start();
    }

    public void createAdditionViewAnimation(View view){


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        final float originalX = view.getX();
        final float originalY = view.getY();

        final float screenX = (float) dm.widthPixels;
        final float screenY = (float) dm.heightPixels;

        float moveToX = ((screenX / 2) - (view.getWidth() / 2));
        float moveToY = ((screenY / 2) - (view.getHeight() / 2));

        AnimatorSet translateAnimatorSet = new AnimatorSet();

        ValueAnimator translateToX = ObjectAnimator.ofFloat(view, "X", moveToX);
        translateToX.setDuration(2000);

        ValueAnimator translateToY = ObjectAnimator.ofFloat(view, "Y", moveToY);
        translateToY.setDuration(2000);

        ValueAnimator rotateView = ObjectAnimator.ofFloat(view, "Rotation", 360);
        rotateView.setDuration(2000);

        ValueAnimator rotateBackView = ObjectAnimator.ofFloat(view, "Rotation", 0);
        rotateBackView.setDuration(1000);

        ValueAnimator backToX = ObjectAnimator.ofFloat(view, "X", originalX);
        backToX.setDuration(1000);

        ValueAnimator backToY = ObjectAnimator.ofFloat(view, "Y", originalY);
        backToY.setDuration(1000);

        translateAnimatorSet.play(translateToX).with(translateToY).with(rotateView);
        translateAnimatorSet.play(backToX).with(backToY).with(rotateBackView).after(rotateView);
        translateAnimatorSet.start();
    }

    public void starAnimation(){
        if(twoMatch && clickCounter >= 2){
            goldStar.setAlpha(0f);
        }
        if (threeMatch && clickCounter >= 3){
            silverStar.setAlpha(0f);
        }
        if (fourMatch && clickCounter >= 4){
            bronzeStar.setAlpha(0f);
        }
    }

    public void printArrray(){
        Log.i("Arraysize", " "+buttonsPressed.size() );
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (MainActivity.connection == false){

            // If there is no internet connection all the scores are saved to the SP from the main activity and then loaded
            // Into the textviews of the achievement activity

            String g = Integer.toString(goldCounter);
            String s = Integer.toString(silverCounter);
            String b = Integer.toString(bronzeCounter);
            MainActivity.printLog("gameStop gold " + g);
            MainActivity.printLog("gameStop silver " + s);
            MainActivity.printLog("gameStop bronze " + b);
            MainActivity.editor.putString("Golds", g);
            MainActivity.editor.putString("Silvers", s);
            MainActivity.editor.putString("Bronzes", b);
            MainActivity.editor.commit();
        }
    }
}

