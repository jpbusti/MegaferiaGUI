package core.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Audiobook extends Book {

    private int duration;
    private Narrator narrator;

    public Audiobook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, int duration, Narrator narrator) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.duration = duration;
        this.narrator = narrator;
    }

    @Override
    public String getSpecificInfo() {
        String narradorName;

        if (narrator != null) {
            narradorName = narrator.getFullname();
        } else {
            narradorName = "N/A";
        }

        return "Duración: " + duration + " min, Narrador: " + narradorName;
    }

    public int getDuration() {
        return duration;
    }

    public Narrator getNarrador() {
        return narrator;
    }
}
