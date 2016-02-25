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
import android.view.Window;

public class Activity_Landing extends FragmentActivity{

    private static final int pageNum = 2;
    private static final int splashDisplayTime = 2500;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(isFirstTime()){
            setContentView(R.layout.activity_screen_slide);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                NavUtils.navigateUpTo(this, new Intent(this, Login_Activity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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
            switch(pos){
                case 1: return Fragment_Intro.create(pos);
                case 2: return Fragment_Intro.create(pos);

            }
            return null;

        }

        @Override
        public int getCount(){
            return pageNum;
        }
    }

}
