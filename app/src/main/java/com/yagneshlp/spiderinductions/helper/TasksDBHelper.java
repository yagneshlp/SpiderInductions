package com.yagneshlp.spiderinductions.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TasksDBHelper extends SQLiteOpenHelper {

    private static final String TAG = TasksDBHelper.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "to_do";

    // Active Tasks table name
    private static final String TABLE_active = "activeTasks";
    private static final String TABLE_dead = "completedTasks";

    // Login Table Columns names
    private static final String KEY_title = "title";
    private static final String KEY_subTitle = "subTitle";
    private static final String KEY_priority = "priority";
    private static final String KEY_taskID = "id";

    public int noOfActiveTasks=0,noOfCompletedTasks=0;



    public TasksDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_active_TABLE = "CREATE TABLE " + TABLE_active + "("
                +KEY_taskID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_title + " TEXT," + KEY_subTitle + " TEXT," + KEY_priority +" TEXT"
                + ")";
        String CREATE_dead_TABLE ="CREATE TABLE " + TABLE_dead + "("
                +KEY_taskID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_title + " TEXT," + KEY_subTitle + " TEXT," + KEY_priority +" TEXT"
                + ")";

        db.execSQL(CREATE_active_TABLE);
        Log.d(TAG, "Active tasks Database created");
        db.execSQL(CREATE_dead_TABLE);
        Log.d(TAG, "Completed tasks Database created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_active);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_dead);
        // Create tables again
        onCreate(db);
    }


    public void addTask(ToDoTask task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_title, task.title); // Name
        values.put(KEY_subTitle, task.subTitle); // Email
        values.put(KEY_priority, task.priority); // Email
        // Inserting Row
        db.insert(TABLE_active, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Task has been Added into the database");
    }

    public void transferTask(ToDoTask task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_title, task.title); // Name
        values.put(KEY_subTitle, task.subTitle); // Email
        values.put(KEY_priority, task.priority); // Email
        // Inserting Row
        db.insert(TABLE_dead, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "Given Task has been Added into the Completed Table");
        deleteTask(task);
        Log.d(TAG, "Given Task has been removed from the active Table");

    }

    public ToDoTask[] getActiveTasks() {
        ToDoTask[] tasks = new ToDoTask[1000];
        String selectQuery = "SELECT  * FROM " + TABLE_active;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tasks from Sqlite: ");
        Log.d(TAG, "No of tasks fetched: " + cursor.getCount());
        noOfActiveTasks = cursor.getCount();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                tasks[i] = new ToDoTask();
                tasks[i].title =  cursor.getString(1);
                tasks[i].subTitle = cursor.getString(2);
                tasks[i].priority = cursor.getString(3);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public ToDoTask[] getDeadTasks() {
        ToDoTask[] tasks = new ToDoTask[1000];
        String selectQuery = "SELECT  * FROM " + TABLE_dead;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tasks from SQLite: ");
        Log.d(TAG, "No of tasks fetched: " + cursor.getCount());
        noOfCompletedTasks = cursor.getCount();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                tasks[i] = new ToDoTask();
                tasks[i].title =  cursor.getString(1);
                tasks[i].subTitle = cursor.getString(2);
                tasks[i].priority = cursor.getString(3);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public void deleteTask(ToDoTask task) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_active, KEY_title + "= '"+task.title+"'", null);
        db.close();
        Log.d(TAG, "Task " + task.title +" has been Deleted");
    }

    public int getNoOfActiveTasks(){
        String selectQuery = "SELECT  * FROM " + TABLE_active;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tasks from Sqlite: ");
        Log.d(TAG, "No of tasks fetched: " + cursor.getCount());
        noOfActiveTasks = cursor.getCount();
        return noOfActiveTasks;
    }

    public int getNoOfCompletedTasks(){

        String selectQuery = "SELECT  * FROM " + TABLE_dead;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tasks from SQLite: ");
        Log.d(TAG, "No of tasks fetched: " + cursor.getCount());
        noOfCompletedTasks = cursor.getCount();
        return noOfCompletedTasks;
    }
}
