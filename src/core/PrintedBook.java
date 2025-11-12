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
public class PrintedBook extends Book {
    
    private int pages;
    private int numCopies;

    public PrintedBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, int pages, int numCopies) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.pages = pages;
        this.numCopies = numCopies;
    }
    
}
