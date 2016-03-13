package com.example.akty7.moodle.CourseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.moodle.Activity_Login;
import com.example.akty7.moodle.HomeActivity.Fragment_CourseList;
import com.example.akty7.moodle.HomeActivity.Fragment_Grades;
import com.example.akty7.moodle.HomeActivity.Fragment_Notifications;
import com.example.akty7.moodle.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Course extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context ctx;
    Bundle bundleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundleUser = getIntent().getExtras();
        ctx = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.course_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //TYAGI: Add course code as title
        setTitle("TYAGI!");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.coursepager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.course_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Assignments(ctx,bundleUser), "ASSIGNMENTS");
        adapter.addFragment(new Fragment_Threads(ctx,bundleUser), "THREADS");
        adapter.addFragment(new Fragment_Grades(ctx,bundleUser), "RESOURCES");
        adapter.addFragment(new Fragment_CourseGrades(ctx,bundleUser), "GRADES");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.course_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notifs) {
            // Handle the actions
        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_logout) {
            logout(bundleUser.getString("url"));
        } else if (id == R.id.nav_grades) {

        } else if (id == R.id.nav_courses) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.course_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(String Url)
    {
        String urlLog = Url + "/default/logout.json";
        RequestQueue q = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlLog, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    String notifcount = response.getString("noti_count");
                    Intent intent = new Intent(Activity_Course.this, Activity_Login.class);
                    startActivity(intent);
                    Activity_Course.this.finish();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Signing out", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Signing out",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
    }

}
