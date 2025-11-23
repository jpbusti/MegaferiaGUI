/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Publisher;
import core.models.Stand;
import java.util.ArrayList;

public interface IStandRepository {
    ArrayList<Stand> getStands();
    ArrayList<Publisher> getPublishers();
    
    void addStand(Stand stand);
    void addPublisher(Publisher publisher);
}