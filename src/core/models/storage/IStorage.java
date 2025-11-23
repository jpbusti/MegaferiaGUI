/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

/**
 *
 * @author Juan
 */
import core.models.Author;
import core.models.Book;
import core.models.Manager;
import core.models.Narrator;
import core.models.Publisher;
import core.models.Stand;
import java.util.ArrayList;

public interface IStorage {
    ArrayList<Stand> getStands();
    ArrayList<Author> getAuthors();
    ArrayList<Manager> getManagers();
    ArrayList<Narrator> getNarrators();
    ArrayList<Publisher> getPublishers();
    ArrayList<Book> getBooks();
}
