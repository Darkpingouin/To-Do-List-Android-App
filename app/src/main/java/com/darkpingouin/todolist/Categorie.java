package com.darkpingouin.todolist;

/**
 * Classe catégorie
 */

public class Categorie {
    private String name;
    private int color;
    private boolean show;

    /**
     * Constructeur
     * @param n nom
     * @param c couleur
     */
    public Categorie(String n, int c)
    {
        this.name = n;
        this.color = c;
        show = true;
    }

    /**
     * Renvoie le nom de la catégorie
     * @return nom de la catégorie
     */
    public String getName()
    {
        return (this.name);
    }

    /**
     * Renvoies la couleur de la catégorie
     * @return couleur de la catégorie
     */
    public int getColor()
    {
        return (this.color);
    }

    /**
     * Set le nom de la catégorie
     * @param n nom
     */
    public void setName(String n)
    {
        this.name = n;
    }

    /**
     * Set la couleur
     * @param c couleur
     */
    public void setColor(int c)
    {
        this.color = c;
    }

    /**
     * Pemret de savoir si la catégorie doit être affichée
     * @return boolean affichage de la catgorie
     */
    public boolean getShow() {
        return this.show;
    }

    /**
     * Set la catégorie en visible ou invible
     * @param show1 true ou false
     */
    public void setShow(boolean show1) {
        this.show = show1;
    }
}
