package com.example.akty7.moodle.HomeActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
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
import com.example.akty7.moodle.Activity_Login;
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
        // Required empty public constructor

        ctx = c;
        this.bundle = bundle;
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

    public class Notif{

        Spanned description;
        boolean isSeen;
        String createdat;
        String id;
        String userid;

        public Notif(Bundle bundle)
        {
            isSeen = bundle.getBoolean("isseen");
            createdat = bundle.getString("createdat");
            userid = bundle.getString("userid");
            id = bundle.getString("id");
        }

    }

    private ArrayList<String> getDataSet() {
        ArrayList results = new ArrayList<String>();

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

        //TODO: Everything is now stored in arraylist of Notifs "notifics"
        //TODO: desc is a Spanned type object which has to be put into edittext apne app htmp parse ho jayeg


        for (int index = 0; index < 20; index++) {
            String obj = "Notification "+index;
            results.add(index, obj);
        }
        return results;
    }
}
