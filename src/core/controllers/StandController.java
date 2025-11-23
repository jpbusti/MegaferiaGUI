package core.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Publisher;
import core.models.Stand;
import core.models.storage.Storage;
import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public class StandController {
    
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

            Storage storage = Storage.getInstance();
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
        return Storage.getInstance().getStands();
    }
    
    public Response assignStandsToPublishers(java.util.ArrayList<String> standIds, java.util.ArrayList<String> publisherNits) {
        if (standIds == null || standIds.isEmpty()) {
            return new Response("Debe seleccionar al menos un Stand.", Status.BAD_REQUEST);
        }
        if (publisherNits == null || publisherNits.isEmpty()) {
            return new Response("Debe seleccionar al menos una Editorial.", Status.BAD_REQUEST);
        }

        java.util.ArrayList<Stand> selectedStands = new java.util.ArrayList<>();
        Storage storage = Storage.getInstance();
        
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

        java.util.ArrayList<Publisher> selectedPublishers = new java.util.ArrayList<>();
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
