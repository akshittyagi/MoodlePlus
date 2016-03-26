package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.moodle.HelperClasses.Notif;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Notifications extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_Notifications";
    View rootView;
    Context ctx;
    Bundle bundle;

    public Fragment_Notifications(Context c,Bundle bundle) {
        ctx = c;
        this.bundle = bundle;
    }


    public Fragment_Notifications() {
        // Required empty public constructor

        ctx = null;
        bundle = new Bundle();
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



    private ArrayList<Notif> getDataSet() {


        String url = bundle.getString("url") + "/default/notifications.json";
        final ArrayList<Notif> notifics = new ArrayList<Notif>();

        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray array = response.getJSONArray("notifications");
                    for(int i=0; i<array.length(); i++)
                    {
                        Notif temp;
                        Bundle bundle = new Bundle();
                        JSONObject notifs = (JSONObject)array.get(i);
                        bundle.putString("userid", notifs.getString("user_id"));
                        bundle.putBoolean("isseen", !(notifs.getString("is_seen") == "0") ) ;
                        bundle.putString("createdat", notifs.getString("created_at"));
                        bundle.putString("id",notifs.getString("id"));
                        temp = new Notif(bundle);
                        String desc = notifs.getString("description");
                        temp.description  = Html.fromHtml(desc);
                        notifics.add(temp);
                    }

                } catch (JSONException e) {
                    Toast.makeText(ctx, "Notif"+e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return notifics;
    }
}
