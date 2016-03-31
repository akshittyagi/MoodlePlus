package com.example.akty7.moodle;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Activity_Landing extends FragmentActivity{

    private static final int pageNum = 1;
    private static final int splashDisplayTime = 2500;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(isFirstTime()){
            setContentView(R.layout.activity_screen_slide);
            Button button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent mainIntent = new Intent(Activity_Landing.this, Activity_Login.class);
                    Activity_Landing.this.startActivity(mainIntent);
                    Activity_Landing.this.finish();
                }
            });
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setPageTransformer(true, new ZoomOutEffect());
            mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    invalidateOptionsMenu();
                }
            });
        }
        else {
            setContentView(R.layout.activity_splash_screen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Activity_Landing.this, Activity_Login.class);
                    Activity_Landing.this.startActivity(mainIntent);
                    Activity_Landing.this.finish();
                }
            }, splashDisplayTime);
        }

    }

    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem() == 0){
            super.onBackPressed();
        }
        else{
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean firstTime = preferences.getBoolean("FirstTime", false);
        if(!firstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("FirstTime", true);
            editor.commit();
        }
        return !firstTime;
    }

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter{
        public ScreenSliderPagerAdapter(FragmentManager FM){
            super(FM);
        }

        @Override
        public Fragment getItem(int pos){
            return Fragment_Intro.create(pos);
        }

        @Override
        public int getCount(){
            return pageNum;
        }
    }

}
