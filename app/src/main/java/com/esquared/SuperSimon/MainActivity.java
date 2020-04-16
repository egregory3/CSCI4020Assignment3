package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button blue;
    Button green;
    Button red;
    Button yellow;
    boolean main = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        main = true;
        super.onResume();

        blue = findViewById(R.id.btn_blue);
        red = findViewById(R.id.btn_red);
        green = findViewById(R.id.btn_green);
        yellow = findViewById(R.id.btn_yellow);
        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processTouch(blue, event);
                Intent intent = new Intent(MainActivity.this, simon_classic.class);
                MainActivity.this.startActivity(intent);

                return false;
            }
        });

        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processTouch(red, event);
                return false;
            }
        });

        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processTouch(green, event);
                return false;
            }
        });

        yellow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processTouch(yellow, event);
                return false;
            }
        });

    }



    boolean processTouch(View V, MotionEvent event){
        new TouchProcessor(V, event);
        return false;
    }

    public class TouchProcessor {
        private View v;
        private MotionEvent event;

        public TouchProcessor(View V, MotionEvent event) {
            v = V;
            this.event = event;

            Button myV = findViewById(v.getId());
            Button btn = findViewById(R.id.btn_blue);
            int bgDown = R.drawable.button_blue_down;
            int bgUp = R.drawable.button_blue;


            if (myV == yellow){
                btn = findViewById(R.id.btn_yellow);
                bgDown = R.drawable.button_yellow_down;
                bgUp = R.drawable.button_yellow;
            }else if (myV == red){
                btn = findViewById(R.id.btn_red);
                bgDown = R.drawable.button_red_down;
                bgUp = R.drawable.button_red;
            }else if (myV == green){
                btn = findViewById(R.id.btn_green);
                bgDown = R.drawable.button_green_down;
                bgUp = R.drawable.button_green;
            }

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("Button", "Blue Button Pressed");
                        btn.setBackgroundResource(bgDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.setBackgroundResource(bgUp);
                        break;



                }

    }
}}


