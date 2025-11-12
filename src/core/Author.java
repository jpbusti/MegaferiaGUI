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
public class Author extends Person {
    
    private ArrayList<Book> books;

    public Author(String name, long id) {
        super(name, id);
        this.books = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }
    
}
