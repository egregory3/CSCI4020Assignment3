package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class simon_rewind extends MainActivity   {
    Random randomNumber;

    private SoundPool sP;

    private Set<Integer> sL;

    ArrayList<Integer> pattern = new ArrayList<Integer>();
    ArrayList<Integer> currentRun = new ArrayList<Integer>();
    final ArrayList<Integer> tempArrayList = new ArrayList<Integer>();

    boolean gameOver=false;

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


    int minRandom;
    int maxRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_simon_rewind);

        roundCount = 0;

        randomNumber = new Random();

        sL = new HashSet<Integer>();

        blue = findViewById(R.id.btn_blue1);
        addClickEffect(blue);
        blue.setSoundEffectsEnabled(false);


        red = findViewById(R.id.btn_red1);
        addClickEffect(red);
        red.setSoundEffectsEnabled(false);

        green = findViewById(R.id.btn_green1);
        addClickEffect(green);
        green.setSoundEffectsEnabled(false);

        yellow = findViewById(R.id.btn_yellow1);
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
        findViewById(R.id.btn_blue1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=2;
                playSound(blueId);
                tempArrayList.add(temp);
                Toast.makeText(simon_rewind.this, "Button Press Added2", Toast.LENGTH_SHORT).show();
            }
        });

        greenId = sP.load(this, R.raw.green, 1);
        findViewById(R.id.btn_green1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=0;
                playSound(greenId);
                tempArrayList.add(temp);
                Toast.makeText(simon_rewind.this, "Button Press Added0", Toast.LENGTH_SHORT).show();
            }
        });

        redId = sP.load(this, R.raw.red, 1);
        findViewById(R.id.btn_red1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=1;
                tempArrayList.add(temp);
                Toast.makeText(simon_rewind.this, "Button Press Added1", Toast.LENGTH_SHORT).show();
                playSound(redId);
            }
        });

        yellowId = sP.load(this, R.raw.yellow, 1);
        findViewById(R.id.btn_yellow1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=3;
                tempArrayList.add(temp);
                Toast.makeText(simon_rewind.this, "Button Press Added3", Toast.LENGTH_SHORT).show();
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
            roundCount=0;


            roundCount++;
            Log.i("ROUNDS", "The number of rounds is  " + roundCount);

            playerTurn = false;

            int i = randomNumber.nextInt(4);
            pattern.add(i);
            playPattern();
            tempArrayList.clear();
            Toast.makeText(simon_rewind.this, "Start User Input", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    userInput();
                }
            }, (1500 * roundCount));



    }

    private void userInput(){

        for(int i=0;i<pattern.size();i++) {
            final int temp=i;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    if(tempArrayList.get(temp)==pattern.get(temp))
                    {
                        Toast.makeText(simon_rewind.this, "Round Cleared", Toast.LENGTH_SHORT).show();
                    }
                    if(tempArrayList.get(temp)!=pattern.get(temp))
                    {
                        Toast.makeText(simon_rewind.this, "You Lose", Toast.LENGTH_SHORT).show();
                        gameOver=true;
                    }

                }
            }, 2500);

        }
    }

    private void playPattern(){

        double delay = 1;
        double defaultWaitTime=600;
        for(int i =0; i< pattern.size();i++)
        {
            final int temp = pattern.get(i);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (temp == 0)
                        pressGreen();
                    if (temp == 1)
                        pressRed();
                    if (temp == 2)
                        pressBlue();
                    if (temp== 3)
                        pressYellow();
                }
            }, (long) (delay*defaultWaitTime));
            delay++;
        }

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
        }, 500);
    }




}