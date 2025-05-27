package com.digitallocker.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.*;

public class DashboardFrame extends JFrame {
    private String username;
    private JTable fileTable;
    private DefaultTableModel tableModel;

    public DashboardFrame(String username) {
        this.username = username;
        setTitle("Digital Locker - Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dark theme colors
        Color bg = new Color(34, 40, 49);
        Color btn = new Color(57, 62, 70);
        Color hover = new Color(0, 173, 181);
        Color text = Color.WHITE;

        getContentPane().setBackground(bg);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(text);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        // File Table
        String[] columns = {"File Name", "Size (KB)"};
        tableModel = new DefaultTableModel(columns, 0);
        fileTable = new JTable(tableModel);
        fileTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fileTable.setRowHeight(28);
        fileTable.setBackground(new Color(44, 52, 63));
        fileTable.setForeground(Color.WHITE);
        fileTable.setGridColor(Color.GRAY);
        fileTable.setSelectionBackground(new Color(0, 173, 181));

        JScrollPane scrollPane = new JScrollPane(fileTable);
        scrollPane.getViewport().setBackground(new Color(44, 52, 63));
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "Your Files"));
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 20, 0));
        buttonPanel.setBackground(bg);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JButton uploadBtn = createStyledButton("Upload", btn, hover);
        JButton downloadBtn = createStyledButton("Download", btn, hover);
        JButton deleteBtn = createStyledButton("Delete", btn, hover);
        JButton changePwdBtn = createStyledButton("Change Password", btn, hover);
        JButton logoutBtn = createStyledButton("Logout", btn, hover);

        buttonPanel.add(uploadBtn);
        buttonPanel.add(downloadBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(changePwdBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        uploadBtn.addActionListener(e -> uploadFile());
        downloadBtn.addActionListener(e -> downloadFile());
        deleteBtn.addActionListener(e -> deleteFile());
        changePwdBtn.addActionListener(e -> new ChangePasswordFrame(username));
        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        refreshFileList();
        setVisible(true);
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

    private void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File destDir = new File("user_files/" + username);
            if (!destDir.exists()) destDir.mkdirs();

            File destFile = new File(destDir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "File uploaded successfully!");
                refreshFileList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Upload failed: " + ex.getMessage());
            }
        }
    }

    private void downloadFile() {
        int selectedRow = fileTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a file to download.");
            return;
        }

        String fileName = tableModel.getValueAt(selectedRow, 0).toString();
        File sourceFile = new File("user_files/" + username + "/" + fileName);

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(fileName));
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File destination = chooser.getSelectedFile();
            try {
                Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "File downloaded successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Download failed: " + ex.getMessage());
            }
        }
    }

    private void deleteFile() {
        int selectedRow = fileTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a file to delete.");
            return;
        }

        String fileName = tableModel.getValueAt(selectedRow, 0).toString();
        File file = new File("user_files/" + username + "/" + fileName);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete this file?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (file.delete()) {
                JOptionPane.showMessageDialog(this, "File deleted successfully!");
                refreshFileList();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the file.");
            }
        }
    }

    private void refreshFileList() {
        tableModel.setRowCount(0);
        File folder = new File("user_files/" + username);
        if (!folder.exists()) return;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                tableModel.addRow(new Object[]{file.getName(), file.length() / 1024});
            }
        }
    }
}