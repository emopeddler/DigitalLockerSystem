package com.digitallocker.view;

import com.digitallocker.dao.UserDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Digital Locker - Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bg = new Color(34, 40, 49);
        Color btn = new Color(57, 62, 70);
        Color hover = new Color(0, 173, 181);
        Color text = Color.WHITE;

        getContentPane().setBackground(bg);

        JLabel title = new JLabel("Login to Digital Locker", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(text);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(bg);
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        addLabelAndField(formPanel, gbc, "Username", usernameField, 0, text);
        addLabelAndField(formPanel, gbc, "Password", passwordField, 1, text);

        JButton loginBtn = createStyledButton("Login", btn, hover);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> attemptLogin());

        JButton registerBtn = new JButton("Create Account");
        registerBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerBtn.setForeground(hover);
        registerBtn.setBackground(bg);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        gbc.gridy = 3;
        formPanel.add(registerBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row, Color color) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(color);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bg, Color hover) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(bg);
            }
        });
        return button;
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        boolean success = UserDao.validateLogin(username, password);
        if (success) {
            new DashboardFrame(username).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
        }
    }
}