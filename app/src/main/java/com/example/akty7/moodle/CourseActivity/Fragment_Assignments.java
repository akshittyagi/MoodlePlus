package com.example.akty7.moodle.CourseActivity;

import android.content.Context;
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
import com.example.akty7.moodle.HelperClasses.Course;
import com.example.akty7.moodle.HomeActivity.CourseRecyclerViewAdapter;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Assignments extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_CourseList";
    View rootView;
    Context ctx;
    Bundle bundle;

    public Fragment_Assignments(Context c,Bundle bundle) {
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
        rootView = inflater.inflate(R.layout.fragment_course_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.course_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AssignRecyclerViewAdapter(getDataSet(),ctx);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    //TYAGI: This method gives fake data.. Instead from the course class, take the inputs
    //Also IDK WHAT THE JSON DATA IS so make course class accordingly and add methods to get data from it..
    // then <String> Wil be replaced by <Course> everywhere
    public class Assignments{
        String name;
        String file;
        String createdat;
        String registeredcourse;
        String lateallowed;
        String deadline;
        Spanned desc;

        public Assignments(Bundle bundle)
        {
            name = bundle.getString("name");
            file = bundle.getString("file_");
            createdat = bundle.getString("created_at");
            registeredcourse = bundle.getString("registered_course_id");
            lateallowed = (bundle.getString("late_days_allowed"));
            deadline = bundle.getString("deadline");
        }
    }
    private ArrayList<Assignments> getDataSet() {
        final ArrayList<Assignments> list = new ArrayList<Assignments>();

        RequestQueue q = Volley.newRequestQueue(ctx);
        String url = bundle.getString("url") +"/courses/course.json/" + bundle.getString("coursecode") + "/assignments";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray arrayAssignments = response.getJSONArray("assignments");
                    for(int i=0;i<arrayAssignments.length();i++)
                    {
                        Assignments ass;
                        Bundle bundle = new Bundle();
                        JSONObject grades = (JSONObject) arrayAssignments.get(i);
                        bundle.putString("name",grades.getString("name"));
                        bundle.putString("file_", grades.getString("file_"));
                        bundle.putString("created_at", grades.getString("created_at"));
                        bundle.putString("registered_course_id", grades.getString("registered_course_id"));
                        bundle.putString("late_days_allowed", grades.getString("late_days_allowed"));
                        bundle.putString("deadline",grades.getString("deadline"));
                        String description = grades.getString("description");
                        ass = new Assignments(bundle);
                        ass.desc = Html.fromHtml(description);
                        list.add(ass);
                    }

                } catch (JSONException e) {
                    Toast.makeText(ctx, "Error Loading Assignments", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ctx,"Error Loading Assignments",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        //TODO: list contains the list of assignments of a particular course

        return list;
    }
}
