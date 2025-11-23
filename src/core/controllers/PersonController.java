package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Author;
import core.models.Manager;
import core.models.Narrator;
import core.models.Person;
import core.models.storage.IPersonRepository;
import java.util.ArrayList;
import java.util.Collections;

public class PersonController {

    private final IPersonRepository personRepo;

    public PersonController(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    public Response createAuthor(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) return validation;
        
        long id = Long.parseLong(idStr);
        personRepo.addAuthor(new Author(id, name, lastname));
        return new Response("Autor creado exitosamente.", Status.CREATED);
    }

    public Response createManager(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) return validation;
        
        long id = Long.parseLong(idStr);
        personRepo.addManager(new Manager(id, name, lastname));
        return new Response("Gerente creado exitosamente.", Status.CREATED);
    }

    public Response createNarrator(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) return validation;
        
        long id = Long.parseLong(idStr);
        personRepo.addNarrator(new Narrator(id, name, lastname));
        return new Response("Narrador creado exitosamente.", Status.CREATED);
    }

    public ArrayList<Author> getAuthors() {
        ArrayList<Author> copies = new ArrayList<>();
        for (Author a : personRepo.getAuthors()) copies.add(a.clone());
        Collections.sort(copies, (p1, p2) -> Long.compare(p1.getId(), p2.getId()));
        return copies;
    }

    public ArrayList<Manager> getManagers() {
        ArrayList<Manager> copies = new ArrayList<>();
        for (Manager m : personRepo.getManagers()) copies.add(m.clone());
        Collections.sort(copies, (p1, p2) -> Long.compare(p1.getId(), p2.getId()));
        return copies;
    }

    public ArrayList<Narrator> getNarrators() {
        ArrayList<Narrator> copies = new ArrayList<>();
        for (Narrator n : personRepo.getNarrators()) copies.add(n.clone());
        Collections.sort(copies, (p1, p2) -> Long.compare(p1.getId(), p2.getId()));
        return copies;
    }
    
    public ArrayList<Author> getAuthorsWithMostBooksInDifferentPublishers() {
        ArrayList<Author> allAuthors = personRepo.getAuthors();
        ArrayList<Author> topAuthors = new ArrayList<>();
        if (allAuthors.isEmpty()) return topAuthors;

        int maxPublishers = -1;
        for (Author a : allAuthors) {
            int qty = a.getPublisherQuantity(); 
            if (qty > maxPublishers) maxPublishers = qty;
        }
        if (maxPublishers > 0) {
            for (Author a : allAuthors) {
                if (a.getPublisherQuantity() == maxPublishers) topAuthors.add(a.clone());
            }
        }
        Collections.sort(topAuthors, (p1, p2) -> Long.compare(p1.getId(), p2.getId()));
        return topAuthors;
    }

    private Response validateCommonData(String idStr, String name, String lastname) {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            return new Response("El ID debe ser numérico.", Status.BAD_REQUEST);
        }
        if (name == null || name.trim().isEmpty() || lastname == null || lastname.trim().isEmpty()) {
        return new Response("El nombre y apellido son obligatorios.", Status.BAD_REQUEST);
    }
    
   
    String regex = "[^\\p{L} \\-']";
    
    if (name.trim().matches(".*" + regex + ".*")) {
        return new Response("El nombre solo debe contener letras.", Status.BAD_REQUEST);
    }
    
    if (lastname.trim().matches(".*" + regex + ".*")) {
        return new Response("El apellido solo debe contener letras.", Status.BAD_REQUEST);
    }
        if (id < 0) return new Response("El ID debe ser positivo.", Status.BAD_REQUEST);
        if (idStr.length() > 15) return new Response("El ID no debe superar los 15 dígitos.", Status.BAD_REQUEST);
        if (name == null || name.trim().isEmpty() || lastname == null || lastname.trim().isEmpty()) {
            return new Response("El nombre y apellido son obligatorios.", Status.BAD_REQUEST);
        }
        
        if (personExists(id, personRepo.getAuthors()) || 
            personExists(id, personRepo.getManagers()) || 
            personExists(id, personRepo.getNarrators())) {
            return new Response("Ya existe una persona registrada con ese ID.", Status.BAD_REQUEST);
        }
        return new Response("OK", Status.OK);
    }

    private <T extends Person> boolean personExists(long id, ArrayList<T> people) {
        for (Person p : people) {
            if (p.getId() == id) return true;
        }
        return false;
    }
}