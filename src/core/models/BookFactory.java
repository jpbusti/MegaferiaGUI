/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Juan
 */
public class BookFactory {
    
    public static Book createBook(String type, String title, ArrayList<Author> authors, String isbn, Publisher publisher, double price, String genre, String format, Map<String, String> params, Narrator narrator) {
        switch (type) {
            case "Impreso":
                int pages = Integer.parseInt(params.get("pages"));
                int copies = Integer.parseInt(params.get("copies"));
                return new PrintedBook(title, authors, isbn, genre, format, price, publisher, pages, copies);
                
            case "Digital":
                String url = params.getOrDefault("url", null);
                return new DigitalBook(title, authors, isbn, genre, format, price, publisher, url);
                
            case "AudioLibro":
                int duration = Integer.parseInt(params.get("duration"));
                if (narrator == null) throw new IllegalArgumentException("El narrador es obligatorio para audiolibros.");
                return new Audiobook(title, authors, isbn, genre, format, price, publisher, duration, narrator);
                
            default:
                throw new IllegalArgumentException("Tipo de libro no soportado: " + type);
        }
    }
}