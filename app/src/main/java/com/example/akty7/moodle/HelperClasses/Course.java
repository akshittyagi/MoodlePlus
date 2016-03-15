package com.example.akty7.moodle.HelperClasses;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by akty7 on 08-Mar-16.
 */
public class Course {
        String coursecode;
        String coursename;
        String description;
        String ltp;
        String credits;
        ArrayList<Assignment> assignments;
        ArrayList<CourseThreads> coursethreads;

        public Course()
        {
            coursecode=null;
            coursename=null;
            description=null;
            ltp=null;
            credits=null;
            assignments=null;
            coursethreads=null;
        }

        public Course(Bundle bundle,ArrayList<Assignment> ass,ArrayList<CourseThreads> coursethread)
        {
            coursecode = bundle.getString("coursecode");
            coursename = bundle.getString("coursename");
            ltp = bundle.getString("ltp");
            credits = bundle.getString("credits");
            description = bundle.getString("description");

            if(ass!=null) {
                for (int i = 0; i < ass.size(); i++) {
                    assignments.add(ass.get(i));
                }
            }

            if(coursethread!=null)
            {
                for(int i=0;i<coursethread.size();i++)
                {
                    coursethreads.add(coursethread.get(i));
                }
            }

        }

    /**
     * Created by akty7 on 08-Mar-16.
     */
    public static class Assignment {

        String name;
        String file;
        String createdat;
        String registeredcourseid;
        boolean lateallowed;
        String deadline;

    }
}


