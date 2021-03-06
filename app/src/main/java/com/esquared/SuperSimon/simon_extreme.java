//Eric Gregory and Eric Raymond
//CSCI 4020, Fall 2020
//Professor John Nicholson
//Assignment 3

package com.esquared.SuperSimon;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class simon_extreme extends MainActivity implements View.OnClickListener {

    private ArrayList<Integer> simonsPattern;
    private View[] views;
    private HashSet<Integer> soundsLoaded;
    private Handler handler;
    private SoundPool soundPool;
    private ImageView green;
    private ImageView red;
    private ImageView blue;
    private ImageView yellow;
    private ImageView purple;
    private ImageView orange;
    private int indice;
    private boolean userTurn;
    private int greenID;
    private int redID;
    private int blueID;
    private int yellowID;
    private int purpleID;
    private int orangeID;
    private int score = 0;
    private TextView scoreTV;
    private Button home;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_extreme);
        home = findViewById(R.id.btn_home);
        scoreTV = findViewById(R.id.tv_score);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        simonsPattern = new ArrayList<>();

        red = findViewById(R.id.btn_red1);
        blue = findViewById(R.id.btn_blue1);
        green = findViewById(R.id.btn_green1);
        yellow = findViewById(R.id.btn_yellow1);
        purple = findViewById(R.id.btn_purple1);
        orange= findViewById(R.id.btn_orange1);
        soundsLoaded = new HashSet<>();
        views = new View[]{red, blue, green, yellow,purple,orange};

        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }

        //START OF GAME AFTER CREATION
        indice = 0;
        userTurn = false;
        increaseSimonPattern(simonsPattern);
        disableUserInput(views);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playSimonsPattern();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);
        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(2);
        soundPool = spBuilder.build();
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    soundsLoaded.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });
        greenID = soundPool.load(this, R.raw.green, 1);
        redID= soundPool.load(this, R.raw.red, 1);
        blueID = soundPool.load(this, R.raw.blue, 1);
        yellowID = soundPool.load(this, R.raw.yellow, 1);
        purpleID= soundPool.load(this, R.raw.purple, 1);
        orangeID= soundPool.load(this, R.raw.orange, 1);

    }

    private void playSimonsPattern() {
        switch (simonsPattern.get(indice)){
            case 1:
                greenAction();
                break;
            case 2:
                redAction();
                break;
            case 3:
                blueAction();
                break;
            case 4:
                yellowAction();
                break;
            case 5:
                purpleAction();
                break;
            case 6:
                orangeAction();
                break;
        }
        indice++;
        if (indice < simonsPattern.size()) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSimonsPattern();
                }
            }, 900);
        } else {
            //Reverse the array
            // reversedPattern=reversePattern(simonsPattern);
            userTurn = true;
            enableUserInput(views);
            indice = 0;
        }
    }

    @Override
    public void onClick(View v) {
        if(userTurn==true) {
            if (comparePattern(simonsPattern, indice, v )) {
                nextRound();
            } else {
                gameOver();
            }
            switch (v.getId()) {
                case R.id.btn_green1:
                    greenAction();
                    break;
                case R.id.btn_red1:
                    redAction();
                    break;
                case R.id.btn_blue1:
                    blueAction();
                    break;
                case R.id.btn_yellow1:
                    yellowAction();
                    break;
                case R.id.btn_purple1:
                    purpleAction();
                    break;
                case R.id.btn_orange1:
                    orangeAction();
                    break;
            }
        }
    }

    private void gameOver() {
        indice = 0;
        userTurn = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(simon_extreme.this, "End Of Game", Toast.LENGTH_SHORT).show();
            }
        }, 200);
        ZeroScore();
        home.setVisibility(View.VISIBLE);
    }

    private void nextRound() {

        indice++;
        if (simonsPattern.size() == indice) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(simon_extreme.this, "Round Won", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
            indice = 0;
            userTurn = false;
            disableUserInput(views);
            increaseSimonPattern(simonsPattern);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSimonsPattern();
                }
            }, 1500);
            AddToScore();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
            soundsLoaded.clear();
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void redAction() {
        red.setImageResource(R.drawable.button_red_down);
        playSound(redID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                red.setImageResource(R.drawable.button_red);
            }
        }, 550);
    }

    private void blueAction() {
        blue.setImageResource(R.drawable.button_blue_down);
        playSound(blueID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blue.setImageResource(R.drawable.button_blue);
            }
        }, 550);
    }

    private void greenAction() {
        green.setImageResource(R.drawable.button_green_down);
        playSound(greenID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                green.setImageResource(R.drawable.button_green);
            }
        }, 550);
    }

    private void yellowAction() {
        yellow.setImageResource(R.drawable.button_yellow_down);
        playSound(yellowID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                yellow.setImageResource(R.drawable.button_yellow);
            }
        }, 550);
    }

    private void purpleAction() {
        purple.setImageResource(R.drawable.button_purple_down);
        playSound(purpleID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                purple.setImageResource(R.drawable.button_purple);
            }
        }, 550);
    }

    private void orangeAction() {
        orange.setImageResource(R.drawable.button_orange_down);
        playSound(orangeID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orange.setImageResource(R.drawable.button_orange);
            }
        }, 550);
    }


    private ArrayList<Integer> reversePattern(ArrayList<Integer> inputPattern) {

        ArrayList<Integer> temp=new ArrayList<>();
        for (int i = inputPattern.size() - 1; i >= 0; i--) {
            temp.add(inputPattern.get(i));
        }
        return temp;
    }

    private boolean comparePattern( ArrayList<Integer> simonsPattern, int index, View view) {
        int value= Integer.valueOf((String) view.getTag());
        if (simonsPattern.get(index) == value) {
            return true;
        } else {
            return false;
        }
    }

    private void increaseSimonPattern(ArrayList<Integer> simonsPattern) {
        Random r= new Random();
        int value =r.nextInt(6)+1;
        simonsPattern.add(value);
    }

    private void disableUserInput(View[] v) {
        for (int i = 0; i < v.length; i++) {
            v[i].setEnabled(false);
        }
    }

    private void enableUserInput(View[] views) {
        //enable entire game board
        for (int i = 0; i < views.length; i++) {
            views[i].setEnabled(true);
        }
    }

    private void playSound(int soundId) {
        //play required sound
        if (soundsLoaded.contains(soundId)) {
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }
    private int AddToScore(){
        score++;
        scoreTV.setText(String.valueOf(score));
        return score;
    }
    private int ZeroScore(){
        score=0;
        scoreTV.setText(String.valueOf(score));
        return score;
    }

}
