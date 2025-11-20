package core.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import core.controllers.utils.Response;
import core.controllers.utils.Status;
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
}
