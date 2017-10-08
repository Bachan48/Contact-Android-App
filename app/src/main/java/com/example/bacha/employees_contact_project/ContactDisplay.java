package com.example.bacha.employees_contact_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class ContactDisplay extends AppCompatActivity {

    String json_url;
    String JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    EditText editText;
    String json_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi, greetings from Bachan.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        BackgroundTask obj=new BackgroundTask();
        obj.execute();
    }


    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String result ;
        DBHelper DBHelper;
        SQLiteDatabase db;

        @Override
        protected String doInBackground(Void... params) {


            try {
                ///////////////////////////////////
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(7000);
                httpURLConnection.setReadTimeout(7000);
                httpURLConnection.connect();


                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {

                        stringBuilder.append(JSON_STRING + "\n");

                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    result = stringBuilder.toString().trim();
                    Log.i("ContactDisplay", "Data fetched Successfully");
                    Log.i("Result: ",result);
                } else {
                    result = null;
                    Log.i("ContactDisplay", "Data Cannot Be Fetched");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPreExecute() {
            json_url="http://10.0.2.2/contactdbconnect/dataservice.php";
        }

        @Override
        protected void onPostExecute(String result) {

            json_str=result;
            Thread thread= new Thread()
            {
                @Override
                public void run(){
                    try {
                        if (json_str != null) {

                            int count=0;
                            String empID, empName, mobileNO, officeNo, homeNo, email;

                            jsonObject  = new JSONObject(json_str);
                            jsonArray = jsonObject.getJSONArray("server_response");
                            DBHelper = new DBHelper(ContactDisplay.this);
                            db= DBHelper.getWritableDatabase();
                            db.execSQL("DELETE from emp_contacts");
                            db.beginTransaction();

                            while (count<jsonArray.length()){

                                JSONObject row= jsonArray.getJSONObject(count);
                                empID=row.getString("empID");
                                empName= row.getString("empName");
                                mobileNO=row.getString("mobileNo");
                                officeNo= row.getString("officeNo");
                                homeNo= row.getString("homeNo");
                                email= row.getString("email");
                                DBHelper.insertData(db, empID, empName, mobileNO, officeNo, homeNo, email);
                                count++;

                            }

                            db.setTransactionSuccessful();
                            Log.i("Status:", "Inserted Successfully");

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        db.endTransaction();
                        db.close();
                    }

                }
            };
            thread.start();

            Intent intent=new Intent(getApplicationContext(), ContactDisplayView.class);
            startActivity(intent);



        }
    }

}
