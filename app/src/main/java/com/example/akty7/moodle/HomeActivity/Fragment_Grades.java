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

        TableRow tr_head = new TableRow(rootView.getContext());
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.parseColor("#3F51B5"));
        tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView title_id = new TextView(rootView.getContext());
        title_id.setId(20);
        title_id.setText("CODE");
        title_id.setTextColor(Color.BLACK);          // part2
        title_id.setPadding(5, 5, 5, 5);
        tr_head.addView(title_id);// add the column to the table row here

        TextView title_course = new TextView(rootView.getContext());    // part3
        title_course.setId(21);// define id that must be unique
        title_course.setText("NAME"); // set the text for the header
        title_course.setTextColor(Color.BLACK); // set the color
        title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(title_course); // add the column to the table row here

        TextView label_desc = new TextView(rootView.getContext());    // part3
        title_course.setId(22);// define id that must be unique
        title_course.setText("SCORE"); // set the text for the header
        title_course.setTextColor(Color.BLACK); // set the color
        title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_desc); // add the column to the table row here

        TextView label_outof = new TextView(rootView.getContext());    // part3
        title_course.setId(23);// define id that must be unique
        title_course.setText("OUT OF"); // set the text for the header
        title_course.setTextColor(Color.BLACK); // set the color
        title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_outof); // add the column to the table row here

        TextView label_weight = new TextView(rootView.getContext());    // part3
        title_course.setId(24);// define id that must be unique
        title_course.setText("WEIGHT"); // set the text for the header
        title_course.setTextColor(Color.BLACK); // set the color
        title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight); // add the column to the table row here

        t1.addView(tr_head, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.MATCH_PARENT));
        ArrayList<Grades> grad= getGrades();

        for( Grades g : grad){
            TableRow tr_body = new TableRow(rootView.getContext());
            tr_head.setId(10);
            tr_head.setBackgroundColor(Color.parseColor("#03A9F4"));
            tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView lbl_code = new TextView(rootView.getContext());
            title_id.setId(20);
            title_id.setText(g.code);
            title_id.setTextColor(Color.BLACK);          // part2
            title_id.setPadding(5, 5, 5, 5);
            tr_head.addView(lbl_code);// add the column to the table row here

            TextView lbl_course = new TextView(rootView.getContext());    // part3
            title_course.setId(21);// define id that must be unique
            title_course.setText(g.name); // set the text for the header
            title_course.setTextColor(Color.BLACK); // set the color
            title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(lbl_course); // add the column to the table row here

            TextView lbl_grade = new TextView(rootView.getContext());    // part3
            title_course.setId(22);// define id that must be unique
            title_course.setText(g.score); // set the text for the header
            title_course.setTextColor(Color.BLACK); // set the color
            title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(lbl_grade); // add the column to the table row here

            TextView lbl_outof = new TextView(rootView.getContext());    // part3
            title_course.setId(23);// define id that must be unique
            title_course.setText(g.outof); // set the text for the header
            title_course.setTextColor(Color.BLACK); // set the color
            title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(lbl_outof); // add the column to the table row here

            TextView lbl_weight = new TextView(rootView.getContext());    // part3
            title_course.setId(24);// define id that must be unique
            title_course.setText(g.weight); // set the text for the header
            title_course.setTextColor(Color.BLACK); // set the color
            title_course.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(lbl_weight); // add the column to the table row here

            t1.addView(tr_head, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.MATCH_PARENT));
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
        String score;
        String weight;
        String outof;

        public Grades(String code,String description,String name,String score,String weight,String outof)
        {
            this.code = code;
            this.description = description;
            this.name = name;
            this.score = score;
            this.weight = weight;
            this.outof = outof;
        }



    }

    private ArrayList<Grades> getGrades() {

        boolean isSpecific = bundle.getBoolean("isSpecific");
        String url = bundle.getString("url");
        if(!isSpecific)
        {
            url = url + "/default/grades.json";
        }
        else
        {
            url = url + "/courses/course.json/"+bundle.getString("courscode")+"/grades";
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
                            Grades g;
                            g = new Grades(course.getString("code"),course.getString("description"),course.getString("name"),grade.getString("score"),grade.getString("weightage"),grade.getString("out_of"));
                            ret.add(g);
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

        return ret;
    }
}

