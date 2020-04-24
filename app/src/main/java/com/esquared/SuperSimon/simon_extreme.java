package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class simon_extreme extends MainActivity {
    int randomNumber = new Random().nextInt(4);
    private SoundPool sP;
    private Set<Integer> sL;
    ArrayList<Integer> pattern = new ArrayList<Integer>();
    int iterator = 0;
    Button blue;
    Button green;
    Button red;
    Button yellow;
    Button start;
    Button flashButton;
    int score;
    TextView tv_score;
    final Handler handler = new Handler();
    final Runnable buttonRelease = new Runnable(){
        public void run(){
            flashButton.setPressed(false);

        }
    };
    final Runnable advance = new Runnable(){

        @Override
        public void run() {
            advancePlay();
        }
    };
    Boolean inPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_classic);

        sL=new HashSet<Integer>();

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


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay(v);
            }

            private void startPlay(View v) {
                if (v == start) {
                    pattern.clear();
                    pattern.add(randomNumber);
                    iterator = 0;
                }
                switch(pattern.get(iterator)) {
                    case 0:{
                        flashButton(blue);
                        inPlay = true;
                        break;

                    }
                    case 1:{
                        flashButton(red);
                        inPlay = true;
                        break;
                    }
                    case 2:{
                        flashButton(green);
                        inPlay = true;
                        break;
                    }
                    case 3:{
                        flashButton(yellow);
                        inPlay = true;
                        break;
                    }
                }
                randomNumber = new Random().nextInt(4);
                start.setText("Restart");

            }

            private void flashButton(View v) {
                flashButton = findViewById(v.getId());
                v.performClick();
                v.setPressed(true);
                handler.postDelayed(buttonRelease, 500);
            }


        });

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");

        Intent i = getIntent();
        score = i.getIntExtra("score", 0);
        tv_score.setText(String.valueOf(score));



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

        final int blueId =sP.load(this, R.raw.blue, 1);
        findViewById(R.id.btn_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(blueId);
                if(inPlay == true){
                    if (pattern.get(iterator) == 0){
                        if (iterator < pattern.size()){
                            iterator ++;
                        }
                        if (iterator ==  pattern.size()){
                            iterator = 0;
                            handler.postDelayed(advance, 1000);
                            inPlay = false;
                        }
                    }else{
                        Toast.makeText(simon_extreme.this, "You Lose", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final int greenId =sP.load(this, R.raw.green, 1);
        findViewById(R.id.btn_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(greenId);
                if(inPlay == true){
                    if (pattern.get(iterator) == 2){
                        if (iterator < pattern.size()){
                            iterator ++;
                        }
                        if (iterator ==  pattern.size()){
                            iterator = 0;
                            handler.postDelayed(advance, 1000);
                            inPlay = false;
                        }
                    }else{
                        Toast.makeText(simon_extreme.this, "You Lose", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final int redId =sP.load(this, R.raw.red, 1);
        findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(redId);
                if(inPlay == true){

                    if (pattern.get(iterator) == 1){
                        if (iterator < pattern.size()){
                            iterator ++;
                        }
                        if (iterator ==  pattern.size()){
                            iterator = 0;
                            handler.postDelayed(advance, 1000);
                            inPlay = false;
                        }
                    }
                }else{
                    Toast.makeText(simon_extreme.this, "You Lose", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final int yellowId =sP.load(this, R.raw.yellow, 1);
        findViewById(R.id.btn_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(yellowId);
                if(inPlay == true){
                    if (pattern.get(iterator) == 3){
                        if (iterator < pattern.size()){
                            iterator ++;
                        }
                        if (iterator ==  pattern.size()){
                            iterator = 0;
                            handler.postDelayed(advance, 1000);
                            inPlay = false;
                        }
                    }else{
                        Toast.makeText(simon_extreme.this, "You Lose", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sP != null) {
            sP.release();
            sP= null;

            sL.clear();
        }
    }


    private void playSound(int soundId) {
        if (sL.contains(soundId)) {
            sP.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void advancePlay() {
        randomNumber = new Random().nextInt(4);
        pattern.add(randomNumber);
        inPlay = false;
        switch(pattern.get(pattern.size()-1)) {
            case 0:{
                flashButton(blue);

                break;

            }
            case 1:{
                flashButton(red);
                break;
            }
            case 2:{
                flashButton(green);
                break;
            }
            case 3:{
                flashButton(yellow);
                break;
            }
        }
        randomNumber = new Random().nextInt(4);
        inPlay = true;
    }

    private void flashButton(View v) {
        flashButton = findViewById(v.getId());
        v.performClick();
        v.setPressed(true);
        handler.postDelayed(buttonRelease, 500);
    }
}
