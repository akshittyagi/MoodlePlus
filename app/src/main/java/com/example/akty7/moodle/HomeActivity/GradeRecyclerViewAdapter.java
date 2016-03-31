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

import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class GradeRecyclerViewAdapter extends RecyclerView.Adapter<GradeRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Fragment_Grades.Grades> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView ccode;
        TextView coutof;
        TextView cscore;
        View v;
        TextView thumbletter;
        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            ccode = (TextView) itemView.findViewById(R.id.coursecode);
            cscore = (TextView) itemView.findViewById(R.id.coursescore);
            coutof = (TextView) itemView.findViewById(R.id.courseoutof);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
        }
    }



    public GradeRecyclerViewAdapter(ArrayList<Fragment_Grades.Grades> myDataset, Context ctx) {
        mDataset = myDataset;
        Log.d("MYLOG",mDataset.size()+"");
        this.ctx =ctx;
//        Toast.makeText(ctx,mDataset.size(),Toast.LENGTH_LONG).show();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_grade, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        //final String urlL = "http://192.168.1.8:8000";
        final String urlL ="http://tapi.cse.iitd.ernet.in:1805";
        Fragment_Grades.Grades mygrad = mDataset.get(position);
        holder.ccode.setText(mygrad.code);
        holder.coutof.setText(mygrad.outof);
        holder.thumbletter.setText(Character.toString(mygrad.code.charAt(0)));
        holder.cscore.setText(mygrad.score);
    }

    public void addItem(Fragment_Grades.Grades dataObj, int index) {
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