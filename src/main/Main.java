package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.controllers.BookController;
import core.controllers.PersonController;
import core.controllers.PublisherController;
import core.controllers.StandController;
import core.models.storage.Storage;
import core.views.MegaferiaFrame;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Error al iniciar el tema visual");
        }

        Storage storage = Storage.getInstance();


        PersonController personController = new PersonController(storage);
        StandController standController = new StandController(storage);
        PublisherController publisherController = new PublisherController(storage, storage);
        BookController bookController = new BookController(storage, storage, storage);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MegaferiaFrame(standController, personController, publisherController, bookController).setVisible(true);
            }
        });
    }
}

