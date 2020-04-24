package com.esquared.SuperSimon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class credits extends MainActivity  {

        @Override
        protected void onCreate(Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_credits);

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

        }








}
