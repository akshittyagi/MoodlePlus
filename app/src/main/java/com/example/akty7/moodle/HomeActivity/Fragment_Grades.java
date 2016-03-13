package com.example.akty7.moodle.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.moodle.Activity_Login;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Grades extends Fragment {


    private static String LOG_TAG = "Fragment_Grades";
    View rootView;
    TableLayout t1;


    Bundle bundle;
    Context ctx;
    public Fragment_Grades(Context ctx,Bundle bundle) {
        // Required empty public constructor
        this.ctx = ctx;
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
        rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        t1 = (TableLayout) rootView.findViewById(R.id.overallgradetable);

        //TYAGI: THIS getGrades function needs backend integration.. After this
        //Change <String> to whatever (Make own class here only)
        // The for loop below should be changed to read values from the json

        //KARAN: Formatting of the ROWS!!!


        ArrayList<String> input = getGrades();
        for (String i:input) {
            TableRow tr_head = new TableRow(rootView.getContext());
            tr_head.setId(10);
            tr_head.setBackgroundColor(Color.GRAY);        // part1
            tr_head.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));


            TextView label_hello = new TextView(rootView.getContext());
            label_hello.setId(20);
            label_hello.setText("HELLO");
            label_hello.setTextColor(Color.WHITE);          // part2
            label_hello.setPadding(5, 5, 5, 5);
            tr_head.addView(label_hello);// add the column to the table row here

            TextView label_android = new TextView(rootView.getContext());    // part3
            label_android.setId(21);// define id that must be unique
            label_android.setText("ANDROID..!!"); // set the text for the header
            label_android.setTextColor(Color.WHITE); // set the color
            label_android.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(label_android); // add the column to the table row here

            t1.addView(tr_head, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,                    //part4
                    TableRow.LayoutParams.MATCH_PARENT));

        }

        return rootView;
    }
    //TYAGI: This method gives fake data.. Instead from the course class, take the inputs
    //Also IDK WHAT THE JSON DATA IS so make course class accordingly and add methods to get data from it..
    // then <String> Wil be replaced by <Course> everywhere

    public class Grades{

        String code;
        String description;
        String name;

        public Grades(String code,String description,String name)
        {
            this.code = code;
            this.description = description;
            this.name = name;
        }



    }
    private ArrayList<String> getGrades() {
        ArrayList results = new ArrayList<String>();
        final ArrayList<Grades> grad = new ArrayList<Grades>();
        String url = bundle.getString("url") + "/default/grades.json";
       
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    JSONArray arrayGrades = response.getJSONArray("courses");
                    for(int i=0 ; i<arrayGrades.length();i++)
                    {
                        JSONObject grades = (JSONObject)arrayGrades.get(i);
                        String code = grades.getString("code");
                        String name = grades.getString("name");
                        String description = grades.getString("description");
                        Grades g = new Grades(code,description,name);
                        grad.add(g);
                    }


                } catch (JSONException e) {
                    Toast.makeText(ctx, "Grades" + e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.toString() ,Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        //TODO: All data in grades arraylist

        for (int index = 0; index < 7; index++) {
            String obj = "Notification "+index;
            results.add(index, obj);
        }
        return results;
    }
}
