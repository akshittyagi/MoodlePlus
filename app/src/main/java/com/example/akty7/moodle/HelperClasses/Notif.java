package com.example.akty7.moodle.HelperClasses;

import android.os.Bundle;
import android.text.Spanned;

public class Notif {

    public Spanned description;
    public boolean isSeen;
    public String createdat;
    public String id;
    public String userid;

    public Notif(Bundle bundle)
    {
        isSeen = bundle.getBoolean("isseen");
        createdat = bundle.getString("createdat");
        userid = bundle.getString("userid");
        id = bundle.getString("id");
    }

}