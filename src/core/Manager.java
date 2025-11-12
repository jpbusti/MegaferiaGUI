/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

/**
 *
 * @author edangulo
 */
public class Manager extends Person {
    
    private Publisher publisher;

    public Manager(String name, long id) {
        super(name, id);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
}
