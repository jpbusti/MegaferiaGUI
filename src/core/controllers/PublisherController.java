package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Manager;
import core.models.Publisher;
import core.models.storage.IPersonRepository;
import core.models.storage.IStandRepository;
import java.util.ArrayList;
import java.util.Collections;

public class PublisherController {
    
    private final IStandRepository standRepo;
    private final IPersonRepository personRepo;

    public PublisherController(IStandRepository standRepo, IPersonRepository personRepo) {
        this.standRepo = standRepo;
        this.personRepo = personRepo;
    }

    public Response createPublisher(String nit, String name, String address, String managerIdStr) {
        if (nit == null || !nit.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d")) {
            return new Response("El NIT debe tener formato XXX.XXX.XXX-X", Status.BAD_REQUEST);
        }
        for (Publisher p : standRepo.getPublishers()) {
            if (p.getNit().equals(nit)) return new Response("Ya existe una editorial con ese NIT.", Status.BAD_REQUEST);
        }
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            return new Response("Todos los campos son obligatorios.", Status.BAD_REQUEST);
        }
        
        Manager manager = null;
        try {
            long mid = Long.parseLong(managerIdStr);
            for (Manager m : personRepo.getManagers()) {
                if (m.getId() == mid) {
                    manager = m;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            return new Response("ID de gerente inválido.", Status.BAD_REQUEST);
        }

        if (manager == null) return new Response("El gerente seleccionado no es válido.", Status.BAD_REQUEST);

        Publisher newPub = new Publisher(nit, name, address, manager);
        standRepo.addPublisher(newPub);
        return new Response("Editorial creada exitosamente.", Status.CREATED);
    }
    
    public ArrayList<Publisher> getPublishers() {
        ArrayList<Publisher> list = new ArrayList<>(standRepo.getPublishers());
        Collections.sort(list, (a, b) -> a.getNit().compareTo(b.getNit()));
        return list;
    }
}