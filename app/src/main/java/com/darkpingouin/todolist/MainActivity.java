package com.darkpingouin.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    int id;
    public static ListView mListView, checkListView;
    public static List<Item> items = new ArrayList<>();
    public static List<Item> tmp = new ArrayList<>();
    public static ArrayList<Categorie> cat = new ArrayList<>();


    TextView nb_tasks;
    public static boolean aff_done, aff_todo, aff_passed, aff_ondate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        checkListView = (ListView) findViewById(R.id.checkCat);
        nb_tasks = (TextView) findViewById(R.id.nb_tasks);
        aff_done = true;
        aff_todo = true;
        aff_passed = true;
        aff_ondate = true;
        id = 0;

        CheckBox checkToDo = (CheckBox) findViewById(R.id.switch_todo);
        checkToDo.setChecked(true);
        checkToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    aff_todo = true;
                else
                    aff_todo = false;
                affListCorresponding();
            }
        });
        CheckBox checkDone = (CheckBox) findViewById(R.id.switch_done);
        checkDone.setChecked(true);
        checkDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    aff_done = true;
                else
                    aff_done = false;
                affListCorresponding();
            }
        });
        CheckBox checkPassed = (CheckBox) findViewById(R.id.switch_passed);
        checkPassed.setChecked(true);
        checkPassed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    aff_passed = true;
                else
                    aff_passed = false;
                affListCorresponding();
            }
        });
        CheckBox checkOnDate = (CheckBox) findViewById(R.id.switch_ondate);
        checkOnDate.setChecked(true);
        checkOnDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    aff_ondate = true;
                else
                    aff_ondate = false;
                affListCorresponding();
            }
        });


        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            getCatData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        if (cat.size() == 0)
            cat.add(new Categorie("none", Color.parseColor("#DFDFDF")));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentMain = new Intent(MainActivity.this, EditItem.class);
                Item item = (Item) mListView.getAdapter().getItem(position);
                String title = item.getTitle();
                String time = item.getTime();
                String txt = item.getText();
                String date = item.getDate();
                String categorie = item.getCategorie();
                intentMain.putExtra("position", String.valueOf(position));
                intentMain.putExtra("title", title);
                intentMain.putExtra("txt", txt);
                intentMain.putExtra("date", date);
                intentMain.putExtra("time", time);
                intentMain.putExtra("categorie", categorie);
                startActivityForResult(intentMain, 1);
            }
        });

        ItemAdapter adapter = new ItemAdapter(MainActivity.this, items);
        checkAdapter adapter1 = new checkAdapter(MainActivity.this, cat);
        checkListView.setAdapter(adapter1);
        mListView.setAdapter(adapter);
        checkDate();
    }

    public List<Item> dataToItems(String data) throws ParseException, XmlPullParserException, IOException {
        List<Item> list = new ArrayList<>();
        Item tmp;
        String title = "";
        String txt = "";
        String status = "";
        String cat = "";
        Date date = new Date();
        int f = 0;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(data));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.TEXT) {
                if (f == 0)
                    title = xpp.getText();
                else if (f == 1) {
                    String d = xpp.getText();
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyyHH:mm");
                    date = newDateFormat.parse(d);
                }
                else if (f == 2)
                    status = xpp.getText();
                else if (f == 3)
                    txt = xpp.getText();
                else if (f == 4)
                    cat = xpp.getText();
                f++;
            }
            if (f == 5) {
                tmp = new Item(title, txt, date);
                if (status.equals(Item.Status.DONE.toString()))
                    tmp.setStatus(Item.Status.DONE);
                else
                    tmp.setStatus(Item.Status.TODO);
                tmp.setCategorie(cat);
                System.out.println("ITEM : " + title + " " + txt + " " + date + " " + status);
                list.add(tmp);
                f = 0;
            }
            eventType = xpp.next();
        }
        return list;
    }

    public ArrayList<Categorie> dataToCat(String data) throws ParseException, XmlPullParserException, IOException {
        ArrayList<Categorie> list = new ArrayList<Categorie>();
        Categorie tmp;
        String name = "";
        int color = 0;
        int f = 0;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(data));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.TEXT) {
                if (f == 0)
                    name = xpp.getText();
                else if (f == 1) {
                    color = Integer.parseInt(xpp.getText());
                }
                f++;
            }
            if (f == 2) {
                tmp = new Categorie(name, color);
                System.out.println("CAT : " + name + " " + color);
                list.add(tmp);
                f = 0;
            }
            eventType = xpp.next();
        }
        return list;
    }

    public void getData() throws ParseException, IOException, XmlPullParserException {
        int c;
        String temp = "";
        FileInputStream fin = null;
        try {
            fin = openFileInput("tasksSave");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fin != null) {
            try {
                while ((c = fin.read()) != -1) {
                    temp = temp + Character.toString((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(temp);
            try {
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            items = dataToItems(temp);
        }
    }

    public void getCatData() throws ParseException, IOException, XmlPullParserException {
        int c;
        String temp = "";
        FileInputStream fin = null;
        try {
            fin = openFileInput("categorySave");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fin != null) {
            try {
                while ((c = fin.read()) != -1) {
                    temp = temp + Character.toString((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(temp);
            try {
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cat = dataToCat(temp);
        }
    }

    public void saveData() {
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("tasksSave", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ItemAdapter a = (ItemAdapter) mListView.getAdapter();
        Item tmp;

        for (int i = 0; i < items.size(); i++) {
            tmp = items.get(i);
            try {
                fOut.write(("<t>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(tmp.getTitle().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</t>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("<d>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write((tmp.getDate() + tmp.getTime()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</d>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("<s>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write((tmp.getStatus().toString()).getBytes());
                System.out.println("SAVING HERE " + tmp.getStatus().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</s>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("<x>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write((tmp.getText()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</x>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("<c>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write((tmp.getCategorie()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</c>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveCategory() {
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("categorySave", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Categorie tmp;
        System.out.println("SAVING CAT -------> " + cat.size());
        for (int i = 0; i < cat.size(); i++) {
            tmp = cat.get(i);
            try {
                fOut.write(("<n>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(tmp.getName().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</n>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("<c>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(String.valueOf(tmp.getColor()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.write(("</c>").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void settings(View V) {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void add(View v) {
        Intent intentMain = new Intent(MainActivity.this, AddItem.class);
        startActivityForResult(intentMain, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String txt = data.getStringExtra("txt");
                String date = data.getStringExtra("date");
                String delete = data.getStringExtra("delete");
                String category = data.getStringExtra("categorie");
                SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy k:m");
                Date d = null;
                try {
                    d = newDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (data.getStringExtra("edit").equals("true")) {
                    int position = Integer.parseInt(data.getStringExtra("position"));
                    try {
                        modifyItem(position, title, txt, d, delete, category);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Item newItem = new Item(title, txt, d);
                    newItem.setCategorie(category);
                    try {
                        addToList(newItem);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //here goes nothing
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                /*String name = data.getStringExtra("name");
                String sColor = data.getStringExtra("color");
                int color = Integer.parseInt(sColor);
                cat.add(new Categorie(name, color));*/
                saveCategory();
                affListCorresponding();
                ((checkAdapter) checkListView.getAdapter()).notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                saveCategory();
                affListCorresponding();
                ((checkAdapter) checkListView.getAdapter()).notifyDataSetChanged();
                //here goes nothing
            }
        }
    }

    public void checkDate() {
        int i = 0;
        Date d;

        d = new Date();
        nb_tasks.setText(String.valueOf(items.size()) + " Tasks");
        while (i < items.size()) {
            if (!(items.get(i).getRealDate().after(d))) {
                items.get(i).setPassed(true);
                items.get(i).setDateColor("#FF0000");
            } else {
                items.get(i).setPassed(false);
                items.get(i).setDateColor("#121212");
            }
            i++;
        }
    }

    public void addToList(Item item) throws ParseException {
        items.add(item);
        checkDate();
        saveData();
        Date f = new Date();
        int delay = (int) (item.getRealDate().getTime() - f.getTime());
        if (delay > 0)
            scheduleNotification(getNotification(item.getTitle()), delay);
        affListCorresponding();
    }

    public void modifyItem(int position, String title, String txt, Date d, String delete, String cat) throws ParseException {
        Item item = items.get(position);
        if (delete.equals("false")) {
            item.setTitle(title);
            item.setText(txt);
            item.setDueDate(d);
            item.setCategorie(cat);
        } else
            items.remove(item);
        checkDate();
        saveData();
        Date f = new Date();
        int delay = (int) (d.getTime() - f.getTime());
        if (delay > 0)
            scheduleNotification(getNotification(title), delay);
        affListCorresponding();
    }

    public void changeCats()
    {
        affListCorresponding();
    }

    public boolean showCatForItem(Item item)
    {
        int i = 0;
        while (i < cat.size()) {
            if (cat.get(i).getName().equals(item.getCategorie())) {
                return cat.get(i).getShow();
            }
            i++;
        }
        return false;
    }
    public void affListCorresponding() {
        int nb_items = items.size();
        boolean t, p;
        int i = 0;
        tmp.clear();
        while (i < nb_items) {
            t = false;
            p = false;
            if (aff_done && items.get(i).getStatus() == Item.Status.DONE)
                t = true;
            if (aff_todo && items.get(i).getStatus() == Item.Status.TODO)
                t = true;
            if ((aff_passed && items.get(i).getPassed()))
                p = true;
            if ((aff_ondate && !items.get(i).getPassed()))
                p = true;
            if (t && p && showCatForItem(items.get(i)))
                tmp.add(items.get(i));
            System.out.println(showCatForItem(items.get(i)));
            i++;
        }
        ItemAdapter adapter = new ItemAdapter(MainActivity.this, tmp);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void todoClick(View v) {
        final int position = mListView.getPositionForView((View) v.getParent());
        SwipeLayout s = (SwipeLayout) mListView.getChildAt(position);
        Item a = items.get(position);
        a.setStatus(Item.Status.TODO);
        /*ItemAdapter b = (ItemAdapter) mListView.getAdapter();
        b.notifyDataSetChanged();*/
        affListCorresponding();
        saveData();
        s.close(true);
    }

    public void catCheck(View v)
    {
        final int position = checkListView.getPositionForView((View) v.getParent());
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked())
            cat.get(position).setShow(true);
        else
            cat.get(position).setShow(false);
        affListCorresponding();
    }

    public void doneClick(View v) {
        final int position = mListView.getPositionForView((View) v.getParent());
        SwipeLayout s = (SwipeLayout) mListView.getChildAt(position);
        Item a = items.get(position);
        a.setStatus(Item.Status.DONE);
        s.close(true);
        ItemAdapter b = (ItemAdapter) mListView.getAdapter();
        affListCorresponding();
        b.notifyDataSetChanged();
        saveData();
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        //notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id);
        id++;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("To Do");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_notif);
        //builder.setColor(Color.parseColor("#102372"));
        affListCorresponding();
        return builder.build();
    }
    public static ArrayList<Categorie> getCatA()
    {
        ArrayList<Categorie> tmp = new ArrayList<Categorie>();
        int i = 0;
        while (i < cat.size())
            tmp.add(cat.get(i++));
        return tmp;
    }

    public void closeMenu(View v)
    {
        DrawerLayout d = ((DrawerLayout) findViewById(R.id.drawer_layout));
        d.closeDrawers();
    }
    public static ArrayList<Categorie> getCat()
    {
        return (getCatA());
    }

    public void addCategorie(View v)
    {
        Intent intentMain = new Intent(MainActivity.this, addCategory.class);
        startActivityForResult(intentMain, 2);
        //startActivity(intentMain);
        System.out.println("MDRRRRRR");
    }
}
