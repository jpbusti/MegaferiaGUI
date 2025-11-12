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
public class Narrator extends Person {
    
    private ArrayList<Audiobook> books;

    public Narrator(String name, long id) {
        super(name, id);
        this.books = new ArrayList<>();
    }
    
    public void addBook(Audiobook book) {
        this.books.add(book);
    }
    
}
