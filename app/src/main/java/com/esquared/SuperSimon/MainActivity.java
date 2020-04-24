package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button green = findViewById(R.id.btn_green);

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
        red.setText("Simon Rewind");
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), simon_rewind.class);
                intent.putExtra("Score", 0);
                startActivity(intent);
            }
        });


        Button blue = findViewById(R.id.btn_blue);
        addClickEffect(blue);
        blue.setText("Simon Extreme");
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), simon_extreme.class);
                intent.putExtra("Score", 0);
                startActivity(intent);
            }
        });




        Button yellow = findViewById(R.id.btn_yellow);
        addClickEffect(yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), simon_surprise.class);
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