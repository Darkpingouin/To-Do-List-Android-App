package com.darkpingouin.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oreo on 10/01/2017.
 */


public class Item {
    private String title;
    private String text;
    private boolean passed;
    private Date dueDate;
    private Status status;
    private String dateColor;
    private String categorie;
    public enum Status {TODO, DONE}

    /**
     * Constructeur
     * @param title
     * @param text
     * @param dueDate
     */
    public Item(String title, String text, Date dueDate)
    {
        this.title = title;
        this.text = text;
        this.passed = false;
        this.dueDate = dueDate;
        this.status = Status.TODO;
        this.dateColor = "#AFAFAF";
        this.categorie = "none";
    }

    /**
     * Set la couleur de la date
     * @param c couleur
     */
    public void setDateColor(String c)
    {
        this.dateColor = c;
    }

    /**
     * Renvoie la couleur de la date
     * @return
     */
    public String getDateColor()
    {
        return this.dateColor;
    }

    /**
     * Renvoie le titre de la task
     * @return le titre
     */
    public String getTitle()
    {
        return (this.title);
    }

    /**
     * Set le titre de la task
     * @param title le titre
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Renvoies la description de la task
     * @return
     */
    public String getText()
    {
        return (this.text);
    }

    /**
     * Set la description de la tache
     * @param text
     */
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

    /**
     * Renvoies la date au format EE d MMM yyyy
     * @return
     */
    public String getDate()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoie l'heure au format HH:mm
     * @return
     */
    public String getTime()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoies la date au format dd/MM
     * @return
     */
    public String getMonth()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoies l'année au format yyyy
     * @return
     */
    public String getYear()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoie la Date
     * @return
     */
    public Date getRealDate()
    {
        return this.dueDate;
    }

    /**
     * Set la date
     * @param dueDate
     */

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * Renvoie le status de la tache
     * @return
     */
    public Status getStatus()
    {
        return (this.status);
    }

    /**
     * Renvoie si la tache est passée dans le temps
     * @return
     */
    public boolean getPassed() {
        return this.passed;
    }

    /**
     * Set si la tache est passée dans le temps
     * @param b
     */
    public void setPassed(boolean b)
    {
        this.passed = b;
    }

    /**
     * Set le status de la tache
     * @param status
     */
    public void setStatus(Status status)
    {
        this.status = status;
    }

    /**
     * set la catégorie de la tache
     * @param cat
     */
    public void setCategorie(String cat)
    {
        this.categorie = cat;
    }

    /**
     * Renvoie le nom de la catégorie de la tache
     * @return
     */
    public String getCategorie()
    {
        return this.categorie;
    }
}
