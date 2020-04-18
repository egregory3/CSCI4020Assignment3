package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
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
    ArrayList<Integer> CurrentRun = new ArrayList<Integer>();
    Button blue;
    Button green;
    Button red;
    Button yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_classic);

        sL=new HashSet<Integer>();

        Button blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);
        blue.setSoundEffectsEnabled(false);

        Button red = findViewById(R.id.btn_red);
        addClickEffect(red);
        red.setSoundEffectsEnabled(false);

        Button green = findViewById(R.id.btn_green);
        addClickEffect(green);
        green.setSoundEffectsEnabled(false);

        Button yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);
        yellow.setSoundEffectsEnabled(false);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");




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
            }
        });

        final int greenId =sP.load(this, R.raw.green, 1);
        findViewById(R.id.btn_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(greenId);
            }
        });

        final int redId =sP.load(this, R.raw.red, 1);
        findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(redId);
            }
        });

        final int yellowId =sP.load(this, R.raw.yellow, 1);
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
            sP= null;

            sL.clear();
        }
    }

    private void playSound(int soundId) {
        if (sL.contains(soundId)) {
            sP.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

}