package com.example.akty7.moodle.CourseChildren;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.akty7.moodle.R;

public class Activity_Assignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.assign_toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        ((TextView) findViewById(R.id.assname)).setText(bundle.getString("name"));
        ((TextView) findViewById(R.id.link)).setText("Link to file: "+bundle.getString("file"));
        ((TextView) findViewById(R.id.uploaddate)).setText("Upload Date: "+bundle.getString("createdat"));
        ((TextView) findViewById(R.id.assdead)).setText("Deadline: " + bundle.getString("deadline"));
        ((TextView) findViewById(R.id.late)).setText("Late days allowed: " +  bundle.getString( "lateallowed"));
        ((TextView) findViewById(R.id.courseidass)).setText("Course: " + bundle.getString("registeredcourse"));
        ((TextView) findViewById(R.id.desc)).setText(bundle.getString("desc"));



    }

}
