package com.darkpingouin.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditItem extends AppCompatActivity {

    int year, month, day, hour, minute;
    boolean editDate = false;
    boolean editTime = false;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String title = getIntent().getStringExtra("title");
        String txt = getIntent().getStringExtra("txt");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String categorie = getIntent().getStringExtra("categorie");
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        addItemsOnSpinner2();
        int i = 0;
        int selection = 0;
        while (i < MainActivity.getCat().size()) {
            if (categorie.equals(MainActivity.getCat().get(i).getName())) {
                ((RelativeLayout) findViewById(R.id.textBar)).setBackgroundColor(MainActivity.getCat().get(i).getColor());
                ((RelativeLayout) findViewById(R.id.textBar1)).setBackgroundColor(MainActivity.getCat().get(i).getColor());
                ((TextView) findViewById(R.id.title)).setBackgroundColor(MainActivity.getCat().get(i).getColor());
                selection = i;
            }
            i++;
        }
        spinner2.setSelection(selection);
        ((TextView) findViewById(R.id.time2)).setText(time);
        ((TextView) findViewById(R.id.date2)).setText(date);
        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.txt)).setText(txt);
    }

    public void addItemsOnSpinner2() {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (i < MainActivity.getCat().size()) {
            list.add(MainActivity.getCat().get(i).getName());
            i++;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((RelativeLayout) findViewById(R.id.textBar)).setBackgroundColor(MainActivity.getCat().get(position).getColor());
                ((RelativeLayout) findViewById(R.id.textBar1)).setBackgroundColor(MainActivity.getCat().get(position).getColor());
                ((TextView) findViewById(R.id.title)).setBackgroundColor(MainActivity.getCat().get(position).getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner2.setAdapter(dataAdapter);
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

    public void edit(View v) {
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

    public void delete(View v) {
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString();
        String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
        //String categorie = ((TextView) findViewById(R.id.categorie)).getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", title);
        returnIntent.putExtra("txt", txt);
        returnIntent.putExtra("date", d);
        returnIntent.putExtra("edit", "true");
        returnIntent.putExtra("position", getIntent().getStringExtra("position"));
        returnIntent.putExtra("categorie", "null");
        returnIntent.putExtra("delete", "true");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void save(View v) {
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString().replace('<', ' ');
        String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
        String categorie = String.valueOf(spinner2.getSelectedItem());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", title);
        returnIntent.putExtra("txt", txt);
        returnIntent.putExtra("date", d);
        returnIntent.putExtra("edit", "true");
        returnIntent.putExtra("position", getIntent().getStringExtra("position"));
        returnIntent.putExtra("categorie", categorie);
        returnIntent.putExtra("delete", "false");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void cancel(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
