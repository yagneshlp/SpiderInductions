package com.yagneshlp.spiderinductions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adammcneilly.ActionCardView;

import java.util.Random;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionCardView actionCardView;
        actionCardView = (ActionCardView) findViewById(R.id.action_card);

        actionCardView.addActionClickListener(R.id.button_task0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task0Activity.class);
                startActivity(intent);

            }
        });

    }




}
