package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotePanel extends JPanel {

    // UNIT 2 - GUI components
    JTextField txtTitle;
    JTextArea txtContent;

    JButton btnSaveFile;
    JButton btnOpenFile;
    JButton btnClear;

    User currentUser;

    public NotePanel(User user) {

        this.currentUser = user;

        // UNIT 2 - Layout Manager
        setLayout(new BorderLayout(10,10));
        
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        txtTitle = new JTextField();

        txtContent = new JTextArea();
        
        btnSaveFile = new JButton("Save Note");
        btnSaveFile.setFont(buttonFont);
        btnOpenFile = new JButton("Open Note");
        btnOpenFile.setFont(buttonFont);
        btnClear = new JButton("Clear");
        btnClear.setFont(buttonFont);
        
        JButton[] buttons = {
            btnSaveFile,
            btnOpenFile,
            btnClear
    };

    for(JButton btn : buttons) {

        btn.setFont(buttonFont);

        btn.setBackground(new Color(204,255,255));

        btn.setPreferredSize(new Dimension(145,35));
    }
        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setFont(labelFont);
        topPanel.add(titleLabel, BorderLayout.WEST);

        topPanel.add(txtTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // CENTER
        add(new JScrollPane(txtContent), BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout());

        bottomPanel.add(btnSaveFile);
        bottomPanel.add(btnOpenFile);
        bottomPanel.add(btnClear);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button Events
        btnSaveFile.addActionListener(e -> saveNote());

        btnOpenFile.addActionListener(e -> openNote());

        btnClear.addActionListener(e -> {
            txtTitle.setText("");
            txtContent.setText("");
        });

        // ============================
        // Keyboard Accelerators
        // ============================

        // CTRL + S
        KeyStroke saveKey = KeyStroke.getKeyStroke(
                KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK
        );

        // CTRL + O
        KeyStroke openKey = KeyStroke.getKeyStroke(
                KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK
        );

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(saveKey, "saveNote");

        getActionMap().put("saveNote",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        saveNote();
                    }
                });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(openKey, "openNote");

        getActionMap().put("openNote",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        openNote();
                    }
                });
    }

    // UNIT 6 - File Handling
    private void saveNote() {

        try {

            JFileChooser chooser = new JFileChooser();

            int option = chooser.showSaveDialog(this);

            if(option == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();

                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(file + ".txt")
                );

                writer.write(txtContent.getText());

                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Note saved successfully"
                );
            }

        } catch(IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // UNIT 6 - File Handling
    private void openNote() {

        try {

            JFileChooser chooser = new JFileChooser();

            int option = chooser.showOpenDialog(this);

            if(option == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();

                // Put filename in title field
                String fileName = file.getName();

                // Remove .txt extension
                if(fileName.endsWith(".txt")) {
                    fileName = fileName.substring(
                            0,
                            fileName.length() - 4
                    );
                }

                txtTitle.setText(fileName);

                Path path = Paths.get(file.getAbsolutePath());

                String content = new String(
                        Files.readAllBytes(path)
                );

                // Put file content in content area
                txtContent.setText(content);
            }

        } catch(IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error opening file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}