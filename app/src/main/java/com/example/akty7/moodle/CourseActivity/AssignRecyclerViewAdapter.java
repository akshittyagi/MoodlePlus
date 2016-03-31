package com.example.akty7.moodle.CourseActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akty7.moodle.CourseChildren.Activity_Assignment;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class AssignRecyclerViewAdapter extends RecyclerView.Adapter<AssignRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Fragment_Assignments.Assignments> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView assignTitle;
        TextView assignDL;
        View v;
        TextView thumbletter;

        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            assignTitle = (TextView) itemView.findViewById(R.id.assigntitle);
            assignDL = (TextView) itemView.findViewById(R.id.assigndeadline);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
            Log.i(LOG_TAG, "Adding Listener");
        }


    }



    public AssignRecyclerViewAdapter(ArrayList<Fragment_Assignments.Assignments> myDataset, Context inctx) {
        mDataset = myDataset;
        ctx = inctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_assignment, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        final Fragment_Assignments.Assignments str = mDataset.get(position);
        final Bundle bundle = new Bundle();
        bundle.putString("name",str.name);
        bundle.putString("file",str.file);
        bundle.putString("createdat",str.createdat);
        bundle.putString("registeredcourse",str.registeredcourse);
        bundle.putString("lateallowed",str.lateallowed);
        bundle.putString("deadline",str.deadline);
        bundle.putString("desc",str.desc.toString());
        holder.assignTitle.setText(str.name);
        holder.assignDL.setText(str.deadline);
        holder.thumbletter.setText(Character.toString(str.name.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick at" + position);

                Intent intent = new Intent(ctx, Activity_Assignment.class);

                intent.putExtras(bundle);
                ctx.startActivity(intent);



            }

        });

    }

    public void addItem(Fragment_Assignments.Assignments dataObj, int index) {
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