package core.controllers;

import core.models.Manager;
import core.models.Publisher;
import core.models.storage.IStorage;
import core.models.storage.Storage;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import java.util.ArrayList;
import java.util.Collections;

public class PublisherController {
    
    private IStorage storage;

    public PublisherController() {
        this.storage = Storage.getInstance();
    }
    
    public Response createPublisher(String nit, String name, String address, String managerIdStr) {
        if (nit == null || nit.trim().isEmpty() || name == null || name.trim().isEmpty() || address == null || address.trim().isEmpty()) {
            return new Response("Todos los campos son obligatorios.", Status.BAD_REQUEST);
        }
        
        if (!nit.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d")) {
            return new Response("El NIT debe tener el formato XXX.XXX.XXX-X", Status.BAD_REQUEST);
        }
        
        for (Publisher p : storage.getPublishers()) {
            if (p.getNit().equals(nit)) {
                return new Response("Ya existe una editorial con ese NIT.", Status.BAD_REQUEST);
            }
        }
        
        long managerId;
        try {
            managerId = Long.parseLong(managerIdStr);
        } catch (NumberFormatException e) {
            return new Response("ID de gerente inválido.", Status.BAD_REQUEST);
        }
        
        Manager selectedManager = null;
        for (Manager m : storage.getManagers()) {
            if (m.getId() == managerId) {
                selectedManager = m;
                break;
            }
        }
        
        if (selectedManager == null) {
            return new Response("El gerente seleccionado no existe.", Status.BAD_REQUEST);
        }
        
        Publisher newPublisher = new Publisher(nit, name, address, selectedManager);
        selectedManager.setPublisher(newPublisher);
        storage.getPublishers().add(newPublisher);
        
        return new Response("Editorial creada exitosamente.", Status.CREATED);
    }
    
    public ArrayList<Publisher> getPublishers() {
        ArrayList<Publisher> copies = new ArrayList<>();
        for (Publisher p : storage.getPublishers()) {
            copies.add(p.clone());
        }
        Collections.sort(copies, (p1, p2) -> p1.getNit().compareTo(p2.getNit()));
        return copies;
    }
}