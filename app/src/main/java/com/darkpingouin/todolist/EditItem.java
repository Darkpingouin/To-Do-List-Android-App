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
import java.util.Date;
import java.util.List;

/**
 * Lorsqu'une task est éditée
 */
public class EditItem extends AppCompatActivity {

    int year, month, day, hour, minute;
    boolean cancel;
    Spinner spinner2;
    String previousDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancel = false;
        setContentView(R.layout.activity_edit_item);
        String title = getIntent().getStringExtra("title");
        String txt = getIntent().getStringExtra("txt");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        previousDate = date + " " + time;
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

    /**
     * Ajoute les catégories dans le spinner
     */
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

    /**
     * Set la date
     * @param view
     */
    public void setDate(View view) {
            showDialog(999);
    }

    /**
     * set l'heure
     * @param view
     */
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

    /**
     * Aaffiche la date
     * @param year
     * @param month
     * @param day
     * @throws ParseException
     */
    private void showDate(int year, int month, int day) throws ParseException {
        String d = (String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date MyDate = newDateFormat.parse(d);
        newDateFormat.applyPattern("EE d MMM yyyy");
        String MySDate = newDateFormat.format(MyDate);
        ((TextView) findViewById(R.id.date2)).setText(MySDate);
    }

    /**
     * Affiche l'heure
     * @param hour
     * @param minute
     */
    private void showTime(int hour, int minute) {
        ((TextView) findViewById(R.id.time2)).setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }
    /**
     * Supprime la tache
     * @param v
     */
    public void delete(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_task);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((TextView) findViewById(R.id.title)).getText().toString();
                String txt = ((TextView) findViewById(R.id.txt)).getText().toString();
                String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
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
        });
        dialog.show();
    }

    /**
     * Enregistre les modifications
     * @param v
     * @throws ParseException
     */
    public void save(View v) throws ParseException {
        Date current = new Date();
        String title = ((TextView) findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) findViewById(R.id.txt)).getText().toString().replace('<', ' ');
        String d = ((TextView) findViewById(R.id.date2)).getText().toString() + " " + ((TextView) findViewById(R.id.time2)).getText().toString();
        String categorie = String.valueOf(spinner2.getSelectedItem());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy k:m");
        Date date = newDateFormat.parse(d);
        Date oldDate = newDateFormat.parse(previousDate);
        if (date.after(current) || date.equals(oldDate)) {
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
        else
            Toast.makeText(getApplicationContext(), "Error you can't enter a date that is already passed !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Ferme la vue
     * @param v
     */
    public void cancel(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
