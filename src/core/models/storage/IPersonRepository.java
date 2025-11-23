/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Author;
import core.models.Manager;
import core.models.Narrator;
import java.util.ArrayList;

public interface IPersonRepository {
    ArrayList<Author> getAuthors();
    ArrayList<Manager> getManagers();
    ArrayList<Narrator> getNarrators();
    
    void addAuthor(Author author);
    void addManager(Manager manager);
    void addNarrator(Narrator narrator);
}