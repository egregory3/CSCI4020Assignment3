package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.*;

public class simon_rewind extends MainActivity {

    private Button[] buttons;
    private Button green;
    private Button red;
    private Button blue;
    private Button yellow;

    private Button start;

    private ArrayList<Integer> simonsPattern;

    private Boolean userTurn;

    private int placeHolder;

    private Random random;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_simon_rewind);

        simonsPattern = new ArrayList<Integer>();

        green = findViewById(R.id.btn_green1);
        red = findViewById(R.id.btn_red1);
        blue = findViewById(R.id.btn_blue1);
        yellow = findViewById(R.id.btn_yellow1);
        start = findViewById(R.id.btnStart1);

        buttons=new Button[]{green,red,blue,yellow};
        for(int i=0;i<buttons.length;i++){
            buttons[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userTurn){
                        if(checkPattern(simonsPattern,placeHolder,v)){
                            nextRound();
                        }else{
                            gameOver();
                        }
                    }
                }
            });
        }

        placeHolder=0;
        userTurn=false;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextRound();
            }
        }, 1500);
    }
    public void playPattern(){
        switch(simonsPattern.get(placeHolder)) {
            case 0:{
                pressGreen();
                break;
            }
            case 1:{
                pressRed();
                break;
            }
            case 2:{
                pressBlue();
                break;
            }
            case 3:{
                pressYellow();
                break;
            }
        }
        placeHolder++;
        if(placeHolder<simonsPattern.size()){
            handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playPattern();
                }
            }, 1000);
        }else{
            userTurn=true;
            placeHolder=0;
        }
    }


    //Animation and sound for the green button
    private void pressGreen(){
        green.setPressed(true);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                green.setPressed(false);
            }
        },600);
    }

    //Animation and sound for the red button
    private void pressRed(){
        red.setPressed(true);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                red.setPressed(false);
            }
        },600);
    }

    //Animation and sound for the blue button
    private void pressBlue(){
        blue.setPressed(true);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blue.setPressed(false);
            }
        },600);
    }

   //Animation and sound for the yellow button
    private void pressYellow(){
        yellow.setPressed(true);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                yellow.setPressed(false);
            }
        },600);
    }

    private void addRandom(){
        simonsPattern.add(random.nextInt(4));
    }

    private boolean checkPattern (ArrayList<Integer> simonsPattern,int placeHolder,View view){
       this.simonsPattern=simonsPattern;
       this.placeHolder=placeHolder;
       if(simonsPattern.get(placeHolder)==view.getTag()){
                return true;
       }else{
           return false;
       }
    }

    private void nextRound(){
        placeHolder++;
        if(placeHolder==simonsPattern.size())
            Toast.makeText(simon_rewind.this, "You Won The Round!", Toast.LENGTH_SHORT).show();
        userTurn=false;
        placeHolder=0;
        addRandom();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                playPattern();
            }
        }, 2000);
    }

    private void gameOver(){
        placeHolder = 0;
        userTurn = false;
        Toast.makeText(simon_rewind.this, "Button Press Added3", Toast.LENGTH_SHORT).show();
    }

}