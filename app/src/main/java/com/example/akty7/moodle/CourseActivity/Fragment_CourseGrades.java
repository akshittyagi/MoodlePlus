package com.example.akty7.moodle.CourseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.akty7.moodle.HomeActivity.CourseRecyclerViewAdapter;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_CourseGrades extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_CourseList";
    View rootView;
    Context ctx;
    Bundle bundle;
    public Fragment_CourseGrades(Context c,Bundle bundle) {
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
        mAdapter = new CourseRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    //TYAGI: This method gives fake data.. Instead from the course class, take the inputs
    //Also IDK WHAT THE JSON DATA IS so make course class accordingly and add methods to get data from it..
    // then <String> Wil be replaced by <Course> everywhere
    public class Grades{

        String weight;
        String userid;
        String name;
        String outof;
        String courseid;
        String score;

        public Grades(Bundle bundle)
        {
            weight = bundle.getString("weight");
            userid = bundle.getString("userid");
            name = bundle.getString("name");
            outof = bundle.getString("outof");
            courseid = bundle.getString("courseid");
            score = bundle.getString("score");
        }

    }

    private ArrayList<String> getDataSet() {
        ArrayList results = new ArrayList<String>();
        bundle.putString("coursecode","cop290");
        final ArrayList<Grades> list = new ArrayList<Grades>();
        String url = bundle.getString("url") + "/courses/course.json/" + bundle.getString("coursecode") + "/grades";

        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    JSONArray grades = response.getJSONArray("grades");
                    for(int i=0;i<grades.length();i++)
                    {
                        Grades grad;
                        Bundle bundle = new Bundle();
                        JSONObject grade = (JSONObject)grades.get(i);
                        bundle.putString("weight",grade.getString("weightage"));
                        bundle.putString("userid",grade.getString("user_id"));
                        bundle.putString("name",grade.getString("name"));
                        bundle.putString("outof", grade.getString("out_of"));
                        bundle.putString("courseid",grade.getString("registered_course_id"));
                        bundle.putString("score",grade.getString("score"));
                        grad = new Grades(bundle);
                        list.add(grad);
                    }


                } catch (JSONException e) {
                    Toast.makeText(ctx, "Error Loading grades", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,"Error Loading grades",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        //TODO: list has all the assigments of the course

        for (int index = 0; index < 20; index++) {
            String obj = "Course "+index;
            results.add(index, obj);
        }
        return results;
    }
}
