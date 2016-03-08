package com.example.akty7.moodle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    EditText editTxt1;
    EditText editTxt2;
    Button button;


    //String url = "http://tapi.cse.iitd.ernet.in:1805";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTxt1 = (EditText) findViewById(R.id.editText);
        editTxt2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);

        editTxt2.setHint("Password");
        final Context context = this;

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String userName = editTxt1.getText().toString();
                final String passWord = editTxt2.getText().toString();
                final String url = "http://192.168.1.4:8000";
                String urlJsonObj = url + "/default/login.json?userid=" + userName + "&password=" + passWord;

                RequestQueue q = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        urlJsonObj, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Bundle bundle = new Bundle();
                        bundle.putString("url",url);
                        try {
                            boolean successful = (response.getString("success").equals("true"));
                            if(successful){

                                //TODO:Bundling
                                JSONObject user = response.getJSONObject("user");
                                String lastname = user.getString("last_name");
                                bundle.putString("lastname",lastname);
                                String id = user.getString("id");
                                bundle.putString("id",id);
                                String firstName = user.getString("first_name");
                                bundle.putString("firstname",firstName);
                                String entryNo = user.getString("entry_no");
                                bundle.putString("entryNo",entryNo);
                                String email = user.getString("email");
                                bundle.putString("email",email);
                                String username = user.getString("username");
                                bundle.putString("username",username);
                                String password = user.getString("password");
                                bundle.putString("password",password);
                                String type = user.getString("type_");
                                bundle.putString("type",type);
                                Intent intent = new Intent(Activity_Login.this, Activity_Home.class);
                                intent.putExtras(bundle);
                                Activity_Login.this.startActivity(intent);
                                Activity_Login.this.finish();

                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "User not found",
                                        Toast.LENGTH_LONG).show();

                            }
                        } catch (org.json.JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Activity_Login.this, Activity_Home.class);
                            intent.putExtras(bundle);
                            Activity_Login.this.startActivity(intent);
                            Activity_Login.this.finish();
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
            q.add(jsonObjReq);
            }

            });


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
