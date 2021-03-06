package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akty7.moodle.CourseActivity.Activity_Course;
import com.example.akty7.moodle.HelperClasses.Course;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Course> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView code;
        TextView name;
        View v;
        TextView thumbletter;
        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            code = (TextView) itemView.findViewById(R.id.coursecode);
            name = (TextView) itemView.findViewById(R.id.coursename);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
        }
    }



    public CourseRecyclerViewAdapter(ArrayList<Course> myDataset,Context ctx) {
        mDataset = myDataset;
        this.ctx =ctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_course, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        //final String urlL = "http://192.168.1.8:8000";
        final String urlL ="http://tapi.cse.iitd.ernet.in:1805";
     //   final Course cour = mDataset.get(position);
        final Course mycour = mDataset.get(position);
        final Bundle bundle = new Bundle();
        bundle.putString("coursename",mycour.coursename);
        bundle.putString("coursecode",mycour.coursecode);
        bundle.putString("description",mycour.description);
        bundle.putString("credits",mycour.credits);
        bundle.putString("ltp",mycour.ltp);
        bundle.putString("url",urlL);

        holder.code.setText(mycour.coursecode);
        holder.name.setText(mycour.coursename);
        holder.thumbletter.setText(Character.toString(mycour.coursename.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, "onClick at" + position);

                Intent intent = new Intent(ctx, Activity_Course.class);
                //TYAGI putextra here for course code
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }

        });

    }

    public void addItem(Course dataObj, int index) {
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