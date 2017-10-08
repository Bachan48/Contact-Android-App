package com.example.bacha.employees_contact_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import com.example.bacha.employees_contact_project.employee.EmployeeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;


public class ContactDisplayView extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    ExpandableListView expandableListView ;
    DBHelper helper;
    ContactDisplayAdapter contactDisplayAdapter;
    SearchView searchView;
    EmployeeDTO employee = new EmployeeDTO();
    final List<String> headings = new ArrayList<String>();
    final HashMap<String, List<String>> childList = new HashMap<String, List<String>>();
    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display_view);
        searchView = (android.widget.SearchView) findViewById(R.id.search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);


        helper = new DBHelper(ContactDisplayView.this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = helper.getAllData(db);

        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        this.loadData(cursor);


       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                loadData((helper.searchData(db, query)));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadData((helper.searchData(db, newText)));
                return false;

            }
        })*/


    }



    @Override
    public boolean onQueryTextChange(String s) {
        contactDisplayAdapter.searchQuery(s);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        contactDisplayAdapter.searchQuery(s);
        return false;
    }

    @Override
    public boolean onClose() {
        contactDisplayAdapter.searchQuery("");
        return false;
    }
    public void loadData(Cursor cursor){

        try {
            int count=0;
            while (cursor.moveToNext()) {

                list = new ArrayList<String>();
                employee.setEmployeeName(cursor.getString(1));
                employee.setPhoneMobile(cursor.getString(2));
                employee.setPhoneOffice(cursor.getString(3));
                employee.setPhoneHome(cursor.getString(4));
                employee.setEmail(cursor.getString(5));
                list.add("Mobile No:" + employee.getPhoneMobile());
                list.add("Office No:" + employee.getPhoneOffice());
                list.add("Home No:" + employee.getPhoneHome());
                list.add("Email:" + employee.getEmail());
                headings.add(employee.getEmployeeName());
                childList.put(employee.getEmployeeName(), list);
                count++;

            }

            String countInt= Integer.toString(count);
            Log.i("count", countInt);
            contactDisplayAdapter = new ContactDisplayAdapter(this, headings, childList);
            expandableListView.setAdapter(contactDisplayAdapter);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


}



