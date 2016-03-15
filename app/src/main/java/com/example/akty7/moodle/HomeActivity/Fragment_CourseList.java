package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
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
import com.example.akty7.moodle.HelperClasses.CourseThreads;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.HelperClasses.Course;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_CourseList extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "Fragment_CourseList";
    View rootView;
    Context ctx;
    Bundle bundle;

    public Fragment_CourseList(Context c,Bundle bundle) {
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

    private ArrayList<String> getDataSet() {

        ArrayList results = new ArrayList<String>();
        final ArrayList<Course> Courses = new ArrayList<Course>();
        String url = bundle.getString("url") + "/courses/list.json";
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    //JSONObject courses = response.getJSONObject("courses");
                    JSONArray arr = response.getJSONArray("courses");
                    for(int i=0; i<arr.length();i++)
                    {
                        Bundle bundle = new Bundle();
                        JSONObject Course = (JSONObject) arr.get(i);
                        bundle.putString("coursecode",Course.getString("code"));
                        bundle.putString("coursename", Course.getString("name"));
                        bundle.putString("description", Course.getString("description"));
                        bundle.putString("credits", Course.getString("credits"));
                        bundle.putString("ltp", Course.getString("l_t_p"));
                        ArrayList<com.example.akty7.moodle.HelperClasses.Course.Assignment> a=null;
                        ArrayList<CourseThreads> c= null;
                        Course cour = new Course(bundle,a,c);
                        Courses.add(cour);

                    }
                } catch (JSONException e) {
                    Toast.makeText(ctx, "CourseList"+ e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        //TODO: Everything is now stored in arraylist of Courses "Course"
        for (int index = 0; index < 20; index++) {
            String obj = "Course "+index;
            results.add(index, obj);
        }
        return results;
    }
}
