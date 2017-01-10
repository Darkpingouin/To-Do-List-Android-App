package com.darkpingouin.todolist;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oreo on 10/01/2017.
 */

public class Item {
    private String title;
    private String text;
    private Date dueDate;

    public Item(String title, String text, Date dueDate)
    {
        this.title = title;
        this.text = text;
        this.dueDate = dueDate;
    }

    public String getTitle()
    {
        return (this.title);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return (this.text);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getDueDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dueDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String sday = String.format("%02d", day);
        String smonth = String.format("%02d", month);
        return ("due til " + sday + "/" + smonth + "/" + year);
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }
}
