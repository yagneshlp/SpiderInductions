package com.yagneshlp.spiderinductions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.yagneshlp.spiderinductions.helper.TasksDBHelper;
import com.yagneshlp.spiderinductions.helper.ToDoTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sampleActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = sampleActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<ToDoTask> cartList;
    private TaskListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;

    ToDoTask[] tasks;
    TasksDBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        getSupportActionBar().setTitle("Tasks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new TasksDBHelper(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        cartList = new ArrayList<>();
        mAdapter = new TaskListAdapter(this, cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        // making http call and fetching menu json
        prepareCart();
    }

    /**
     * method make volley network call and parses json
     */
    private void prepareCart() {

        Log.d(TAG, "prepare cart executing...");

        tasks = new ToDoTask[1000];
        tasks = dbHelper.getActiveTasks();

        List<ToDoTask> items = Arrays.asList(tasks);
        items = items.subList(0,dbHelper.noOfActiveTasks);

        Log.d(TAG, "item list assigned");

        // adding items to cart list
        cartList.clear();
        cartList.addAll(items);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "Prepare cart executed succesfully");

    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        Log.d(TAG, "swipe direction:" + direction);

        if (viewHolder instanceof TaskListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final ToDoTask deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds cartList to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
