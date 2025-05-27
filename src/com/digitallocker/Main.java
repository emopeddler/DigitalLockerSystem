package com.digitallocker;

import com.digitallocker.view.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Modern theme
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}