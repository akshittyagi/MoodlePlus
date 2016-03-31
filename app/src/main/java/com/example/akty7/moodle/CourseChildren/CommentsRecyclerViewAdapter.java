package com.example.akty7.moodle.CourseChildren;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akty7.moodle.HelperClasses.Comments;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Comments> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {



        TextView comment;
        TextView creator;
        TextView commdate;
        View v;
        TextView thumbletter;

        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            comment = (TextView) itemView.findViewById(R.id.commdesc);
            creator = (TextView) itemView.findViewById(R.id.commauthor);
            commdate = (TextView) itemView.findViewById(R.id.commcreated);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
            Log.i(LOG_TAG, "Adding Listener");
        }


    }

    public CommentsRecyclerViewAdapter(ArrayList<Comments> myDataset, Context inctx) {
        mDataset = myDataset;
        ctx = inctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comment, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        Comments str = mDataset.get(position);
        holder.comment.setText(str.description);
        holder.creator.setText(str.userid);
        holder.commdate.setText(str.createdat);
        holder.thumbletter.setText(Character.toString(str.description.charAt(0)));
    }

    public void addItem(Comments dataObj, int index) {
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