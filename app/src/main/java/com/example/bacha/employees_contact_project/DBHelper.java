package com.example.bacha.employees_contact_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.Calendar;

/**
 * Created by bachan on 11/24/2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private long lastQueryMS = 0;
    Cursor cursor;

    public static final String DATABASE_NAME="employee_db";
    public static final String TABLE_NAME="emp_contacts";


    public static final String create_query= "create table "+TABLE_NAME+
            "(emp_ID TEXT, emp_Name TEXT, mobile_no TEXT, office_no TEXT, home_no TEXT, email TEXT)";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);

    }

    public boolean insertData(SQLiteDatabase db, String emp_id, String empName, String mobileNo, String officeNo, String homeNo, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put("emp_ID",emp_id);
        contentValues.put("emp_Name", empName);
        contentValues.put("mobile_no", mobileNo);
        contentValues.put("office_no", officeNo);
        contentValues.put("home_no", homeNo);
        contentValues.put("email", email);

        long result= db.insert(TABLE_NAME, null, contentValues);
        if(result== -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Cursor getAllData(SQLiteDatabase db) {
        db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("Select * from emp_contacts", null);
        return cursor;
    }

    /*public Cursor searchData(SQLiteDatabase db,String key){
       // if(Calendar.getInstance().getTimeInMillis()-lastQueryMS >500){
            //lastQueryMS = Calendar.getInstance().getTimeInMillis();
            db = this.getReadableDatabase();
            String [] columns= {"emp_ID","emp_Name", "mobile_no","office_no", "home_no", "email"};
            String [] arguments= {key,key,key,key,key,key};
            //Cursor res= db.rawQuery("Select * from emp_contacts where emp_ID = key", null);
           // String dbSearchQuery = "emp_ID LIKE '?%' OR emp_Name LIKE '?%' OR mobile_no LIKE '?%' OR office_no LIKE '?%' OR home_no LIKE '?%' OR email LIKE '?%'";
            //cursor  = db.query(true,"emp_contacts",columns,"Bachan Ghimire",arguments,null,null,null,null);
       // }
            //cursor = db.rawQuery("Select * from emp_contacts where name='' ", null);
       return cursor;
    }*/

}
