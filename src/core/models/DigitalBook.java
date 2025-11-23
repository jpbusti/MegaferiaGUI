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
public class DigitalBook extends Book {
    private String hyperlink;

    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hyperlink = null;
    }
    
    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, String hyperlink) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hyperlink = hyperlink;
    }

    @Override
    public String getSpecificInfo() {
        return (hyperlink != null && !hyperlink.isEmpty()) ? "URL: " + hyperlink : "Sin URL";
    }

    public boolean hasHyperlink() { return hyperlink != null; }
    public String getHyperlink() { return hyperlink; }
}
