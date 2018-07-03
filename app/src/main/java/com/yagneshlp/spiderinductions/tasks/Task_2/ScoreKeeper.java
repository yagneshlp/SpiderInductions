package com.yagneshlp.spiderinductions.tasks.Task_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ScoreKeeper {

    // LogCat tag
    private static String TAG = ScoreKeeper.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref1;
    SharedPreferences.Editor editor1;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME_Score = "Hangman";

    private static final String KEY_Score = "Score";


    public  ScoreKeeper(Context context) {
        this._context = context;
        pref1 = _context.getSharedPreferences(PREF_NAME_Score, PRIVATE_MODE);
        editor1 = pref1.edit();

    }

    public boolean updateScore(int score) {
        if(score<getScore()) {
            editor1.putInt(KEY_Score, score);
            // commit changes
            editor1.commit();
            Log.d(TAG, "Current Score was better than previous value,User Score Updated");
            return true;
        }
        else {
            Log.d(TAG, "Already stored Value was better than current score, User Score not Updated");
            return false;
        }
    }


    public int getScore(){

        return pref1.getInt(KEY_Score, 10);
    }




}
