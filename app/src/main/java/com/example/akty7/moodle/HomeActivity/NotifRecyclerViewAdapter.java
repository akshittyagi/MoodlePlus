package com.example.akty7.moodle.HomeActivity;

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

import com.example.akty7.moodle.CourseChildren.Activity_Assignment;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class NotifRecyclerViewAdapter extends RecyclerView.Adapter<NotifRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<String> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseCode;
        View v;
        TextView thumbletter;

        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            courseName = (TextView) itemView.findViewById(R.id.textView);
            courseCode = (TextView) itemView.findViewById(R.id.textView2);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
            Log.i(LOG_TAG, "Adding Listener");
        }


    }



    public NotifRecyclerViewAdapter(ArrayList<String> myDataset,Context inctx) {
        mDataset = myDataset;
        ctx = inctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notif, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        String str = mDataset.get(position);
        holder.courseName.setText(str);
        holder.courseCode.setText(str);
        holder.thumbletter.setText(Character.toString(str.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick at" + position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Dummy Message "+position)
                        .setTitle("Dummy Title "+position)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                Intent intent = new Intent(ctx, Activity_Assignment.class);
                ctx.startActivity(intent);


            }

        });

    }

    public void addItem(String dataObj, int index) {
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