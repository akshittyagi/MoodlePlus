package com.example.akty7.moodle.CourseChildren;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.moodle.CourseActivity.AssignRecyclerViewAdapter;
import com.example.akty7.moodle.DividerItemDecoration;
import com.example.akty7.moodle.HelperClasses.Comments;
import com.example.akty7.moodle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Comments extends AppCompatActivity {
    Context ctx;
    String thread_id;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.comments_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle bundle = getIntent().getExtras();
        thread_id=bundle.getString("Threadid");
        ctx = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CommentsRecyclerViewAdapter(getDataSet(),this);
        mRecyclerView.setAdapter(mAdapter);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("New Comment ");
                final EditText input = new EditText(ctx);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        String url = "http://tapi.cse.iitd.ernet.in:1805"+"/threads/post_comment.json?thread_id="+thread_id+"&description="+m_Text;
                        RequestQueue q = Volley.newRequestQueue(ctx);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response){

                                try {
                                    boolean IsSuccessful = response.getString("success")=="true";
                                    if(!IsSuccessful)
                                    {
                                        Toast.makeText(ctx,"Error Adding Comment",Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(ctx,"Error Adding Comment",Toast.LENGTH_LONG).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ctx,"Error Adding Comment",Toast.LENGTH_LONG).show();
                            }
                        });
                        q.add(jsonObjectRequest);






                        //TYAGI: JSON API TO SEND THIS AS THE NEW COMMENT
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    public ArrayList<Comments> getDataSet()
    {
        final ArrayList<Comments> list = new ArrayList<>();
        String url = "http://tapi.cse.iitd.ernet.in:1805"+"/threads/thread.json/"+thread_id;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    JSONArray comments = response.getJSONArray("comments");
                    for(int i=0;i<comments.length();i++)
                    {
                        Comments c=new Comments();
                        JSONObject comm = (JSONObject) comments.get(i);
                        String userid = comm.getString("user_id");
                        String descriptionComm = comm.getString("description");
                        String created_at = comm.getString("created_at");
                        String threadid = comm.getString("thread_id");
                        String ID = comm.getString("id");

                        c.createdat = created_at;
                        c.description = descriptionComm;
                        c.userid = userid;
                        c.threadid=ID;
                        list.add(c);

                    }



                } catch (JSONException e) {
                    Toast.makeText(ctx,"Error Loading Comments",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,"Error Loading Comments",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return list;
    }

}
