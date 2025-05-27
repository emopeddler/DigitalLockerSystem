package com.digitallocker.view;

import com.digitallocker.dao.UserDao;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordFrame extends JFrame {
    private JPasswordField oldPassField, newPassField;
    private String username;

    public ChangePasswordFrame(String username) {
        this.username = username;

        setTitle("Change Password");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        getContentPane().setBackground(new Color(34, 40, 49));

        JLabel oldPassLabel = new JLabel("Old Password:");
        oldPassLabel.setForeground(Color.WHITE);
        oldPassField = new JPasswordField();

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setForeground(Color.WHITE);
        newPassField = new JPasswordField();

        JButton changeBtn = new JButton("Change Password");
        changeBtn.setBackground(new Color(0, 173, 181));
        changeBtn.setForeground(Color.WHITE);
        changeBtn.addActionListener(e -> changePassword());

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.GRAY);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(e -> dispose());

        add(oldPassLabel);
        add(oldPassField);
        add(newPassLabel);
        add(newPassField);
        add(changeBtn);
        add(cancelBtn);

        setVisible(true);
    }

    private void changePassword() {
        String oldPass = String.valueOf(oldPassField.getPassword());
        String newPass = String.valueOf(newPassField.getPassword());

        if (oldPass.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        boolean success = UserDao.changePassword(username, oldPass, newPass);
        if (success) {
            JOptionPane.showMessageDialog(this, "Password changed successfully.");
            dispose(); // close window
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect old password.");
        }
    }
}
