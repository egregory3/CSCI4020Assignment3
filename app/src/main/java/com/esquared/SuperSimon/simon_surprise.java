package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class simon_surprise extends MainActivity implements View.OnClickListener {

    private ArrayList<Integer> simonsPattern;
    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private View[] views;
    private int indice;
    private boolean userTurn;
    private SoundPool soundPool;
    private HashSet<Integer> soundsLoaded;
    private int redID;
    private int blueID;
    private int greenID;
    private int yellowID;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_surprise);

        simonsPattern = new ArrayList<>();

        one = findViewById(R.id.btn_one);
        two = findViewById(R.id.btn_two);
        three = findViewById(R.id.btn_three);
        four = findViewById(R.id.btn_four);
        soundsLoaded = new HashSet<>();
        views = new View[]{one, two, three, four};

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
        }, 1500);
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
        redID= soundPool.load(this, R.raw.red, 1);
        blueID = soundPool.load(this, R.raw.blue, 1);
        greenID = soundPool.load(this, R.raw.green, 1);
        yellowID = soundPool.load(this, R.raw.yellow, 1);

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
        }
        indice++;
        if (indice < simonsPattern.size()) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSimonsPattern();
                }
            }, 1500);
        } else {
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
                case R.id.btn_one:
                    greenAction();
                    break;
                case R.id.btn_two:
                    redAction();
                    break;
                case R.id.btn_three:
                    blueAction();
                    break;
                case R.id.btn_four:
                    yellowAction();
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
                Toast.makeText(simon_surprise.this, "End Of Game", Toast.LENGTH_SHORT).show();
            }
        }, 200);

    }

    private void nextRound() {

        indice++;
        if (simonsPattern.size() == indice) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(simon_surprise.this, "Round Won", Toast.LENGTH_SHORT).show();
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
            }, 2000);
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



    private void greenAction() {
        one.setImageResource(R.drawable.button_black_down);
        playSound(greenID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                one.setImageResource(R.drawable.button_black);
            }
        }, 550);
    }


    private void redAction() {
        two.setImageResource(R.drawable.button_black_down);
        playSound(redID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                two.setImageResource(R.drawable.button_black);
            }
        }, 550);
    }

    private void blueAction() {
        three.setImageResource(R.drawable.button_black_down);
        playSound(blueID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                three.setImageResource(R.drawable.button_black);
            }
        }, 550);
    }



    private void yellowAction() {
        four.setImageResource(R.drawable.button_black_down);
        playSound(yellowID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                four.setImageResource(R.drawable.button_black);
            }
        }, 550);
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
        int value =r.nextInt(4)+1;
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
}
