package com.darkpingouin.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe Item
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
     * @param title titre
     * @param text descritpion
     * @param dueDate date
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
     * @return la couleur de la date
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
     * @return la description
     */
    public String getText()
    {
        return (this.text);
    }

    /**
     * Set la description de la tache
     * @param text la description
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
     * @return la date au format EE d MMM yyyy
     */
    public String getDate()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoie l'heure au format HH:mm
     * @return l'heure au format HH:mm
     */
    public String getTime()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoies la date au format dd/MM
     * @return la date au format dd/MM
     */
    public String getMonth()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoies l'année au format yyyy
     * @return l'année au format yyyy
     */
    public String getYear()
    {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy");
        String MySDate = newDateFormat.format(this.dueDate);
        return MySDate;
    }

    /**
     * Renvoie la Date
     * @return la Date
     */
    public Date getRealDate()
    {
        return this.dueDate;
    }

    /**
     * Set la date
     * @param dueDate la date
     */

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * Renvoie le status de la tache
     * @return ToDo ou Done
     */
    public Status getStatus()
    {
        return (this.status);
    }

    /**
     * Renvoie si la tache est passée dans le temps
     * @return true ou false
     */
    public boolean getPassed() {
        return this.passed;
    }

    /**
     * Set si la tache est passée dans le temps
     * @param b true ou false
     */
    public void setPassed(boolean b)
    {
        this.passed = b;
    }

    /**
     * Set le status de la tache
     * @param status ToDo ou Done
     */
    public void setStatus(Status status)
    {
        this.status = status;
    }

    /**
     * set la catégorie de la tache
     * @param cat la catégorie
     */
    public void setCategorie(String cat)
    {
        this.categorie = cat;
    }

    /**
     * Renvoie le nom de la catégorie de la tache
     * @return le nom de la catégorie
     */
    public String getCategorie()
    {
        return this.categorie;
    }
}
