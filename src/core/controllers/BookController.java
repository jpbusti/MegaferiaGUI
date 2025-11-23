package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Audiobook;
import core.models.Author;
import core.models.Book;
import core.models.DigitalBook;
import core.models.Narrator;
import core.models.PrintedBook;
import core.models.Publisher;
import core.models.storage.IStorage;
import core.models.storage.Storage;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

public class BookController {
    
    private IStorage storage;
    private PropertyChangeSupport support;

    public BookController() {
        this.storage = Storage.getInstance();
        this.support = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    private Response validateCommonData(String title, String isbn, ArrayList<String> authorIds, String publisherNit, String priceStr) {
        if (title == null || title.trim().isEmpty() || isbn == null || isbn.trim().isEmpty()) {
            return new Response("El título y el ISBN son obligatorios.", Status.BAD_REQUEST);
        }
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) return new Response("El precio debe ser mayor a 0.", Status.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new Response("El precio debe ser un número válido.", Status.BAD_REQUEST);
        }
        if (!isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            return new Response("El ISBN debe tener el formato XXX-X-XX-XXXXXX-X", Status.BAD_REQUEST);
        }
        
        for (Book b : storage.getBooks()) {
            if (b.getIsbn().equals(isbn)) {
                return new Response("Ya existe un libro registrado con ese ISBN.", Status.BAD_REQUEST);
            }
        }
        if (authorIds == null || authorIds.isEmpty()) {
            return new Response("Debe seleccionar al menos un autor.", Status.BAD_REQUEST);
        }
        if (getPublisherByNit(publisherNit) == null) {
            return new Response("La editorial seleccionada no es válida.", Status.BAD_REQUEST);
        }
        return new Response("OK", Status.OK);
    }

    public Response createPrintedBook(String title, String isbn, ArrayList<String> authorIds, String publisherNit, String priceStr, String genre, String format, String pagesStr, String copiesStr) {
        Response common = validateCommonData(title, isbn, authorIds, publisherNit, priceStr);
        if (!common.getMessage().equals("OK")) return common;

        try {
            int pages = Integer.parseInt(pagesStr);
            int copies = Integer.parseInt(copiesStr);
            double price = Double.parseDouble(priceStr);
            
            if (pages <= 0 || copies <= 0) return new Response("Las páginas y ejemplares deben ser mayores a 0.", Status.BAD_REQUEST);
            
            ArrayList<Author> authors = getAuthorsByIds(authorIds);
            Publisher publisher = getPublisherByNit(publisherNit);
            
            PrintedBook book = new PrintedBook(title, authors, isbn, genre, format, price, publisher, pages, copies);
            storage.getBooks().add(book);
            
            support.firePropertyChange("NewBook", null, book);

            return new Response("Libro Impreso creado exitosamente.", Status.CREATED);
        } catch (NumberFormatException e) {
            return new Response("El número de páginas y ejemplares deben ser enteros.", Status.BAD_REQUEST);
        }
    }

    public Response createDigitalBook(String title, String isbn, ArrayList<String> authorIds, String publisherNit, String priceStr, String genre, String format, String url) {
        Response common = validateCommonData(title, isbn, authorIds, publisherNit, priceStr);
        if (!common.getMessage().equals("OK")) return common;

        double price = Double.parseDouble(priceStr);
        ArrayList<Author> authors = getAuthorsByIds(authorIds);
        Publisher publisher = getPublisherByNit(publisherNit);

        DigitalBook book;
        if (url == null || url.trim().isEmpty()) {
             book = new DigitalBook(title, authors, isbn, genre, format, price, publisher);
        } else {
             book = new DigitalBook(title, authors, isbn, genre, format, price, publisher, url);
        }
        storage.getBooks().add(book);
        
        support.firePropertyChange("NewBook", null, book);

        return new Response("Libro Digital creado exitosamente.", Status.CREATED);
    }

    public Response createAudioBook(String title, String isbn, ArrayList<String> authorIds, String publisherNit, String priceStr, String genre, String format, String durationStr, String narratorIdStr) {
        Response common = validateCommonData(title, isbn, authorIds, publisherNit, priceStr);
        if (!common.getMessage().equals("OK")) return common;

        try {
            int duration = Integer.parseInt(durationStr);
            long narratorId = Long.parseLong(narratorIdStr);
            double price = Double.parseDouble(priceStr);
            
            if (duration <= 0) return new Response("La duración debe ser mayor a 0.", Status.BAD_REQUEST);

            ArrayList<Author> authors = getAuthorsByIds(authorIds);
            Publisher publisher = getPublisherByNit(publisherNit);
            Narrator narrator = getNarratorById(narratorId);
            
            if (narrator == null) return new Response("El narrador seleccionado no existe.", Status.BAD_REQUEST);

            Audiobook book = new Audiobook(title, authors, isbn, genre, format, price, publisher, duration, narrator);
            storage.getBooks().add(book);
            
            support.firePropertyChange("NewBook", null, book);

            return new Response("Audiolibro creado exitosamente.", Status.CREATED);
        } catch (NumberFormatException e) {
            return new Response("La duración debe ser un número entero.", Status.BAD_REQUEST);
        }
    }
    
    private ArrayList<Author> getAuthorsByIds(ArrayList<String> ids) {
        ArrayList<Author> found = new ArrayList<>();
        for (String idStr : ids) {
            try {
                long id = Long.parseLong(idStr);
                for (Author a : storage.getAuthors()) {
                    if (a.getId() == id) found.add(a);
                }
            } catch (NumberFormatException e) { }
        }
        return found;
    }

    private Publisher getPublisherByNit(String nit) {
        for (Publisher p : storage.getPublishers()) {
            if (p.getNit().equals(nit)) return p;
        }
        return null;
    }
    
    private Narrator getNarratorById(long id) {
        for (Narrator n : storage.getNarrators()) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    public ArrayList<Book> getBooksByAuthor(long authorId){
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : storage.getBooks()) {
            for (Author a : b.getAuthors()) {
                if (a.getId() == authorId) {
                    result.add(b.clone());
                    break;
                }
            }
        }
        Collections.sort(result, (b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        return result;
    }

    public ArrayList<Book> getBooksByFormat(String format) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : storage.getBooks()) {
            if (b.getFormat() != null && b.getFormat().equals(format)) {
                result.add(b.clone());
            }
        }
        Collections.sort(result, (b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        return result;
    }

    public ArrayList<Book> getBooks() { 
        ArrayList<Book> copies = new ArrayList<>();
        for (Book b : storage.getBooks()) {
            copies.add(b.clone());
        }
        Collections.sort(copies, (b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()));
        return copies;
    }
}