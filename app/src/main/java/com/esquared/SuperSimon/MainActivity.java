package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        final Button tv = findViewById(R.id.btn_blue);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processTouch(tv, event);


                return false;
            }
        });




    }

    private boolean processTouch(View V, MotionEvent event){
        TextView myV = findViewById(V.getId());
        TextView tv = findViewById(R.id.btn_blue);

        if( myV == tv){
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("Button", "Blue Button Pressed");
                    tv.setBackgroundResource(R.drawable.button_blue_down);
                    return true;
                case MotionEvent.ACTION_UP:
                    tv.setBackgroundResource(R.drawable.button_blue);
                    Intent intent = new Intent(this, simon_classic.class);
                    this.startActivity(intent);
                    return true;
            }
        }
        return false;
    }

}


