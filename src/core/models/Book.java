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
public abstract class Book implements Cloneable {
    protected String title;
    protected ArrayList<Author> authors;
    protected String isbn;
    protected String genre;
    protected String format;
    protected double value;
    protected Publisher publisher;

    public Book(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.genre = genre;
        this.format = format;
        this.value = value;
        this.publisher = publisher;
    }

    public abstract String getSpecificInfo();

    public String getTitle() { return title; }
    public ArrayList<Author> getAuthors() { return authors; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public String getFormat() { return format; }
    public double getValue() { return value; }
    public Publisher getPublisher() { return publisher; }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
