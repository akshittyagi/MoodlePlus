package com.example.akty7.moodle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Activity handling splash screen
public class Fragment_Intro extends Fragment {

    public static final String argPage = "page";
    private int mPageNumber;

    public static Fragment_Intro create(int pageNumber){
        Fragment_Intro fragment = new Fragment_Intro();
        Bundle args = new Bundle();
        args.putInt(argPage, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(argPage);
    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle SavedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_splash, container, false);
        return rootView;
    }

    public int getPageNumber(){
        return mPageNumber;
    }

}
