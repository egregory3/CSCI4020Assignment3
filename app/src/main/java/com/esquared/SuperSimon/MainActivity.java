package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button green = findViewById(R.id.btn_green);
        addClickEffect(green);
        green.setText("Simon Classic");
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), simon_classic.class);
                intent.putExtra("Score", 0);
                startActivity(intent);
            }
        });

        Button red = findViewById(R.id.btn_red);
        addClickEffect(red);


        Button blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);




        Button yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);
    }




    @Override
    protected void onResume() {
        super.onResume();



    }


    void addClickEffect(View v)
    {
        Drawable dN = v.getBackground();
        Drawable dP = v.getBackground().getConstantState().newDrawable();
        dP.mutate();
        dP.setColorFilter(new LightingColorFilter(0x77777777, 0x77777777));

        StateListDrawable lD = new StateListDrawable();
        lD.addState(new int[] {android.R.attr.state_pressed}, dP);
        lD.addState(new int[] {}, dN);
        v.setBackground(lD);


    }



}