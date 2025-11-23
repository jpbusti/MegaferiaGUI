package core.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import core.models.Book;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Author extends Person {
    private List<Book> books;
    
    public List<Book> getBooks() {
        return books; // O new ArrayList<>(books) para encapsulamiento
    }


    public Author(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
        this.books = new ArrayList<>();
    }
    public int calculatePublisherQuantity() {
        if (books == null || books.isEmpty()) {
            return 0;
        }
        // Usamos un HashSet para asegurar que las editoriales sean únicas.
        HashSet<String> uniquePublishers = new HashSet<>();
        
        for (Book book : this.books) {
            // Asume que Book tiene un método getPublisher() que devuelve un Publisher
            // y Publisher tiene un método getNit() que devuelve el identificador único.
            if (book.getPublisher() != null) {
                uniquePublishers.add(book.getPublisher().getNit());
            }
        }
        return uniquePublishers.size();
    }
    
    
    public int getBookQuantity() {
        return this.books.size();
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }
    
    public int getPublisherQuantity() {
        ArrayList<Publisher> publishers = new ArrayList<>();
        for (Book book : this.books) {
            if (!publishers.contains(book.getPublisher())) {
                publishers.add(book.getPublisher());
            }
        }
        return publishers.size();
    }
    
}
