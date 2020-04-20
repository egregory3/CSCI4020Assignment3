package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class simon_rewind extends MainActivity {
    Random randomNumber;

    private SoundPool sP;

    private Set<Integer> sL;

    ArrayList<Integer> pattern = new ArrayList<Integer>();
    ArrayList<Integer> currentRun = new ArrayList<Integer>();

    Button red;
    Button green;
    Button yellow;
    Button blue;

    int blueId;
    int greenId;
    int redId;
    int yellowId;
    int roundCount;

    boolean playerTurn;

    double delay;

    int minRandom;
    int maxRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_simon_rewind);

        roundCount = 0;

        randomNumber = new Random();

        sL = new HashSet<Integer>();

        blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);
        blue.setSoundEffectsEnabled(false);


        red = findViewById(R.id.btn_red);
        addClickEffect(red);
        red.setSoundEffectsEnabled(false);

        green = findViewById(R.id.btn_green);
        addClickEffect(green);
        green.setSoundEffectsEnabled(false);

        yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);
        yellow.setSoundEffectsEnabled(false);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");

        Button start = findViewById(R.id.startButton);
        start.setSoundEffectsEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startGame();


            }
        });


    }

    public void onResume() {

        super.onResume();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        sP = spBuilder.build();

        sP.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    sL.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

        blueId = sP.load(this, R.raw.blue, 1);
        findViewById(R.id.btn_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(blueId);
            }
        });

        greenId = sP.load(this, R.raw.green, 1);
        findViewById(R.id.btn_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(greenId);
            }
        });

        redId = sP.load(this, R.raw.red, 1);
        findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(redId);
            }
        });

        yellowId = sP.load(this, R.raw.yellow, 1);
        findViewById(R.id.btn_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(yellowId);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sP != null) {
            sP.release();
            sP = null;

            sL.clear();
        }
    }


    private void startGame() {

        playerTurn = false;

        delay = 0;

        minRandom = 1;
        maxRandom = 4;

        int i = randomNumber.nextInt((maxRandom - minRandom) + 1) + minRandom;
        currentRun.add(i);
        for (int j = 0; j < currentRun.size(); j++) {


            i = currentRun.get(j);

            if (i == 1)
                pressGreen();
            if (i == 2)
                pressRed();
            if (i == 3)
                pressBlue();
            if (i == 4)
                pressYellow();


        }
        roundCount++;
        Log.i("ROUNDS", "The number of rounds is  " + roundCount);
    }


    //function to play sound attached to soundId
    private void playSound(int soundId) {
        if (sL.contains(soundId)) {
            sP.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    //action taken when random selection is green button
    private void pressGreen() {

        green.setPressed(true);
        playSound(greenId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                green.setPressed(false);
            }
        }, 500);
    }

    //action taken when random selection is red button
    private void pressRed() {

        red.setPressed(true);
        playSound(redId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                red.setPressed(false);
            }
        }, 500);
    }

    //action taken when random selection is blue button
    private void pressBlue() {

        blue.setPressed(true);
        playSound(blueId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                blue.setPressed(false);
            }
        }, 500);
    }

    //action taken when random selection is blue;
    private void pressYellow() {

        yellow.setPressed(true);
        playSound(yellowId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                yellow.setPressed(false);
            }
        }, 550);
    }


}