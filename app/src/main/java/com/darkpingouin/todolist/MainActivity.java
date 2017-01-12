package com.darkpingouin.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    ListView mListView;
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item item = (Item) mListView.getAdapter().getItem(position);
                item.setText("lkjlkjlk");
                ItemAdapter a = (ItemAdapter) mListView.getAdapter();
                a.notifyDataSetChanged();
            }
        });
        ItemAdapter adapter = new ItemAdapter(MainActivity.this, items);
        mListView.setAdapter(adapter);
    }
    public static Date getDate(int day, int month, int year, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void add(View v)
    {
        Intent intentMain = new Intent(MainActivity.this, AddItem.class);
        startActivityForResult(intentMain, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String title=data.getStringExtra("title");
                String txt=data.getStringExtra("txt");
                String date=data.getStringExtra("date");
                SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy k:m");
                Date d = null;
                try {
                    d = newDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Item newItem = new Item(title, txt, d);
                addToList(newItem);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //here goes nothing
            }
        }
    }//onActivityResult

    public void addToList(Item item)
    {
        ItemAdapter a = (ItemAdapter) mListView.getAdapter();
        a.add(item);
        a.notifyDataSetChanged();
    }
}
