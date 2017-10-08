package com.example.bacha.employees_contact_project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by bachan on 12/8/2016.
 */

public class ContactDisplayAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;

    private HashMap<String, List<String>> child_titles;

    private Context context;
    private List<String> search_title;



    public ContactDisplayAdapter(Context context, List<String> header_titles, HashMap<String, List<String>> child_titles ){
        //this.header_titles=header_titles;
        this.header_titles = new ArrayList<>();
        this.child_titles=child_titles;
        this.context=context;
        this.search_title = header_titles;
        this.header_titles.addAll(this.search_title);
    }

    public void searchQuery(String query) {
        query = query.toLowerCase();
        header_titles.clear();

        if (query.isEmpty()) {
            header_titles.addAll(search_title);
        } else {
            for (String title: search_title) {
                List<String> childs = child_titles.get(title);
                for (String child: childs) {
                    if (child.toLowerCase().contains(query)
                            || title.toLowerCase().contains(query)) {
                        if (!header_titles.contains(title)) {
                            header_titles.add(title);
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        int numberOfGroups=header_titles.size();
        return numberOfGroups;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String headerItem = header_titles.get(groupPosition);
        List<String> childs= child_titles.get(headerItem);
        int childItemSize = childs.size();
        return childItemSize;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String heading_item= header_titles.get(groupPosition);
        return heading_item;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String headerItem = header_titles.get(groupPosition);
        List<String> childs= child_titles.get(headerItem);
        String child_item= childs.get(childPosition);
        return child_item;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) this.getGroup(groupPosition);
        if (convertView == null) ;
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_layout, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.heading_item);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) this.getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (((convertView == null)) || (((Integer) convertView.getTag()) != childPosition)) {
            if (childPosition == 0) {
                convertView = layoutInflater.inflate(R.layout.mobile_layout, null);
                convertView.setTag(0);
            }

            if (childPosition == 1) {
                convertView = layoutInflater.inflate(R.layout.office_layout, null);
                convertView.setTag(1);
            }

            if (childPosition == 2) {
                convertView = layoutInflater.inflate(R.layout.home_layout, null);
                convertView.setTag(2);
            }

            if (childPosition == 3) {
                convertView = layoutInflater.inflate(R.layout.email_layout, null);

                convertView.setTag(3);
            }

            if (childPosition == 0) {
                final TextView mobile_item = (TextView) convertView.findViewById(R.id.mobile_item);
                mobile_item.setText(title);

                Button sms_button=(Button) convertView.findViewById(R.id.smsBtn_mobile);
                sms_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mobile_no=(String) mobile_item.getText();
                        mobile_no= mobile_no.substring(mobile_no.indexOf(":")+0, mobile_no.length());
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:" + Uri.encode(mobile_no)));
                        context.startActivity(intent);
                    }
                });

                Button callBtn_mobile=(Button) convertView.findViewById(R.id.callBtn_mobile);
                callBtn_mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mobile_no=(String) mobile_item.getText();
                        mobile_no= mobile_no.substring(mobile_no.indexOf(":")+0, mobile_no.length());
                        Intent phoneCallIntent= new Intent(new Intent(Intent.ACTION_CALL));
                        phoneCallIntent.setData(Uri.parse("tel:"+mobile_no));
                        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            return;
                        }
                        context.startActivity(phoneCallIntent);
                    }
                });

            }
            //////////////////////////////////
            if (childPosition == 1) {
                final TextView office_item = (TextView) convertView.findViewById(R.id.office_item);
                office_item.setText(title);

                Button callBtn_office=(Button) convertView.findViewById(R.id.officeBtn_mobile);
                callBtn_office.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String office_no = (String) office_item.getText();
                        office_no = office_no.substring(office_no.indexOf(":") + 1, office_no.length());
                        Intent phoneCallIntent = new Intent(new Intent(Intent.ACTION_CALL));
                        phoneCallIntent.setData(Uri.parse("tel:" + office_no));
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(phoneCallIntent);
                    }
                });
            }

            if (childPosition == 2) {
                final TextView home_item = (TextView) convertView.findViewById(R.id.home_item);
                home_item.setText(title);

                Button callBtn_home=(Button) convertView.findViewById(R.id.homeBtn_mobile);
                callBtn_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String home_no = (String) home_item.getText();
                        home_no = home_no.substring(home_no.indexOf(":") + 2, home_no.length());
                        Intent phoneCallIntent = new Intent(new Intent(Intent.ACTION_CALL));
                        phoneCallIntent.setData(Uri.parse("tel:" + home_no));
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(phoneCallIntent);
                    }
                });
            }

            if (childPosition == 3) {
                final TextView email_item = (TextView) convertView.findViewById(R.id.email_item);
                email_item.setText(title);
            }
        }
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
