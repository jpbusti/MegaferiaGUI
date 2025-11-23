/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Book;
import java.util.ArrayList;

public interface IBookRepository {
    ArrayList<Book> getBooks();
    void addBook(Book book);
}
