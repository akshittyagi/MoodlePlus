package com.example.akty7.moodle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BackendServerHandler extends AppCompatActivity {

    String url = "http://192.168.1.4:8000";
    String username="cs5110300";
    String password="shubham";
    //1
    String loginAPI = url + "/default/login.json?userid=" + username + "&password=" + password;
    //2
    String logoutAPI = url + "/default/logout.json";
    //3
    String listCoursesAPI = url + "/courses/list.json";
    //4
    String allNotifsAPI = url + "/default/notifications.json";
    //5
    String allGradesAPI = url + "/default/grades.json";
    //coursecode should be in lowercase;
    String coursecode="cop290";
    //6
    String listAssignsAPI = url + "/courses/course.json/" + coursecode + "/assignments";
    String assignment_number="1";
    //7
    String particAssgnAPI = url + "/courses/assignment.json/" + assignment_number;
    //8
    String coursegradesAPI = url + "courses/course.json/" + coursecode + "/grades";
    //9
    String listCoursThreadAPI = url + "/courses/course.json/" + coursecode + "/threads";
    String thread_number="1";
    //10
    String particThreadAPI = url + "/threads/thread.json/" + thread_number;
    String title="New", desc="Fuck";
    //11
    String newThreadAPI = url + "/threads/new.json?title=/" + title + "&description=" + desc + "&course_code=" + coursecode;
    String thread_id="1";
    //12
    String addCommentThreadAPI = url + "/threads/post_comment.json?thread_id=" + thread_id + "&description=" + desc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int code = 4;
        interactWithServer(code);



    }


    public void makeJsonObjectRequest(final int code) {

        String urlJsonObj = null;
        switch(code)
        {
            case 1: urlJsonObj = loginAPI;
                break;
            case 2: urlJsonObj = logoutAPI;
                break;
            case 3: urlJsonObj = listCoursesAPI;
                break;
            case 4: urlJsonObj = allNotifsAPI;
                break;
            case 5: urlJsonObj = allGradesAPI;
                break;
            case 6: urlJsonObj = listAssignsAPI;
                break;
            case 7: urlJsonObj = particAssgnAPI;
                break;
            case 8: urlJsonObj = coursegradesAPI;
                break;
            case 9: urlJsonObj = listCoursThreadAPI;
                break;
            case 10: urlJsonObj = particThreadAPI;
                break;
            case 11: urlJsonObj = newThreadAPI;
                break;
            case 12: urlJsonObj = addCommentThreadAPI;
                break;
            default: urlJsonObj = null;
        }
        RequestQueue q = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Parsing json object response
                    // response will be a json object
                    switch(code) {

                        case 1:
                            boolean successful = (response.getString("success").equals("true")?true:false);

                            if (successful)
                            {
                                JSONObject user = response.getJSONObject("user");
                                String lastname = user.getString("last_name");
                                String id = user.getString("mobile");
                                String firstName = user.getString("first_name");
                                String entryNo = user.getString("entry_no");
                                String email = user.getString("email");
                                String username = user.getString("username");
                                String registrationID = user.getString("registration_id");
                                String password = user.getString("password");
                                String type = user.getString("type_");
                            }
                            else
                            {

                                Toast.makeText(getApplicationContext(),
                                        "User not found",
                                        Toast.LENGTH_LONG).show();
                            }

                            break;

                        case 2:
                            String notifCount = response.getString("noti_count");
                            break;
                        case 3:
                            String current_sem = response.getString("current_sem");
                            JSONObject courses = response.getJSONObject("courses");
                            JSONArray arr = response.getJSONArray("courses");
                            for(int i=0; i<arr.length();i++)
                            {
                                JSONObject Course = (JSONObject) arr.get(i);
                                String code = Course.getString("code");
                                String name = Course.getString("name");
                                String description = Course.getString("description");
                                String credits = Course.getString("3");
                                String id = Course.getString("id");
                                String ltp = Course.getString("l_t_p");
                                //To
                            }
                            break;
                        case 4:

                            JSONArray array = response.getJSONArray("notifications");
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject notifs = (JSONObject)array.get(i);
                                String userid = notifs.getString("user_id");
                                String description = notifs.getString("description");
                                Html.fromHtml(description);
                                boolean isSeen = !(notifs.getString("is_seen")=="0");
                                String createdAt = notifs.getString("created_at");
                                String id = notifs.getString("id");
                            }

                            break;

                        case 5:
                            JSONArray arrayGrades = response.getJSONArray("courses");
                            for(int i=0 ; i<arrayGrades.length();i++)
                            {
                                JSONObject grades = (JSONObject)arrayGrades.get(i);
                                String code = grades.getString("code");
                                String name = grades.getString("name");
                                String description = grades.getString("description");
                                String credits = grades.getString("credits");
                                String id = grades.getString("id");
                                String ltp = grades.getString("l_t_p");
                            }

                            break;

                        case 6:
                            JSONArray arrayAssignments = response.getJSONArray("assignments");
                            for(int i=0;i<arrayAssignments.length();i++)
                            {
                                JSONObject grades = (JSONObject) arrayAssignments.get(i);
                                String name = grades.getString("name");
                                String file = grades.getString("file_");
                                String createdat = grades.getString("created_at");
                                String registeredcourseid = grades.getString("registered_course_id");
                                boolean lateAllowed = (grades.getString("late_days_allowed")=="1");
                                String type = grades.getString("type_");
                                String deadline = grades.getString("deadline");
                                String id = grades.getString("id");
                                String description = grades.getString("description");
                                Html.fromHtml(description);

                            }

                            break;

                        case 7:
                            JSONObject assgn = response.getJSONObject("assignment");
                            String name = assgn.getString("name");
                            String file = assgn.getString("file_");
                            String createdat = assgn.getString("created_at");
                            String registeredcourseid = assgn.getString("registered_course_id");
                            boolean lateAllowed = (assgn.getString("late_days_allowed")=="1");
                            String type = assgn.getString("type_");
                            String deadline = assgn.getString("deadline");

                            JSONObject regis = response.getJSONObject("regsitered");
                            String startdate = regis.getString("starting_date");
                            String id = regis.getString("id");
                            String professor = regis.getString("professor");
                            String semester = regis.getString("semester");
                            String enddate = regis.getString("ending_date");
                            String year = regis.getString("year_");
                            String coureID = regis.getString("course_id");

                            //submissions empty
                            JSONArray submissions = response.getJSONArray("submissions");

                            JSONObject course = response.getJSONObject("course");
                            String code = course.getString("code");
                            String cName = course.getString("name");
                            String description = course.getString("description");
                            String credits = course.getString("credits");
                            String cID = course.getString("id");
                            String ltp = course.getString("l_t_p");

                            break;

                        case 8:
                            JSONArray grades = response.getJSONArray("grades");
                            for(int i=0;i<grades.length();i++)
                            {
                                JSONObject grade = (JSONObject)grades.get(i);
                                String weightage = grade.getString("weightage");
                                String userid = grade.getString("user_id");
                                String nameAssgn = grade.getString("name");
                                String out_of = grade.getString("out_of");
                                String regisCourseID = grade.getString("registered_course_id");
                                String score = grade.getString("score");
                                String courseID = grade.getString("id");
                            }

                            String tab = response.getString("tab");
                            String yearOfCourse = response.getString("year");
                            String sem = response.getString("sem");

                            //res empty
                            JSONArray res = response.getJSONArray("resources");

                            //prev empty
                            JSONArray prev = response.getJSONArray("previous");
                            break;

                        case 9:

                            JSONArray coursethreads = response.getJSONArray("course_threads");
                            for(int i=0;i<coursethreads.length();i++)
                            {
                                JSONObject thread = (JSONObject) coursethreads.get(i);
                                String user_id = thread.getString("user_id");
                                String descriptionTh = thread.getString("description");
                                String title = thread.getString("title");
                                String created_at = thread.getString("created_at");
                                String regiscourseid = thread.getString("registered_course_id");
                                String updatedAt = thread.getString("updated_at");
                                String Threadid = thread.getString("id");
                            }
                            break;

                        case 10:

                            JSONArray timesreadable = response.getJSONArray("times_readable");
                            for(int i=0;i<timesreadable.length();i++)
                            {
                                String times = timesreadable.get(i).toString();
                            }

                            JSONArray comments = response.getJSONArray("comments");
                            for(int i=0;i<comments.length();i++)
                            {
                                JSONObject comm = (JSONObject) comments.get(i);
                                String userid = comm.getString("user_id");
                                String descriptionComm = comm.getString("description");
                                String created_at = comm.getString("created_at");
                                String threadid = comm.getString("thread_id");
                                String ID = comm.getString("id");
                            }

                            JSONObject thread = response.getJSONObject("thread");
                            String userid = thread.getString("user_id");
                            String THdescription = thread.getString("description");
                            String title = thread.getString("title");
                            String createdAt = thread.getString("created_at");
                            String registeredCourseID = thread.getString("registered_course_id");
                            String updatedAt = thread.getString("updated_at");
                            String Id = thread.getString("id");

                            JSONArray commUsers = response.getJSONArray("comment_users");
                            for(int i=0;i<commUsers.length();i++)
                            {
                                JSONObject user = (JSONObject) commUsers.get(i);
                                String lastName = user.getString("last_name");
                                String Usid = user.getString("id");
                                String firstName = user.getString("first_name");
                                String entry_no = user.getString("entry_no");
                                String email = user.getString("email");
                                String username = user.getString("username");
                                String password = user.getString("password");
                                String Type = user.getString("type_");
                            }

                            break;

                        case 11:
                            String thread_id = response.getString("thread_id");
                            boolean success = (response.getString("success")=="true");
                            break;
                        case 12:
                            boolean IsSuccessful = response.getString("success")=="true";
                            if(IsSuccessful) {
                                JSONObject comm = response.getJSONObject("comment");
                                String userID = comm.getString("user_id");
                                String descriptionComm = comm.getString("description");
                                String created = comm.getString("created_at");
                                String threadID = comm.getString("thread_id");
                                String USid = comm.getString("id");

                                String username = response.getString("user_name");

                                JSONObject user = response.getJSONObject("user");
                                String usrName = user.getString("username");
                                String entryNo = user.getString("entry_no");
                                String USID = user.getString("id");
                                String typeUs = user.getString("type_");
                                String email = user.getString("email");

                                String timeReadable = user.getString("time_readable");
                                break;
                            }
                            else{
                                break;
                            }

                        default:
                            Toast.makeText(getApplicationContext(),"Wrong input",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });

        // Adding request to request queue

        q.add(jsonObjReq);
    }

    public void interactWithServer(final int code) {
        RequestQueue queue = Volley.newRequestQueue(this);


        switch (code)
        {
            case 1:
                StringRequest stringRequest = new StringRequest(Request.Method.GET, loginAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                        //{"user": {"last_name": "Jindal", "reset_password_key": "", "registration_key": "", "id": 4, "first_name": "Shubham", "entry_no": "2011CS50300", "email": "cs5110300@cse.iitd.ac.in", "username": "cs5110300", "registration_id": "", "password": "shubham", "type_": 0}, "success": true}

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
                break;
            case 2:
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, logoutAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                        //{"noti_count": 4}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest2);
                break;
            case 3:
                StringRequest stringRequest3 = new StringRequest(Request.Method.GET, listCoursesAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                        //{}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest3);
                break;
            case 4:
                StringRequest stringRequest4 = new StringRequest(Request.Method.GET, allNotifsAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest4);
                break;
            case 5:
                StringRequest stringRequest5 = new StringRequest(Request.Method.GET, allGradesAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest5);
                break;
            case 6:
                StringRequest stringRequest6 = new StringRequest(Request.Method.GET, listAssignsAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest6);
                break;
            case 7:
                StringRequest stringRequest7 = new StringRequest(Request.Method.GET, particAssgnAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest7);
                break;
            case 8:
                StringRequest stringRequest8 = new StringRequest(Request.Method.GET, coursegradesAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest8);
                break;
            case 9:
                StringRequest stringRequest9 = new StringRequest(Request.Method.GET, listCoursThreadAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest9);
                break;
            case 10:
                StringRequest stringRequest10 = new StringRequest(Request.Method.GET, particThreadAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest10);
                break;
            case 11:
                StringRequest stringRequest11 = new StringRequest(Request.Method.GET, newThreadAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest11);
                break;
            case 12:
                StringRequest stringRequest12 = new StringRequest(Request.Method.GET, addCommentThreadAPI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Success " + response, Toast.LENGTH_LONG).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest12);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_LONG).show();
                BackendServerHandler.this.finish();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}