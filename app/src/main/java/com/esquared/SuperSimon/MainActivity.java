//Eric Gregory and Eric Raymond
//CSCI 4020, Fall 2020
//Professor John Nicholson
//Assignment 3

package com.esquared.SuperSimon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        Button green = findViewById(R.id.btn_green);
        green.setText("Simon Classic");
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Simon Classic Instructions");
                dialog.setMessage("Follow the patterns as they are presented to you . If your pattern matches Simon's pattern, one button is added" +
                        " to Simon's pattern. If the pattern does not match, it's game over. :(");
                dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), simon_classic.class);
                        intent.putExtra("Score", 0);
                        startActivity(intent);
                    }
                });
                final AlertDialog classicAlert = dialog.create();
                classicAlert.show();
            }
        });

        Button red = findViewById(R.id.btn_red);
        addClickEffect(red);
        red.setText("Simon Rewind");
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Simon Rewind Instructions");
                dialog.setMessage("Play back Simon's pattern in the revers order that it is presented on screen. If you successfully, reverse Simon's patter" +
                        "a button is added to Simon's pattern. If you do not successfully select Simon's patter in reverse, it's game over :(");
                dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), simon_rewind.class);
                        intent.putExtra("Score", 0);
                        startActivity(intent);
                    }
                });
                final AlertDialog rewindAlert = dialog.create();
                rewindAlert.show();
            }
        });

        Button blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);
        blue.setText("Simon Extreme");
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Simon Extreme Instructions");
                dialog.setMessage("Instead of four possible colors in Simon's pattern, there are six! If your pattern matches Simon's pattern, one button is added" +
                        "to Simon's pattern. If the pattern does not match, it's game over. :(");
                dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), simon_extreme.class);
                        intent.putExtra("Score", 0);
                        startActivity(intent);
                    }
                });
                final AlertDialog extremeAlert = dialog.create();
                extremeAlert.show();
            }
        });



        Button yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);
        yellow.setText("Simon Suprise");
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Simon Surprise Instructions");
                dialog.setMessage("Instead of mutltiple colors and different sounds, all buttons are assoicated with the same sound and the  bottons all have the same color." +
                        "The buttons will light up when selected. Match Simon's pattern to go to the next round. If you do not match Simon's pattern, it is game over!");
                dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), simon_surprise.class);
                        intent.putExtra("Score", 0);
                        startActivity(intent);
                    }
                });
                final AlertDialog surpriseAlert = dialog.create();
                surpriseAlert.show();
            }
        });

        Button creditsButton = findViewById(R.id.creditsButton);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), credits.class);
                intent.putExtra("Score", 0);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();


    }

    void addClickEffect(Button b)
    {
        Drawable dN = b.getBackground();
        Drawable dP = b.getBackground().getConstantState().newDrawable();
        dP.mutate();
        dP.setColorFilter(new LightingColorFilter(0x77777777, 0x77777777));

        StateListDrawable lD = new StateListDrawable();
        lD.addState(new int[] {android.R.attr.state_pressed}, dP);
        lD.addState(new int[] {}, dN);
        b.setBackground(lD);
    }



}