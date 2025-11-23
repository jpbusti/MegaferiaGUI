package core.models.storage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import core.models.Author;
import core.models.Book;
import core.models.Manager;
import core.models.Narrator;
import core.models.Publisher;
import core.models.Stand;
import java.util.ArrayList;

public class Storage implements IStorage {
    
    private static Storage instance;
    
    private ArrayList<Stand> stands;
    private ArrayList<Author> authors;
    private ArrayList<Manager> managers;
    private ArrayList<Narrator> narrators;
    private ArrayList<Publisher> publishers;
    private ArrayList<Book> books;

    private Storage() {
        this.stands = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.narrators = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.books = new ArrayList<>();
    }
    
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    @Override public ArrayList<Stand> getStands() { return stands; }
    @Override public ArrayList<Author> getAuthors() { return authors; }
    @Override public ArrayList<Manager> getManagers() { return managers; }
    @Override public ArrayList<Narrator> getNarrators() { return narrators; }
    @Override public ArrayList<Publisher> getPublishers() { return publishers; }
    @Override public ArrayList<Book> getBooks() { return books; }
    
}