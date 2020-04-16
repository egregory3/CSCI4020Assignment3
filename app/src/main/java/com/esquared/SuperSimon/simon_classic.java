package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Random;

public class simon_classic extends MainActivity {
    Random random = new Random();
    int nextFlash = 0;
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
        blue = findViewById(R.id.btn_blue);
        green = findViewById(R.id.btn_green);
        red = findViewById(R.id.btn_red);
        yellow = findViewById(R.id.btn_yellow);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");

        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new TouchProcessor(v, event);
                return false;
            }
        });
        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new TouchProcessor(v, event);
                return false;
            }
        });
        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new TouchProcessor(v, event);
                return false;
            }
        });
        yellow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new TouchProcessor(v, event);
                return false;
            }
        });

    }

    public void onResume(){
        main = false;
        super.onResume();

        try {
            advance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void advance() throws InterruptedException {
        nextFlash = random.nextInt(4);
        pattern.add(nextFlash);
        for(int i=0; i<pattern.size(); i++)
        {

            switch(pattern.get(i)){
                case(0):{
                    flash(blue);
                }
                case(1):{
                    flash(green);
                }
                case(2):{
                    flash(red);
                }
                case(3):{
                    flash(yellow);
                }
            }
        }
    }
    public void flash(View V) throws InterruptedException {
        Button button = findViewById(V.getId());
        int bgrd = 0, bgru = 0;
        switch(V.getId()){
            case(R.id.btn_blue):{
                bgrd = R.drawable.button_blue_down;
                Thread.sleep(500);
                bgru = R.drawable.button_blue;
                break;

            }
            case(R.id.btn_green):{
                bgrd = R.drawable.button_green_down;
                Thread.sleep(500);
                bgru = R.drawable.button_green;
                break;
            }
            case(R.id.btn_red):{
                bgrd = R.drawable.button_red_down;
                Thread.sleep(500);
                bgru = R.drawable.button_red;
                break;
            }
            case(R.id.btn_yellow):{
                bgrd = R.drawable.button_yellow_down;
                Thread.sleep(500);
                bgru = R.drawable.button_yellow;
                break;
            }



        }
        button.setBackgroundResource(bgrd);
        Log.i("Button State", "Down");
        Thread.sleep(500);
        button.setBackgroundResource(bgru);
        Log.i("Button State", "up");
        return;
    }
}


