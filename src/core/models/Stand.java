package core.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Stand implements Cloneable {
    
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

    public double getPrice() {
        return price;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }
    
    public int getPublisherQuantity() {
        return this.publishers.size();
    }

    @Override
    public Stand clone() {
        try {
            Stand cloned = (Stand) super.clone();
            cloned.publishers = new ArrayList<>(this.publishers);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}