package com.yagneshlp.spiderinductions.tasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.yagneshlp.spiderinductions.R;

import me.grantland.widget.AutofitTextView;

public class Task1Activity extends Activity {

    long startTime;
    long MillisecondTime, StartTime, TimeBuff, MilliSeconds, UpdateTime = 0L ;
    int Seconds, Minutes ;
    Handler handler;
    int state = 0;
    private static final String TAG = Task1Activity.class.getSimpleName();
    RelativeLayout layout;
    AutofitTextView Stopwatch, InspectionTime,ModeView;
    CountDownTimer inspection;
    boolean isInspectTimerPaused = false, isInspectTimerRunning = false,isStopwatchRunning = false, beeped1 = false,beeped2 = false,beeped3 = false;
    String mode;


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mode= "No Data Recieved";
            } else {
                mode= extras.getString("mode");
            }
        } else {
            mode= (String) savedInstanceState.getSerializable("mode");
        }

        Log.d(TAG, mode);

        setContentView(R.layout.activity_task1);
        handler = new Handler();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/timer.ttf");





        inspection =  new CountDownTimer(15000, 30) {

            public void onTick(long millisUntilFinished) {
                long ms = (millisUntilFinished - (millisUntilFinished/1000)*1000);
                InspectionTime.setText(String.format("%02d", millisUntilFinished/1000) + ":"
                        + String.format("%03d", ms) );
                if(millisUntilFinished<3000 && mode.equals("Hacker")){
                  if(!beeped1){
                      ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                      toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
                      beeped1 =true;
                  }

                }
                if(millisUntilFinished<2000 && mode.equals("Hacker")){
                    if(!beeped2){
                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
                        beeped2 =true;
                    }

                }
                if(millisUntilFinished<1000 && mode.equals("Hacker")){
                    if(!beeped3){
                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
                        beeped3 =true;
                    }

                }
            }

            public void onFinish() {
                InspectionTime.setText("00:000");
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
                isInspectTimerRunning = false;
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                isStopwatchRunning = true;
            }
        };






        InspectionTime = findViewById(R.id.textViewInspection);
        Stopwatch = findViewById(R.id.textViewStopwatch);
        layout = findViewById(R.id.relativeLayout);
        ModeView = findViewById(R.id.modeTV);
        ModeView.setText(mode + " Mode");
        if(mode.equals("Hacker"))
            ModeView.setTextColor(getResources().getColor(R.color.hackerMode));

        //Stopwatch.setTypeface(font);
        //InspectionTime.setTypeface(font);

       layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInspectTimerPaused && !isStopwatchRunning){
                    InspectionTime.setText("15:000");
                    isInspectTimerPaused = false;
                    state = 2;
                }


                if(state==0){
                    state = 1;
                    inspection.start();
                    Stopwatch.setText("0:00:000");
                    isInspectTimerRunning = true;
                 }
                else if(!isInspectTimerRunning && state==1)
                {
                    state = 2;
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    MilliSeconds = 0 ;
                    isStopwatchRunning = false;
                }
                else if (isInspectTimerRunning && state==1 && mode.equals("Hacker")){
                    inspection.cancel();
                    isInspectTimerPaused = true;
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    isStopwatchRunning = true;
                    state =3;

                }
                else if(state == 2){
                    InspectionTime.setText("15:000");
                    Stopwatch.setText("0:00:000");
                    state = 0;
                }
                else if (state == 3){
                    handler.removeCallbacks(runnable);
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    MilliSeconds = 0 ;
                    isStopwatchRunning = false;
                    state = 2;
                }
            }



        });

       layout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
               vibrator.vibrate(50);
               if(isInspectTimerRunning) {
                   inspection.cancel();
                   isInspectTimerPaused = true;

               }
               return true;
           }
       });


    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = UpdateTime - ((UpdateTime/1000)*1000);
            long ms = (UpdateTime - (UpdateTime/1000)*1000);
            Stopwatch.setText(Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", ms) );
            handler.postDelayed(this, 30);
        }

    };
    public void beeper(){

            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
            toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                            toneG.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 400);

                        }
                    }, 600);
                }
            }, 600);


    }
}
