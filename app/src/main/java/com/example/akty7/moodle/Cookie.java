package com.example.akty7.moodle;


import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by akty7 on 13-Mar-16.
 */
public class Cookie extends Application{




        CookieHandler cookieManage;
        public void onCreate() {
            cookieManage= new CookieManager();
            CookieHandler.setDefault(cookieManage);
            super.onCreate();
        }



}
