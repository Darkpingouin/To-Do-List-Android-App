package com.darkpingouin.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oreo on 10/01/2017.
 */


public class Item {
    private String title;
    private String text;
    private Date dueDate;
    private Status status;
    private String dateColor;
    public enum Status {TODO, DONE}
    public Item(String title, String text, Date dueDate)
    {
        this.title = title;
        this.text = text;
        this.dueDate = dueDate;
        this.status = Status.TODO;
        this.dateColor = "#D3D3D3";
    }

    public void setDateColor(String c)
    {
        this.dateColor = c;
    }

    public String getDateColor()
    {
        return this.dateColor;
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
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy\nHH:mm");
        String MySDate = "due till " + newDateFormat.format(this.dueDate);
        return MySDate;
    }
    public String getDate()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    public String getTime()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    public Date getRealDate()
    {
        return this.dueDate;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Status getStatus()
    {
        return (this.status);
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
}
