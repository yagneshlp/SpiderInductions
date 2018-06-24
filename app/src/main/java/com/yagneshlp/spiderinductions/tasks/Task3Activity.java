package com.yagneshlp.spiderinductions.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.yagneshlp.spiderinductions.widget.SimpleWidgetProvider;
import com.yagneshlp.spiderinductions.helper.TaskListAdapter;
import com.yagneshlp.spiderinductions.R;
import com.yagneshlp.spiderinductions.helper.RecyclerItemTouchHelper;
import com.yagneshlp.spiderinductions.helper.RecyclerTouchListener;
import com.yagneshlp.spiderinductions.helper.TasksDBHelper;
import com.yagneshlp.spiderinductions.helper.ToDoTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task3Activity extends Activity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    MutativeFab addTask;
    ToDoTask newTask;
    TasksDBHelper dbHelper;
    ToDoTask[] tasks;
    private static final String TAG = Task3Activity.class.getSimpleName();
    private RecyclerView recyclerViewActive,recyclerViewCompleted;
    private List<ToDoTask> activeTaskList,completedTaskList;
    private TaskListAdapter taskListAdapterActive,taskListAdapterCompleted;
    private CoordinatorLayout coordinatorLayout;
    TextView tvA,tvC,tvM;
    String priorityState = "High";
    boolean isUndo = false;
    ImageView tickIcon;




    @Override
    public void onBackPressed() {
        Intent updateWidget=new  Intent(getApplicationContext(), SimpleWidgetProvider.class);
        updateWidget.setAction(SimpleWidgetProvider.Update_tasks);
        sendBroadcast(updateWidget);
        finishAffinity();
    }
    @Override
    public void onResume(){
        super.onResume();
        Intent updateWidget=new  Intent(getApplicationContext(), SimpleWidgetProvider.class);
        updateWidget.setAction(SimpleWidgetProvider.Update_tasks);
        sendBroadcast(updateWidget);

    }
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        Intent updateWidget=new  Intent(getApplicationContext(), SimpleWidgetProvider.class);
        updateWidget.setAction(SimpleWidgetProvider.Update_tasks);
        sendBroadcast(updateWidget);

        dbHelper = new TasksDBHelper(getApplicationContext());


        tvA =findViewById(R.id.ActivedTaskMessage);
        tvC = findViewById(R.id.completedTaskMessage);
        tvM = findViewById(R.id.textViewMessage0Atasks);
        tickIcon = findViewById(R.id.completed_icon);



        recyclerViewActive = findViewById(R.id.recycler_view);
        recyclerViewCompleted = findViewById(R.id.recycler_view_completed);

        coordinatorLayout = findViewById(R.id.coordinator_layout);

        activeTaskList = new ArrayList<>();
        completedTaskList = new ArrayList<>();

        taskListAdapterActive = new TaskListAdapter(this, activeTaskList);
        taskListAdapterCompleted = new TaskListAdapter(this,completedTaskList);

        recyclerViewCompleted.setNestedScrollingEnabled(false);
        recyclerViewActive.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager mLayoutManagerActive = new LinearLayoutManager(getApplicationContext());
        recyclerViewActive.setLayoutManager(mLayoutManagerActive);
        recyclerViewActive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActive.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewActive.setAdapter(taskListAdapterActive);

        RecyclerView.LayoutManager mLayoutManagerCompleted = new LinearLayoutManager(getApplicationContext());
        recyclerViewCompleted.setLayoutManager(mLayoutManagerCompleted);
        recyclerViewCompleted.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCompleted.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewCompleted.setAdapter(taskListAdapterCompleted);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT , this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewActive);

        loadItemsOntoActiveTasksList();
        loadItemsOntoCompletedTasksList();

        addTask = findViewById(R.id.buttonAddTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "fab pressed");
                newTask = new ToDoTask();
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.layout_addtaskdialog, null);
                final EditText etTitle = alertLayout.findViewById(R.id.et_title);
                final EditText etSubTitle = alertLayout.findViewById(R.id.et_subTitle);
                final CheckBox cbH = alertLayout.findViewById(R.id.checkBoxHigh),cbM = alertLayout.findViewById(R.id.checkBoxMedium),cbL= alertLayout.findViewById(R.id.checkBoxLow);
                cbH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            priorityState = "High";
                            if(cbM.isChecked()) cbM.setChecked(false);
                            if(cbL.isChecked()) cbL.setChecked(false);
                        }else{
                            if(!cbM.isChecked() && !cbL.isChecked()) cbH.setChecked(true);
                        }
                    }
                });
                cbM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            priorityState = "Medium";
                            if(cbH.isChecked()) cbH.setChecked(false);
                            if(cbL.isChecked()) cbL.setChecked(false);
                        }
                        else{
                            if(!cbH.isChecked() && !cbL.isChecked()) cbM.setChecked(true);
                        }
                    }
                });
                cbL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            priorityState = "Low";
                            if(cbM.isChecked()) cbM.setChecked(false);
                            if(cbH.isChecked()) cbH.setChecked(false);
                        }
                        else{
                            if(!cbM.isChecked() && !cbH.isChecked()) cbL.setChecked(true);
                        }
                    }
                });


                AlertDialog.Builder alert = new AlertDialog.Builder(Task3Activity.this);
                alert.setTitle("Add Task");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(etTitle.getText().toString().length() !=0){
                            newTask.title = etTitle.getText().toString();
                            newTask.subTitle = etSubTitle.getText().toString();
                            newTask.priority = priorityState;
                            dbHelper.addTask(newTask);
                            tasks = dbHelper.getActiveTasks();
                            Intent updateWidget=new  Intent(getApplicationContext(), SimpleWidgetProvider.class);
                            updateWidget.setAction(SimpleWidgetProvider.Update_tasks);
                            sendBroadcast(updateWidget);
                            loadItemsOntoActiveTasksList();
                        }else{
                            Toast.makeText(Task3Activity.this,"Task not added due to insufficient Details!\nTry again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });

        recyclerViewActive.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerViewActive, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, final int position) {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
                AlertDialog.Builder alert = new AlertDialog.Builder(Task3Activity.this);
                alert.setTitle("Delete task");
                alert.setMessage("Do you really want to delete the task?");
                alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToDoTask deleteTask = activeTaskList.get(position);
                        dbHelper.deleteTask(deleteTask);
                        loadItemsOntoActiveTasksList();
                    }
                });

                alert.setPositiveButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        }));


    }





    private void loadItemsOntoActiveTasksList() {

        Log.d(TAG, "Items loading into Active tasks List....");

        if(dbHelper.getNoOfActiveTasks() == 0){
            tvA.setVisibility(View.GONE);
            tvM.setVisibility(View.VISIBLE);
        }else{
            tvA.setVisibility(View.VISIBLE);
            tvM.setVisibility(View.GONE);
        }

        if(dbHelper.getNoOfCompletedTasks() == 0){
            tvC.setVisibility(View.GONE);
        }else{
            tvC.setVisibility(View.VISIBLE);
        }

        tasks = new ToDoTask[1000];
        tasks = dbHelper.getActiveTasks();

        List<ToDoTask> items = Arrays.asList(tasks);
        items = items.subList(0,dbHelper.noOfActiveTasks);

        Log.d(TAG, "item list assigned");

        // adding items to cart list
        activeTaskList.clear();
        activeTaskList.addAll(items);

        // refreshing recycler view
        taskListAdapterActive.notifyDataSetChanged();
        Log.d(TAG, "Items loaded into Active tasks List succesfully");

        Intent updateWidget=new  Intent(getApplicationContext(), SimpleWidgetProvider.class);
        updateWidget.setAction(SimpleWidgetProvider.Update_tasks);
        sendBroadcast(updateWidget);

    }

    private void loadItemsOntoCompletedTasksList() {

        Log.d(TAG, "Items loading into Completed tasks List....");
        if(dbHelper.getNoOfActiveTasks() == 0){
            tvA.setVisibility(View.GONE);
            tvM.setVisibility(View.VISIBLE);
        }else{
            tvA.setVisibility(View.VISIBLE);
            tvM.setVisibility(View.GONE);
        }

        if(dbHelper.getNoOfCompletedTasks() == 0){
            tvC.setVisibility(View.GONE);
        }else{
            tvC.setVisibility(View.VISIBLE);
        }

        tasks = new ToDoTask[1000];
        tasks = dbHelper.getDeadTasks();

        List<ToDoTask> items = Arrays.asList(tasks);
        items = items.subList(0,dbHelper.noOfCompletedTasks);

        Log.d(TAG, "item list assigned");

        // adding items to cart list
        completedTaskList.clear();
        completedTaskList.addAll(items);

        // refreshing recycler view
        taskListAdapterCompleted.notifyDataSetChanged();
        Log.d(TAG, "Items loaded into completed tasks List succesfully");

    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        Log.d(TAG, "swipe direction:" + direction);


        if (viewHolder instanceof TaskListAdapter.MyViewHolder && direction == 4) {                                  //direction = 4 for Left Direction = 8 for right
            // get the removed item name to display it in snack bar
            String name = activeTaskList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final ToDoTask deletedItem = activeTaskList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            taskListAdapterActive.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " Has been Completed! ", 3000);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    isUndo=true;
                    // undo is selected, restore the deleted item
                    taskListAdapterActive.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {

                    if(!isUndo){
                    Log.d(TAG, "Snackbar dismissed");
                    ToDoTask transferTask = deletedItem;
                    dbHelper.transferTask(transferTask);
                    loadItemsOntoCompletedTasksList();
                    }
                    isUndo = false;

                }

                @Override
                public void onShown(Snackbar snackbar) {
                }
            });
            snackbar.show();
        }
    }
}
