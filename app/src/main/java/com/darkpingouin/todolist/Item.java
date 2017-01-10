package com.darkpingouin.todolist;

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

    public Date getDueDate()
    {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }
}
