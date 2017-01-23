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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.darkpingouin.todolist.R.id.date;

public class AddItem extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    public Spinner spinner2;
    private TextView dateView, timeView;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        dateView = (TextView) findViewById(date);
        timeView = (TextView) findViewById(R.id.time);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        try {
            showDate(year, month + 1, day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        showTime(hour, minute);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        addItemsOnSpinner2();
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
                ((TextView) findViewById(R.id.title)).setBackgroundColor(MainActivity.getCat().get(position).getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner2.setAdapter(dataAdapter);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    public void setTime(View view) {
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
        dateView.setText(MySDate);

    }

    private void showTime(int hour, int minute) {
        timeView.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }

    public void cancel(View view)
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }

    public void save(View view) throws ParseException {
        Date current = new Date();
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString().replace('<', ' ');
        String d = ((TextView) findViewById(R.id.date)).getText().toString() + " " + ((TextView) findViewById(R.id.time)).getText().toString();
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy k:m");
        Date date = newDateFormat.parse(d);
        String categorie = String.valueOf(spinner2.getSelectedItem());
        if (date.after(current)) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("title", title);
            returnIntent.putExtra("txt", txt);
            returnIntent.putExtra("date", d);
            returnIntent.putExtra("categorie", categorie);
            returnIntent.putExtra("edit", "false");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else
            Toast.makeText(getApplicationContext(), "Error you can't enter a date that is already passed !", Toast.LENGTH_SHORT).show();
    }
}
