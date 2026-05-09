package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotePanel extends JPanel {

    // UNIT 2 - GUI components (JTextArea, JTextField, JButton, JScrollPane)
    JTextField txtTitle;
    JTextArea txtContent;

    JButton btnSaveFile;
    JButton btnOpenFile;
    JButton btnClear;

    User currentUser;

    public NotePanel(User user) {

        this.currentUser = user;

        // UNIT 2 - Layout Manager (BorderLayout)
        setLayout(new BorderLayout(10,10));

        txtTitle = new JTextField();
        txtContent = new JTextArea();

        btnSaveFile = new JButton("Save Note");
        btnOpenFile = new JButton("Open Note");
        btnClear = new JButton("Clear");

        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Title:"), BorderLayout.WEST);
        topPanel.add(txtTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // CENTER (UNIT 2 - JScrollPane + JTextArea)
        add(new JScrollPane(txtContent), BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout());

        bottomPanel.add(btnSaveFile);
        bottomPanel.add(btnOpenFile);
        bottomPanel.add(btnClear);

        add(bottomPanel, BorderLayout.SOUTH);

        // UNIT 3 - Event Handling using Lambda Expressions
        btnSaveFile.addActionListener(e -> saveNote());
        btnOpenFile.addActionListener(e -> openNote());
        btnClear.addActionListener(e -> txtContent.setText(""));
    }

    // UNIT 6 - File Handling (Character Streams + BufferedWriter + FileWriter)
    private void saveNote() {

        try {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();

                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(file + ".txt")
                );

                writer.write("TITLE: " + txtTitle.getText() + "\n\n");
                writer.write(txtContent.getText());

                writer.close();

                JOptionPane.showMessageDialog(this,
                        "Note saved successfully");
            }

        } catch (IOException e) {

            // UNIT 5 - Exception Handling (try-catch-finally concept)
            JOptionPane.showMessageDialog(this,
                    "Error saving file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // UNIT 6 - File Handling (BufferedReader + FileReader + Path/NIO)
    private void openNote() {

        try {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();

                Path path = Paths.get(file.getAbsolutePath());

                // UNIT 6 - java.nio.file.Files
                String content = new String(Files.readAllBytes(path));
                txtContent.setText(content);

                txtContent.setText(content);
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Error opening file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}