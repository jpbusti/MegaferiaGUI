/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class DigitalBook extends Book {
    
    private boolean hasHyperlink;
    private ArrayList<String> hyperlinks;

    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hasHyperlink = false;
        this.hyperlinks = null;
    }
    
    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, ArrayList<String> hyperlinks) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hasHyperlink = true;
        this.hyperlinks = hyperlinks;
    }
    
}
