package com.esquared.SuperSimon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class simon_classic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_classic);
        Button blue = findViewById(R.id.btn_blue);
        Button green = findViewById(R.id.btn_green);
        Button red = findViewById(R.id.btn_red);
        Button yellow = findViewById(R.id.btn_yellow);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");
    }
}
