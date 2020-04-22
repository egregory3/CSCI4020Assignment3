package com.esquared.SuperSimon;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class simon_rewind extends MainActivity {
    Random randomNumber;

    //MyAsyncTask myAsyncTask;

    private SoundPool sP;

    private Set<Integer> sL;

    ArrayList<Integer> compPattern = new ArrayList<Integer>();
    /*   ArrayList<Integer> currentRun = new ArrayList<Integer>();*/
    ArrayList<Integer> userPattern = new ArrayList<Integer>();

    boolean gameOver = false;

    Handler handler = new Handler();


    Button red;
    Button green;
    Button yellow;
    Button blue;

    int blueId;
    int greenId;
    int redId;
    int yellowId;
    int roundCount=12;

    boolean playerTurn;
    boolean patternDisplaying;
    boolean lastInArray;

    int minRandom;
    int maxRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_simon_rewind);

        roundCount = 0;
        randomNumber = new Random();

        sL = new HashSet<Integer>();

        blue = findViewById(R.id.btn_blue1);
        addClickEffect(blue);
        blue.setSoundEffectsEnabled(false);


        red = findViewById(R.id.btn_red1);
        addClickEffect(red);
        red.setSoundEffectsEnabled(false);

        green = findViewById(R.id.btn_green1);
        addClickEffect(green);
        green.setSoundEffectsEnabled(false);

        yellow = findViewById(R.id.btn_yellow1);
        addClickEffect(yellow);
        yellow.setSoundEffectsEnabled(false);

        blue.setText("");
        green.setText("");
        red.setText("");
        yellow.setText("");

        Button start = findViewById(R.id.startButton);
        start.setSoundEffectsEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPattern.clear();
                roundCount++;
                for(int i=roundCount;i<4;i++)
                    compPattern.add(randomNumber.nextInt(4));
                playPattern();
                CompareTask compareTask=new CompareTask();
                compareTask.execute();
            }
        });

    }

    public void onResume() {

        super.onResume();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        sP = spBuilder.build();

        sP.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    sL.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

        //Blue button with sound and action
        blueId = sP.load(this, R.raw.blue, 1);
        findViewById(R.id.btn_blue1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternDisplaying==true)
                    return;
                int temp = 2;
                playSound(blueId);
                Toast.makeText(simon_rewind.this, "Button Press Added2", Toast.LENGTH_SHORT).show();
            }
        });


        //Green button with sound and action
        greenId = sP.load(this, R.raw.green, 1);
        findViewById(R.id.btn_green1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternDisplaying==true)
                    return;
                int temp = 0;
                playSound(greenId);
                Toast.makeText(simon_rewind.this, "Button Press Added0", Toast.LENGTH_SHORT).show();

            }
        });

        //Red button with sound and action
        redId = sP.load(this, R.raw.red, 1);
        findViewById(R.id.btn_red1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternDisplaying==true)
                    return;
                int temp = 1;
                Toast.makeText(simon_rewind.this, "Button Press Added1", Toast.LENGTH_SHORT).show();
                playSound(redId);
            }
        });

        //Yellow button with sound and action
        yellowId = sP.load(this, R.raw.yellow, 1);
        findViewById(R.id.btn_yellow1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternDisplaying==true)
                    return;
                if(patternDisplaying==false) {
                    int temp = 3;
                    playSound(yellowId);
                    userPattern.add(temp);
                    Toast.makeText(simon_rewind.this, "Button Press Added3", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }


    class CompareTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            boolean matchingPatterns = false;
            try {
                Thread.sleep(roundCount * 1000);
                for (int i = 0; i < compPattern.size(); i++) {
                    if (compPattern.size() == userPattern.size() && compPattern.get(i) == userPattern.get(i)) {
                        matchingPatterns = true;
                    } else {
                        matchingPatterns = false;
                        break;
                    }
                }
                if (matchingPatterns == true) {
                    roundCount++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playPattern();
                        }
                    });

                    publishProgress(roundCount);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            endOfGame();
                        }
                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(simon_rewind.this, "You have passed round "+ roundCount, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            patternDisplaying = false;
        }

    }



    private void endOfGame(){
        Toast.makeText(simon_rewind.this, "Button Press Added3", Toast.LENGTH_SHORT).show();
    }

    //function calling to play the pattern stored in compPattern
    private void playPattern(){
        patternDisplaying=true;
        double delay = 1;
        double defaultWaitTime=600;
        for(int i =0; i< compPattern.size();i++)
        {
            final int temp = compPattern.get(i);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (temp == 0)
                        pressGreen();
                    if (temp == 1)
                        pressRed();
                    if (temp == 2)
                        pressBlue();
                    if (temp== 3)
                        pressYellow();
                }
            }, (long) (delay*defaultWaitTime));
            delay++;
           if(i==compPattern.size()-1)
           {
               compPattern.add(randomNumber.nextInt(4));
               patternDisplaying=false;
               break;
           }
        }
    }

    //function to play sound attached to soundId
    private void playSound(int soundId) {
        if (sL.contains(soundId)) {
            sP.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sP != null) {
            sP.release();
            sP = null;
            sL.clear();
        }
    }

    //Green button action when pressed by computer pattern
    private void pressGreen() {
        green.setPressed(true);
        playSound(greenId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                green.setPressed(false);
            }
        }, 500);
    }

    //Red button action when pressed by computer pattern
    private void pressRed() {

        red.setPressed(true);
        playSound(redId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                red.setPressed(false);
            }
        }, 500);
    }

    //Blue  button action when pressed by computer pattern
    private void pressBlue() {
        blue.setPressed(true);
        playSound(blueId);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                blue.setPressed(false);
            }
        }, 500);
    }

    //Yellow button action when pressed by computer pattern
    private void pressYellow() {
        yellow.setPressed(true);
        playSound(yellowId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                yellow.setPressed(false);
            }
        }, 500);
    }





}