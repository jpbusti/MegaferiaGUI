package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Publisher;
import core.models.Stand;
import core.models.storage.IStandRepository;
import java.util.ArrayList;
import java.util.Collections;

public class StandController {
    
    private final IStandRepository standRepo;

    public StandController(IStandRepository standRepo) {
        this.standRepo = standRepo;
    }
    
    public Response createStand(String idStr, String priceStr) {
        long id;
        double price;
        try {
            id = Long.parseLong(idStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return new Response("ID y Precio deben ser numéricos.", Status.BAD_REQUEST);
        }
        
        if (id < 0 || idStr.length() > 15) return new Response("ID inválido.", Status.BAD_REQUEST);
        if (price <= 0) return new Response("El precio debe ser mayor a 0.", Status.BAD_REQUEST);
        
        for (Stand s : standRepo.getStands()) {
            if (s.getId() == id) return new Response("El Stand ya existe.", Status.BAD_REQUEST);
        }
        
        standRepo.addStand(new Stand(id, price));
        return new Response("Stand creado exitosamente.", Status.CREATED);
    }
    
    public Response assignStandsToPublishers(ArrayList<String> standIds, ArrayList<String> publisherNits) {
        if (standIds.isEmpty() || publisherNits.isEmpty()) {
            return new Response("Debe seleccionar Stands y Editoriales.", Status.BAD_REQUEST);
        }
        
        ArrayList<Stand> standsToBuy = new ArrayList<>();
        ArrayList<Publisher> buyers = new ArrayList<>();
        
        // Validar existencia
        for (String sid : standIds) {
            Stand s = findStand(Long.parseLong(sid));
            if (s != null) standsToBuy.add(s);
        }
        for (String nit : publisherNits) {
            Publisher p = findPublisher(nit);
            if (p != null) buyers.add(p);
        }
        
        for (Stand s : standsToBuy) {
            for (Publisher p : buyers) {
                s.getPublishers().add(p);
            }
        }
        
        return new Response("Compra realizada con éxito.", Status.OK);
    }
    
    public ArrayList<Stand> getStands() {
        ArrayList<Stand> list = new ArrayList<>(standRepo.getStands());
        Collections.sort(list, (a, b) -> Long.compare(a.getId(), b.getId()));
        return list;
    }
    
    private Stand findStand(long id) {
        for (Stand s : standRepo.getStands()) if (s.getId() == id) return s;
        return null;
    }
    
    private Publisher findPublisher(String nit) {
        for (Publisher p : standRepo.getPublishers()) if (p.getNit().equals(nit)) return p;
        return null;
    }
}