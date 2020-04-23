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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class simon_rewind extends AppCompatActivity implements View.OnClickListener{

    private View[] views;

    private ArrayList<Integer> simonsPattern;
    private ImageView green;
    private ImageView red;
    private ImageView blue;
    private ImageView yellow;
    private int placeHolder;

    private Button start;



    private Boolean userTurn;



    private Random random=new Random();
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
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playPattern();
                    }
                }, 1500);
            }

        });

        views=new View[]{green,red,blue,yellow};
        for(int i=0;i<views.length;i++){
           views[i].setOnClickListener(this);
        }

        placeHolder=0;
        userTurn=false;

        addRandom();

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
        green.setImageResource(R.drawable.button_green_down);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                green.setImageResource(R.drawable.button_green);
            }
        },600);
    }

    //Animation and sound for the red button
    private void pressRed(){
        red.setImageResource(R.drawable.button_red_down);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                red.setImageResource(R.drawable.button_red);
            }
        },600);
    }

    //Animation and sound for the blue button
    private void pressBlue(){
        blue.setImageResource(R.drawable.button_blue_down);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blue.setImageResource(R.drawable.button_blue);
            }
        },600);
    }

   //Animation and sound for the yellow button
    private void pressYellow(){
        yellow.setImageResource(R.drawable.button_yellow_down);
        //playSound
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                yellow.setImageResource(R.drawable.button_yellow);
            }
        },600);
    }

    @Override
    public void onClick(View view) {
        if (userTurn==true) {
            if (checkPattern(simonsPattern,placeHolder,view)){
                nextRound();
            }else {
                gameOver();
            }
            //getSound(view);
        }
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