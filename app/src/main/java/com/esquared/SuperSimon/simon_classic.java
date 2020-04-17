package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Random;

public class simon_classic extends MainActivity {
    Random randomNumber;
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

        Button blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button red = findViewById(R.id.btn_red);
        addClickEffect(red);
        Button green = findViewById(R.id.btn_green);
        addClickEffect(green);
        Button yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");


    }

    public void onResume() {

        super.onResume();


    }



}