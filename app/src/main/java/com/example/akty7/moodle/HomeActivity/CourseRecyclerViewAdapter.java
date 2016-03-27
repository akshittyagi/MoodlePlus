package com.example.akty7.moodle.HomeActivity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akty7.moodle.CourseActivity.Activity_Course;
import com.example.akty7.moodle.HelperClasses.Course;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Course> mDataset;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView code;
        TextView name;
        TextView desc;
        View v;
        TextView thumbletter;
        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            code = (TextView) itemView.findViewById(R.id.coursecode);
            name = (TextView) itemView.findViewById(R.id.coursename);
            desc = (TextView) itemView.findViewById(R.id.coursedesc);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
        }
    }



    public CourseRecyclerViewAdapter(ArrayList<Course> myDataset) {
        mDataset = myDataset;
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
        final Course cour = mDataset.get(position);
        holder.code.setText(cour.coursecode);
        holder.name.setText(cour.coursename);
        holder.desc.setText(cour.description);
        holder.thumbletter.setText(Character.toString(cour.coursename.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick at" + position);
                Intent intent = new Intent(v.getContext(), Activity_Course.class);
                //TYAGI putextra here for course code

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