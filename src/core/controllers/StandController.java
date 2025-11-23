package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Publisher;
import core.models.Stand;
import core.models.storage.IStorage;
import core.models.storage.Storage;
import java.util.ArrayList;
import java.util.Collections;

public class StandController {
    
    private IStorage storage;

    public StandController() {
        this.storage = Storage.getInstance();
    }
    
    public Response createStand(String idStr, String priceStr) {
        try {
            long id;
            double price;

            try {
                id = Long.parseLong(idStr);
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                return new Response("El ID y el Precio deben ser numéricos.", Status.BAD_REQUEST);
            }

            if (id < 0) {
                return new Response("El ID debe ser mayor o igual a 0.", Status.BAD_REQUEST);
            }
            if (String.valueOf(id).length() > 15) {
                return new Response("El ID no debe tener más de 15 dígitos.", Status.BAD_REQUEST);
            }
            if (price <= 0) {
                return new Response("El precio debe ser superior a 0.", Status.BAD_REQUEST);
            }

            for (Stand stand : storage.getStands()) {
                if (stand.getId() == id) {
                    return new Response("Ya existe un Stand con ese ID.", Status.BAD_REQUEST);
                }
            }

            Stand newStand = new Stand(id, price);
            storage.getStands().add(newStand);
            
            return new Response("Stand creado exitosamente.", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Error inesperado al crear el stand.", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ArrayList<Stand> getStands() {
        ArrayList<Stand> copies = new ArrayList<>();
        for (Stand s : storage.getStands()) {
            copies.add(s.clone());
        }
        Collections.sort(copies, (s1, s2) -> Long.compare(s1.getId(), s2.getId()));
        return copies;
    }
    
    public Response assignStandsToPublishers(ArrayList<String> standIds, ArrayList<String> publisherNits) {
        if (standIds == null || standIds.isEmpty()) {
            return new Response("Debe seleccionar al menos un Stand.", Status.BAD_REQUEST);
        }
        if (publisherNits == null || publisherNits.isEmpty()) {
            return new Response("Debe seleccionar al menos una Editorial.", Status.BAD_REQUEST);
        }

        ArrayList<Stand> selectedStands = new ArrayList<>();
        
        for (String idStr : standIds) {
            try {
                long id = Long.parseLong(idStr);
                boolean found = false;
                for (Stand s : storage.getStands()) {
                    if (s.getId() == id) {
                        selectedStands.add(s);
                        found = true;
                        break;
                    }
                }
                if (!found) return new Response("El Stand con ID " + id + " no existe.", Status.BAD_REQUEST);
            } catch (NumberFormatException e) {
                return new Response("ID de Stand inválido: " + idStr, Status.BAD_REQUEST);
            }
        }

        ArrayList<Publisher> selectedPublishers = new ArrayList<>();
        for (String nit : publisherNits) {
            boolean found = false;
            for (Publisher p : storage.getPublishers()) {
                if (p.getNit().equals(nit)) {
                    selectedPublishers.add(p);
                    found = true;
                    break;
                }
            }
            if (!found) return new Response("La Editorial con NIT " + nit + " no existe.", Status.BAD_REQUEST);
        }

        for (Stand stand : selectedStands) {
            for (Publisher publisher : selectedPublishers) {
                stand.addPublisher(publisher);
                publisher.addStand(stand);
            }
        }

        return new Response("Compra de Stands registrada exitosamente.", Status.OK);
    }
}