package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Author;
import core.models.Manager;
import core.models.Narrator;
import core.models.Person;
import core.models.storage.Storage;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Juan
 */
public class PersonController {

    private Storage storage;

    public PersonController() {
        this.storage = Storage.getInstance();
    }

    public Response createAuthor(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) {
            return validation;
        }

        long id = Long.parseLong(idStr);
        Author newAuthor = new Author(id, name, lastname);
        storage.getAuthors().add(newAuthor);
        return new Response("Autor creado exitosamente.", Status.CREATED);
    }

    public Response createManager(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) {
            return validation;
        }

        long id = Long.parseLong(idStr);
        Manager newManager = new Manager(id, name, lastname);
        storage.getManagers().add(newManager);
        return new Response("Gerente creado exitosamente.", Status.CREATED);
    }

    public Response createNarrator(String idStr, String name, String lastname) {
        Response validation = validateCommonData(idStr, name, lastname);
        if (!validation.getMessage().equals("OK")) {
            return validation;
        }

        long id = Long.parseLong(idStr);
        Narrator newNarrator = new Narrator(id, name, lastname);
        storage.getNarrators().add(newNarrator);
        return new Response("Narrador creado exitosamente.", Status.CREATED);
    }

    public ArrayList<Author> getAuthors() {
        return storage.getAuthors();
    }

    public ArrayList<Manager> getManagers() {
        return storage.getManagers();

    }

    public ArrayList<Narrator> getNarrators() {
        return storage.getNarrators();
    }

    private Response validateCommonData(String idStr, String name, String lastname) {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            return new Response("El ID debe ser numérico.", Status.BAD_REQUEST);
        }

        if (id < 0) {
            return new Response("El ID debe ser positivo.", Status.BAD_REQUEST);
        }
        if (idStr.length() > 15) {
            return new Response("El ID no debe superar los 15 dígitos.", Status.BAD_REQUEST);
        }
        if (name == null || name.trim().isEmpty() || lastname == null || lastname.trim().isEmpty()) {
            return new Response("El nombre y apellido son obligatorios.", Status.BAD_REQUEST);
        }

        if (personExists(id, storage.getAuthors())|| personExists(id, storage.getManagers()) || personExists(id, storage.getNarrators())) {
            return new Response("Ya existe una persona registrada con ese ID.", Status.BAD_REQUEST);
        }

        return new Response("OK", Status.OK);
    }

    private <T extends Person> boolean personExists(long id, ArrayList<T> people) {
        for (Person p : people) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
