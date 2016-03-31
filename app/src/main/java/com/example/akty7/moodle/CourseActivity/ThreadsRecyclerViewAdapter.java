package com.example.akty7.moodle.CourseActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.akty7.moodle.HelperClasses.Thread;
import com.example.akty7.moodle.CourseChildren.Activity_Comments;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class ThreadsRecyclerViewAdapter extends RecyclerView.Adapter<ThreadsRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Thread> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView threadTitle;
        TextView threadCreator;
        View v;
        TextView thumbletter;

        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            threadTitle = (TextView) itemView.findViewById(R.id.textView);
            threadCreator = (TextView) itemView.findViewById(R.id.textView2);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
            Log.i(LOG_TAG, "Adding Listener");
        }


    }



    public ThreadsRecyclerViewAdapter(ArrayList<Thread> myDataset, Context inctx) {
        mDataset = myDataset;
        ctx = inctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_threadlist, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        final Thread str = mDataset.get(position);
        final Bundle bundle = new Bundle();
        bundle.putString("userid",str.user_id);
        bundle.putString("description",str.descriptionTh);
        bundle.putString("title",str.title);
        bundle.putString("createdat",str.created_at);
        bundle.putString("registeredcourse",str.regiscourseid);
        bundle.putString("updatedat",str.updatedAt);
        bundle.putString("Threadid",str.Threadid);
        holder.threadTitle.setText(str.title);
        holder.threadCreator.setText(str.user_id);
        holder.thumbletter.setText(Character.toString(str.title.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick at" + position);

                Intent intent = new Intent(ctx, Activity_Comments.class);
                //intent.putextra
                intent.putExtras(bundle);
                        ctx.startActivity(intent);
                //TYAGII do this putextra

            }

        });

    }

    public void addItem(Thread dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {

        return mDataset.size();
    }

}