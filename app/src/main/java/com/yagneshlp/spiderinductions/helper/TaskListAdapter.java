package com.yagneshlp.spiderinductions.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.yagneshlp.spiderinductions.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder>  {
    private Context context;
    private List<ToDoTask> cartList;
    private static final String TAG = TaskListAdapter.class.getSimpleName();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
           // price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public TaskListAdapter(Context context, List<ToDoTask> cartList) {
        this.context = context;
        this.cartList = cartList;
    }


        @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_listitem, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ToDoTask item = cartList.get(position);
        Log.d(TAG, "cart item: " + position + item.getTitle() + item.subTitle + item.priority);
        holder.name.setText(item.getTitle());
        holder.description.setText(item.getSubTitlee());
        //holder.price.setText(item.getPriority());

      if(item.getPriority().equals("High")){
          Glide.with(context)
                  .load(getImage("high"))
                  .into(holder.thumbnail);
      }
      if(item.getPriority().equals("Medium")){
            Glide.with(context)
                    .load(getImage("medium"))
                    .into(holder.thumbnail);
       }
       if(item.getPriority().equals("Low")){
            Glide.with(context)
                    .load(getImage("low"))
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ToDoTask item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
}