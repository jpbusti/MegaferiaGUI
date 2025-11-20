package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.views.MegaferiaFrame;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        // Configuración del tema visual (FlatLaf)
        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Error al iniciar el tema visual");
        }

        // Iniciar la aplicación
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MegaferiaFrame().setVisible(true);
            }
        });
    }
}

