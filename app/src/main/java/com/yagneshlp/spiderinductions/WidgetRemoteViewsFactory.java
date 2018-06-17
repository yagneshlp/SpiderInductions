package com.yagneshlp.spiderinductions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.yagneshlp.spiderinductions.helper.TasksDBHelper;
import com.yagneshlp.spiderinductions.helper.ToDoTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private ArrayList<ToDoTask> activeTaskList;
    private ToDoTask[] tasks;
    private TasksDBHelper dbHelper;



    public WidgetRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;

    }

    // Initialize the data set.

    public void onCreate() {

        activeTaskList =new ArrayList<ToDoTask>();
        dbHelper = new TasksDBHelper(mContext);

        tasks = new ToDoTask[1000];
        tasks = dbHelper.getActiveTasks();

        List<ToDoTask> items = Arrays.asList(tasks);
        items = items.subList(0,dbHelper.noOfActiveTasks);

        Log.d("WidgetRemoteViewFactory", "item list assigned");


        activeTaskList.clear();
        activeTaskList.addAll(items);

    }


    public RemoteViews getViewAt(int position) {

        // position will always range from 0 to getCount() - 1.
        // Construct a RemoteViews item based on the app widget item XML file, and set the
        // text based on the position.

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_listitem);

        // feed row

        ToDoTask data= activeTaskList.get(position);
        rv.setTextViewText(R.id.widgetListTitle, data.getTitle());
        rv.setTextViewText(R.id.widgetListSubTitle, data.getSubTitlee());
        if(data.getPriority().equals("High")) rv.setTextColor(R.id.widgetListPriority,mContext.getResources().getColor(R.color.high_priority));
        if(data.getPriority().equals("Medium")) rv.setTextColor(R.id.widgetListPriority,mContext.getResources().getColor(R.color.medium_priority));
        if(data.getPriority().equals("Low")) rv.setTextColor(R.id.widgetListPriority,mContext.getResources().getColor(R.color.low_priority));
        rv.setTextViewText(R.id.widgetListPriority, data.getPriority() + " Priority");




        Bundle extras = new Bundle();
        extras.putInt("com.yagneshlp.EXTRA_ITEM", position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("homescreen_meeting",data.title);
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

    
        return rv;

    }

    public int getCount(){

        Log.e("size=", activeTaskList.size()+"");

        return activeTaskList.size();

    }

    public void onDataSetChanged(){

        // Fetching JSON data from server and add them to records arraylist



    }

    public int getViewTypeCount(){

        return 1;

    }

    public long getItemId(int position) {

        return position;

    }

    public void onDestroy(){

        activeTaskList.clear();

    }

    public boolean hasStableIds() {

        return true;

    }

    public RemoteViews getLoadingView() {

        return null;

    }

}
