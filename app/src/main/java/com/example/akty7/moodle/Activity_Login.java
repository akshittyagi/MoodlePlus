package com.example.akty7.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.moodle.HomeActivity.Activity_Home;

import org.json.JSONObject;

public class Activity_Login extends AppCompatActivity {

    EditText editTxt1 = null;
    EditText editTxt2 = null;
    Button button = null;

    private String userName = null;
    private String passWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTxt1 = (EditText) findViewById(R.id.editText);
        editTxt2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);

        //TODO : (BACKEND) Strings for login
        userName = editTxt1.getText().toString();
        passWord = editTxt2.getText().toString();

        boolean succ = login(userName,passWord);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(loginCheck(userName, passWord)){
                    Intent intent = new Intent(Activity_Login.this, Activity_Home.class);
                    Activity_Login.this.startActivity(intent);
                    Activity_Login.this.finish();
                }
            }
        });


    }
    String url = "http://tapi.cse.iitd.ernet.in:1805";

    public boolean login(String username,String password)
    {
        String urlJsonObj =url + "/default/login.json?userid=" + username + "&password=" + password;
        boolean ret=false;

        RequestQueue q = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


    try {
        boolean successful = (response.getString("success").equals("true"));
        if (successful) {
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

        } else {

            Toast.makeText(getApplicationContext(),
                    "User not found",
                    Toast.LENGTH_LONG).show();

        }
    } catch (org.json.JSONException e) {
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

        return ret;
    }


    public boolean loginCheck(String user, String pass){
        //TODO
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
