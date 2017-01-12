package com.darkpingouin.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditItem extends AppCompatActivity {

    int year, month, day, hour, minute;
    boolean editDate = false;
    boolean editTime = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String title = getIntent().getStringExtra("title");
        String txt = getIntent().getStringExtra("txt");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        ((TextView) findViewById(R.id.time2)).setText(time);
        ((TextView) findViewById(R.id.date2)).setText(date);
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.txt)).setText(txt);
    }

    public void setDate(View view) {
        if (editDate)
            showDialog(999);
    }

    public void setTime(View view) {
        if (editTime)
            showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }

        if (id == 998) {
            return new TimePickerDialog(this, myTimeListener, hour, minute, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    try {
                        showDate(arg1, arg2 + 1, arg3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
    private TimePickerDialog.OnTimeSetListener myTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker arg0,
                                      int arg1, int arg2) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showTime(arg1, arg2);
                }
            };

    private void showDate(int year, int month, int day) throws ParseException {
        String d = (String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date MyDate = newDateFormat.parse(d);
        newDateFormat.applyPattern("EE d MMM yyyy");
        String MySDate = newDateFormat.format(MyDate);
        ((TextView) findViewById(R.id.date2)).setText(MySDate);
    }

    private void showTime(int hour, int minute) {
        ((TextView) findViewById(R.id.time2)).setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }
    public void edit(View v)
    {
        TextView textView = (TextView) findViewById(R.id.txt);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setFocusable(true);
        titleView.setFocusableInTouchMode(true);
        editDate = true;
        editTime = true;
        TextView save = (TextView) findViewById(R.id.save);
        save.setVisibility(View.VISIBLE);
    }

    public void delete(View v)
    {
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString();
        String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title",title);
        returnIntent.putExtra("txt", txt);
        returnIntent.putExtra("date", d);
        returnIntent.putExtra("edit", "true");
        returnIntent.putExtra("position", getIntent().getStringExtra("position"));
        returnIntent.putExtra("delete", "true");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void save(View v)
    {
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString();
        String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title",title);
        returnIntent.putExtra("txt", txt);
        returnIntent.putExtra("date", d);
        returnIntent.putExtra("edit", "true");
        returnIntent.putExtra("position", getIntent().getStringExtra("position"));
        returnIntent.putExtra("delete", "false");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void cancel(View v)
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }
}
