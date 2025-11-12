/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Stand {
    
    private long id;
    private double price;
    private ArrayList<Publisher> publishers;

    public Stand(long id, double price) {
        this.id = id;
        this.price = price;
        this.publishers = new ArrayList<>();
    }
    
    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
    }

    public long getId() {
        return id;
    }
    
}
