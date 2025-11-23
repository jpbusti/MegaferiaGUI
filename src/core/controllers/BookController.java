package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Author;
import core.models.Book;
import core.models.BookFactory;
import core.models.Narrator;
import core.models.Publisher;
import core.models.storage.IBookRepository;
import core.models.storage.IPersonRepository;
import core.models.storage.IStandRepository;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class BookController {
    
    private final IBookRepository bookRepo;
    private final IPersonRepository personRepo;
    private final IStandRepository standRepo; 
    private final PropertyChangeSupport support;

    public BookController(IBookRepository bookRepo, IPersonRepository personRepo, IStandRepository standRepo) {
        this.bookRepo = bookRepo;
        this.personRepo = personRepo;
        this.standRepo = standRepo;
        this.support = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public Response createBook(String type, String title, String isbn, ArrayList<String> authorIds, 
                               String publisherNit, String priceStr, String genre, String format, 
                               Map<String, String> extraParams, String narratorIdStr) {
        
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
        
        for (Book b : bookRepo.getBooks()) {
            if (b.getIsbn().equals(isbn)) {
                return new Response("Ya existe un libro registrado con ese ISBN.", Status.BAD_REQUEST);
            }
        }
        
        if (authorIds == null || authorIds.isEmpty()) {
            return new Response("Debe seleccionar al menos un autor.", Status.BAD_REQUEST);
        }

        ArrayList<Author> authors = getAuthorsByIds(authorIds);
        Publisher publisher = getPublisherByNit(publisherNit);
        
        if (publisher == null) {
            return new Response("La editorial seleccionada no es válida.", Status.BAD_REQUEST);
        }

        Narrator narrator = null;
        if (narratorIdStr != null && !narratorIdStr.isEmpty()) {
            try {
                long nid = Long.parseLong(narratorIdStr);
                narrator = getNarratorById(nid);
            } catch(NumberFormatException e) {}
        }

        try {
            Book newBook = BookFactory.createBook(type, title, authors, isbn, publisher, price, genre, format, extraParams, narrator);
            
            bookRepo.addBook(newBook);
            
            for (Author a : authors) {
                a.addBook(newBook);
            }
            
            support.firePropertyChange("NewBook", null, newBook);
            return new Response("Libro (" + type + ") creado exitosamente.", Status.CREATED);
            
        } catch (NumberFormatException e) {
            return new Response("Error en datos numéricos: " + e.getMessage(), Status.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage(), Status.BAD_REQUEST);
        }
    }
    
    private ArrayList<Author> getAuthorsByIds(ArrayList<String> ids) {
        ArrayList<Author> found = new ArrayList<>();
        for (String idStr : ids) {
            try {
                long id = Long.parseLong(idStr);
                for (Author a : personRepo.getAuthors()) {
                    if (a.getId() == id) found.add(a);
                }
            } catch (NumberFormatException e) { }
        }
        return found;
    }

    private Publisher getPublisherByNit(String nit) {
        for (Publisher p : standRepo.getPublishers()) {
            if (p.getNit().equals(nit)) return p;
        }
        return null;
    }
    
    private Narrator getNarratorById(long id) {
        for (Narrator n : personRepo.getNarrators()) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    public ArrayList<Book> getBooks() { 
        ArrayList<Book> copies = new ArrayList<>();
        for (Book b : bookRepo.getBooks()) {
            copies.add(b.clone());
        }
        Collections.sort(copies, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getIsbn().compareTo(b2.getIsbn());
            }
        });
        return copies;
    }
    
    public ArrayList<Book> getBooksByAuthor(long authorId){
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : bookRepo.getBooks()) {
            for (Author a : b.getAuthors()) {
                if (a.getId() == authorId) {
                    result.add(b.clone());
                    break;
                }
            }
        }
        Collections.sort(result, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getIsbn().compareTo(b2.getIsbn());
            }
        });
        return result;
    }

    public ArrayList<Book> getBooksByFormat(String format) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : bookRepo.getBooks()) {
            if (b.getFormat() != null && b.getFormat().equals(format)) {
                result.add(b.clone());
            }
        }
        Collections.sort(result, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getIsbn().compareTo(b2.getIsbn());
            }
        });
        return result;
    }
}