package com.yagneshlp.spiderinductions.tasks.Task_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.helper.ScoreKeeper;

import java.util.Random;

public class Task2Activity extends Activity {

    private static final String TAG = Task2Activity.class.getSimpleName();
    String Hangman,PseudoHangman,Text,mode ;
    TextView tv,wrong,recent,best;
    int wrongs=0;
    EditText et;
    Button button,resetButton;
    ScoreKeeper scoreKeeper;
    RelativeLayout hackerLayout;
    ImageView imageView;
    String[] mobile_companies = {"APPLE", "SAMSUNG", "ONEPLUS", "XIAOMI","OPPO",
                                    "LAVA","VIVO","MICROMAX","MOTOROLA","lENOVO",
                                    "HUAWEI","ZTE","PANASONIC","SONY","NOKIA"};

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

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


        tv= findViewById(R.id.hangmanText);
        et = findViewById(R.id.letterToCheck);
        button = findViewById(R.id.checkerButton);
        wrong  = findViewById(R.id.wrongs);
        recent = findViewById(R.id.recentGuess);
        resetButton = findViewById(R.id.resetButton);
        scoreKeeper = new ScoreKeeper(getApplicationContext());
        hackerLayout = findViewById(R.id.hackerModeTask2);
        imageView = findViewById(R.id.ImageView);
        best = findViewById(R.id.bestScore);

        if(mode.equals("Hacker"))
            hackerLayout.setVisibility(View.VISIBLE);

        generateWord();

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {                                   //Listener to automatically start the checking process on IME Action Done from Keyboard
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    executioner();
                }
                return false;
            }
        });



        button.setOnClickListener(new View.OnClickListener() {                      //Checking button's listener
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);         //To Hide the Soft Keyboard
                imm.hideSoftInputFromWindow(
                        et.getWindowToken(), 0);
                if(wrongs<10)
                    executioner();
                else
                    Toast.makeText(getApplicationContext(),"You've already been hanged\nLong press check button to reset and continue",Toast.LENGTH_LONG).show();

            }
        });

      resetButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              et.setVisibility(View.VISIBLE);
              button.setVisibility(View.VISIBLE);
              resetButton.setVisibility(View.GONE);
              wrong.setVisibility(View.VISIBLE);
              generateWord();

          }
      });






    }

    protected void executioner(){
        if(et.getText().length()==0){
            Toast.makeText(getApplicationContext(),"No text entered",Toast.LENGTH_LONG).show();
        }
        else{

            boolean flag = false;
            for(int i=0;i<Hangman.length();i++){
                if(et.getText().toString().charAt(0) == Hangman.charAt(i)){

                    StringBuilder myName = new StringBuilder(Text);
                    myName.setCharAt(2*i, et.getText().toString().charAt(0));

                    Text= myName.toString();
                    flag =true;

                }

            }
            if(!flag) {
                wrongs++;
                loadGraphics();
                recent.setText("The most recent wrong guess is " +et.getText().toString().charAt(0) );

            }
            tv.setText(Text);
            et.getText().clear();
            if(wrongs<9)
                wrong.setText("You have "+ (10-wrongs)+ " Lives Left");
            else
                wrong.setText("Only 1 Life Left! \nGuess Carefully!");
            if(Text.equals(PseudoHangman)){
                et.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
                if(scoreKeeper.updateScore(wrongs)){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Task2Activity.this);
                    alertDialogBuilder.setTitle("Congartulations!")
                            .setMessage("This is your best score!");
                    alertDialogBuilder.setPositiveButton("Yeah!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                Toast.makeText(getApplicationContext(),"Congrats, You have won!",Toast.LENGTH_LONG).show();
            }
            if(wrongs==10){
                tv.setText(Hangman);
                et.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
                wrong.setVisibility(View.GONE);
                recent.setText("You have been Hanged !!!");
                Toast.makeText(getApplicationContext(),"Hanngman!!! ",Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void generateWord(){                                   //TODO: Create a dictionary and assign the value of hangman from it randomly

        int min = 0;
        int max = 14;
        Random r = new Random();
        int random = r.nextInt(max - min + 1) + min;
        Hangman = mobile_companies[random];
        int length = Hangman.length();
            Log.d(TAG, "Current Word is: "+Hangman + " "+ length);
        StringBuilder myName = new StringBuilder();
        String dummy = " ";
        myName.setLength(length*2);
        for(int i = 0; i<2*length; i++) {
            if (i % 2 == 0)
                myName.setCharAt(i, Hangman.charAt(i / 2));
            else
                myName.setCharAt(i,dummy.charAt(0));
            Log.d(TAG, i+ " :: Current pseudoword is : " + myName.toString());
        }

        PseudoHangman =myName.toString();
        Log.d(TAG, "Current PseudoWord is: "+PseudoHangman);
        wrongs = 0;
        Text ="";

        for(int i=0;i<Hangman.length();i++){       //To generate a placeholder for the Word.
            Text=Text.concat("_ ");
        }

        tv.setText(Text);                        //Setting the Placeholder
        recent.setText("");
        wrong.setText("You have 10 lives left");
        best.setText("Current Best was achived with \n"+ (10 - scoreKeeper.getScore())+"\nremaining lives");
        loadGraphics();

    }

    public void loadGraphics(){
        if(wrongs == 0)
            imageView.setImageResource(R.drawable.hangman0);
        if(wrongs == 1)
            imageView.setImageResource(R.drawable.hangman1);
        if(wrongs == 2)
            imageView.setImageResource(R.drawable.hangman2);
        if(wrongs == 3)
            imageView.setImageResource(R.drawable.hangman3);
        if(wrongs == 4)
            imageView.setImageResource(R.drawable.hangman4);
        if(wrongs == 5)
            imageView.setImageResource(R.drawable.hangman5);
        if(wrongs == 6)
            imageView.setImageResource(R.drawable.hangman6);
        if(wrongs == 7)
            imageView.setImageResource(R.drawable.hangman7);
        if(wrongs == 8)
            imageView.setImageResource(R.drawable.hangman8);
        if(wrongs == 9)
            imageView.setImageResource(R.drawable.hangman9);
        if(wrongs == 10)
            imageView.setImageResource(R.drawable.hangman10);

    }

}
