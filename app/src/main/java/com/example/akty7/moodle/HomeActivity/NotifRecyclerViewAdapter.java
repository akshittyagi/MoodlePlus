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
import com.example.akty7.moodle.HelperClasses.Notif;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class NotifRecyclerViewAdapter extends RecyclerView.Adapter<NotifRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Notif> mDataset;
    Context ctx;
    //private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView sender;
        TextView text;
        TextView date;
        View v;
        TextView thumbletter;

        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            sender = (TextView) itemView.findViewById(R.id.sender);
            text = (TextView) itemView.findViewById(R.id.text);
            date = (TextView) itemView.findViewById(R.id.date);
            thumbletter=(TextView) itemView.findViewById(R.id.thumb);
            Log.i(LOG_TAG, "Adding Listener");
        }


    }



    public NotifRecyclerViewAdapter(ArrayList<Notif> myDataset,Context inctx) {
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
        final Notif notif = mDataset.get(position);
        holder.text.setText(notif.description);
        holder.date.setText(notif.createdat);
        holder.sender.setText(notif.userid);

        holder.thumbletter.setText(Character.toString(notif.userid.charAt(0)));
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick at" + position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(notif.description)
                        .setTitle(notif.createdat)
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

    public void addItem(Notif dataObj, int index) {
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