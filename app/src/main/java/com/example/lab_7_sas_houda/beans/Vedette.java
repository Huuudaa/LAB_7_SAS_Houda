package com.example.lab_7_sas_houda.beans;

public class Vedette {
    private int id;
    private String prenom;
    private String photo;
    private float note;
    private static int compteur = 0;

    public Vedette(String prenom, String photo, float note) {
        this.id = ++compteur;
        this.prenom = prenom;
        this.photo = photo;
        this.note = note;
    }

    public int getId() { return id; }
    public String getPrenom() { return prenom; }
    public String getPhoto() { return photo; }
    public float getNote() { return note; }

    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setNote(float note) { this.note = note; }
}