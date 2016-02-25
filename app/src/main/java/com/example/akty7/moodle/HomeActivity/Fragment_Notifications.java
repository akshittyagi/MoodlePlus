package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.R;

import java.util.ArrayList;

public class Fragment_Notifications extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_Notifications";
    View rootView;
    Context ctx;

    public Fragment_Notifications(Context c) {
        // Required empty public constructor
        ctx = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.notif_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NotifRecyclerViewAdapter(getDataSet(),rootView.getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    //TYAGI: This method gives fake data.. Instead from the course class, take the inputs
    //Also IDK WHAT THE JSON DATA IS so make course class accordingly and add methods to get data from it..
    // then <String> Wil be replaced by <Course> everywhere

    private ArrayList<String> getDataSet() {
        ArrayList results = new ArrayList<String>();
        for (int index = 0; index < 20; index++) {
            String obj = "Notification "+index;
            results.add(index, obj);
        }
        return results;
    }
}
