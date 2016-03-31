package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.akty7.moodle.HelperClasses.CourseThreads;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Grades extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_Grades";
    View rootView;
    Context ctx;
    Bundle bundle;
    boolean isSpecific;

    public Fragment_Grades(Context c,Bundle bundle,boolean isSpecific) {
        // Required empty public constructor
        ctx = c;
        this.bundle = bundle;
        this.isSpecific = isSpecific;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.grades_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GradeRecyclerViewAdapter(getGrades(),ctx);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
    //TYAGI: This method gives fake data.. Instead from the course class, take the inputs
    //Also IDK WHAT THE JSON DATA IS so make course class accordingly and add methods to get data from it..
    // then <String> Wil be replaced by <Course> everywhere

    public class Grades{

        public String code;
        public String description;
        public String name;
        public String score;
        public String weight;
        public String outof;

    }


    private ArrayList<Grades> getGrades() {

        String url = bundle.getString("url");

        if(!isSpecific){
            url = url + "/default/grades.json";
        }
        else
        {
            url = url + "/courses/course.json/"+bundle.getString("coursecode")+"/grades";
        }

        final ArrayList<Grades> ret=new ArrayList<Grades>();

        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray arrayCourse = response.getJSONArray("courses");
                    JSONArray arrayGrades = response.getJSONArray("grades");
                    for(int i=0;i<arrayGrades.length();i++)
                    {
                        JSONObject course = (JSONObject)arrayCourse.get(i);
                        JSONObject grade = (JSONObject)arrayGrades.get(i);
                        Grades g=new Grades();
                        g.code = course.getString("code");
                        g.description=course.getString("description");
                        g.name=course.getString("name");
                        g.score=grade.getString("score");
                        g.weight=grade.getString("weightage");
                        g.outof=grade.getString("out_of");
                        ret.add(g);
                        Log.d("RETTwT", ret.size() + "");

                    }
                } catch (JSONException e) {
                    Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        Log.d("RETTT",ret.size()+"");
        return ret;
    }

}
