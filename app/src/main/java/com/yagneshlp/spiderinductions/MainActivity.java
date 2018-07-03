package com.yagneshlp.spiderinductions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adammcneilly.ActionCardView;
import com.yagneshlp.spiderinductions.tasks.Task_0.Task0Activity;
import com.yagneshlp.spiderinductions.tasks.Task_1.Task1Activity;
import com.yagneshlp.spiderinductions.tasks.Task_2.Task2Activity;
import com.yagneshlp.spiderinductions.tasks.Task_3.Task3Activity;
import com.yagneshlp.spiderinductions.tasks.Task_4.Task4Activity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionCardView actionCardView0,actionCardView1,actionCardView2,actionCardView3,actionCardView4;
        actionCardView0 = (ActionCardView) findViewById(R.id.action_card);
        actionCardView1 = (ActionCardView) findViewById(R.id.action_cardTask1);
        actionCardView2 = (ActionCardView) findViewById(R.id.action_cardTask2) ;
        actionCardView3 = (ActionCardView) findViewById(R.id.action_cardTask3) ;
        actionCardView4 = findViewById(R.id.action_cardTask4);

        actionCardView0.addActionClickListener(R.id.button_task0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task0Activity.class);
                startActivity(intent);

            }
        });
        actionCardView1.addActionClickListener(R.id.button_task1Normal, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task1Activity.class);
                intent.putExtra("mode", "Normal");
                startActivity(intent);


            }
        });
        actionCardView1.addActionClickListener(R.id.button_task1Hacker, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task1Activity.class);
                intent.putExtra("mode", "Hacker");
                startActivity(intent);


            }
        });

        actionCardView2.addActionClickListener(R.id.button_task2Normal, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task2Activity.class);
                intent.putExtra("mode", "Normal");
                startActivity(intent);


            }
        });
        actionCardView2.addActionClickListener(R.id.button_task2Hacker, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task2Activity.class);
                intent.putExtra("mode", "Hacker");
                startActivity(intent);


            }
        });

        actionCardView3.addActionClickListener(R.id.button_task3Normal, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task3Activity.class);
                intent.putExtra("mode", "Hacker");
                startActivity(intent);


            }
        });
        actionCardView4.addActionClickListener(R.id.button_task4Normal, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task4Activity.class);
                intent.putExtra("mode", "Hacker");
                startActivity(intent);


            }
        });




    }




}
