package com.example.oliverthurn.fastmath2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by oliverthurn on 12/7/16.
 */
public class Sounds {

    protected static SoundPool pool;
    protected static int sample1 = -1;
    protected static int sample2 = -1;
    protected static int sample3 = -1;

    public Sounds(Context context){

        pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("touchone.wav");
            sample1 = pool.load(descriptor, 0);

            descriptor = assetManager.openFd("nextlevel.wav");
            sample2 = pool.load(descriptor,0);

            descriptor = assetManager.openFd("touchthree.wav");
            sample3 = pool.load(descriptor, 0);
        } catch(IOException e){
            e.printStackTrace();
        }

    }



    public void playSound(int sound){

        if (!MainActivity.isMute){
        switch (sound) {
            case 1:
                pool.play(sample1, 1, 1, 0, 0, 1);
                break;
            case 2:
                pool.play(sample2, 1, 1, 0, 0, 1);
                break;
            case 3:
                pool.play(sample3, 1, 1, 0, 0, 1);
                break;
            }
        }
    }

    public static void muteSounds(boolean b){

        if (b == true){
            pool.setVolume(sample1,0,0);
            pool.setVolume(sample2,0,0);
            pool.setVolume(sample3,0,0);
        } else {
            pool.setVolume(sample1,1,1);
            pool.setVolume(sample2,1,1);
            pool.setVolume(sample3,1,1);
        }
    }
}
