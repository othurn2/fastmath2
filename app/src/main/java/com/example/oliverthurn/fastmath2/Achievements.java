package com.example.oliverthurn.fastmath2;

/**
 * Created by oliverthurn on 12/7/16.
 */
public class Achievements {

    protected boolean fiveLevels;
    protected boolean tenLevels;
    protected boolean fifteenLevels;

    protected int golds;
    protected int silvers;
    protected int bronzes;

    public Achievements(){
        fiveLevels = false;
        tenLevels = false;
        fifteenLevels = false;
        golds = 0;
        silvers = 0;
        bronzes = 0;
    }

    public void passedFive(){
        this.fiveLevels = true;
    }

    public void passedTen(){
        this.tenLevels = true;
    }

    public void passedFifteen(){
        this.fifteenLevels = true;
    }

    public void addGold(){
        this.golds++;
    }

    public void addSilver(){
        this.silvers++;
    }

    public void addBronze(){
        this.bronzes++;
    }

    public boolean isFiveLevels() {
        return fiveLevels;
    }

    public boolean isTenLevels() {
        return tenLevels;
    }

    public boolean isFifteenLevels() {
        return fifteenLevels;
    }

    public int getGolds() {
        return golds;
    }

    public int getSilvers() {
        return silvers;
    }

    public int getBronzes() {
        return bronzes;
    }
}
