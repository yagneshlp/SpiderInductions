package com.yagneshlp.spiderinductions;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class Task0Activity extends Activity {
    int curamt,random,prev=0;
    TextView tv_raand,tv_cur;
    Button rs1,rs2,rs5,rs10,undo,reset;
    ProSwipeButton proSwipeBtn;
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task0);

        rs1 = (Button) findViewById(R.id.button1rs);
        rs2 = (Button) findViewById(R.id.button2rs);
        rs5 = (Button) findViewById(R.id.button5rs);
        rs10 = (Button) findViewById(R.id.button10rs);
        undo = (Button) findViewById(R.id.buttonundo);
        tv_raand = (TextView) findViewById(R.id.tvrandomno);
        tv_cur = (TextView) findViewById(R.id.tvcuramt);
        reset = (Button) findViewById(R.id.buttonreset);
        fl = (FrameLayout) findViewById(R.id.frameMaiin);

        seedRandom();

        rs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev = 1;
                curamt=curamt+1;
                checkStatus();
            }
        });
        rs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev = 2;
                curamt = curamt +2;
                checkStatus();
            }
        });

        rs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev = 5;
                curamt = curamt+5;
                checkStatus();
            }
        });

        rs10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev = 10;
                curamt = curamt + 10;
                checkStatus();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(prev==0)){
                curamt = curamt - prev;
                undo.setEnabled(false);
                checkStatus();}
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seedRandom();

            }
        });







    }

    public void checkStatus(){
        if(curamt==random)
        {
            tv_cur.setText("You have Paid correctly");
            undo.setEnabled(false);
            rs1.setEnabled(false);
            rs2.setEnabled(false);
            rs5.setEnabled(false);
            rs10.setEnabled(false);
            fl.setBackgroundColor(getResources().getColor(R.color.green_success));
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
            v.vibrate(200);
            v.vibrate(200);
        }
        else if(curamt>random)
        {
            tv_cur.setText("You have exceeded the amount :( \n Reset and start again.");
            rs1.setEnabled(false);
            rs2.setEnabled(false);
            rs5.setEnabled(false);
            rs10.setEnabled(false);
            undo.setEnabled(false);
            fl.setBackgroundColor(getResources().getColor(R.color.red_failure));
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(400);
        }
        else
            tv_cur.setText("You have currently accumlated \u20B9 " +String.valueOf(curamt));
    }

    public void seedRandom(){
        int min = 1;
        int max = 30;
        Random r = new Random();
        random = r.nextInt(max - min + 1) + min;
        tv_raand.setText("\u20B9 " + String.valueOf(random));
        rs1.setEnabled(true);
        rs2.setEnabled(true);
        rs5.setEnabled(true);
        rs10.setEnabled(true);
        undo.setEnabled(true);
        curamt = 0;
        prev = 0;
        tv_cur.setText("");
        fl.setBackgroundColor(getResources().getColor(R.color.background));
    }
}
