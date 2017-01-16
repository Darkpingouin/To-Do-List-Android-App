package com.darkpingouin.todolist;

/**
 * Created by Oreo on 16/01/2017.
 */

public class Categorie {
    private String name;
    private int color;

    public Categorie(String n, int c)
    {
        this.name = n;
        this.color = c;
    }

    public String getName()
    {
        return (this.name);
    }

    public int getColor()
    {
        return (this.color);
    }

    public void setName(String n)
    {
        this.name = n;
    }

    public void setColor(int c)
    {
        this.color = c;
    }
}
