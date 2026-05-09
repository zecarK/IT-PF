package gui;

import daoimpl.UserDAOImpl;
import exception.EmptyFieldException;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;

public class RegisterForm extends JFrame implements ActionListener {

    // UNIT 2
    // JTextField, JFrame, JPanel, JRadioButton

    JTextField txtName, txtUsername;
    JPasswordField txtPassword;

    JRadioButton rbMale, rbFemale, rbOthers;

    JButton btnRegister, btnUpload, btnBack;

    JLabel lblProfile;

    File selectedFile;

    public RegisterForm() {

        setTitle("Register Form");
        setSize(550,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel parentPanel = new JPanel(new BorderLayout(10, 10));
        parentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        // UNIT 2
        // JRadioButton
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        rbOthers = new JRadioButton("Others");

        ButtonGroup group = new ButtonGroup();
        group.add(rbMale);
        group.add(rbFemale);
        group.add(rbOthers);

        btnRegister = new JButton("Create Account");
        btnBack = new JButton("Back");
        btnUpload = new JButton("Upload");

        // Profile picture label
        lblProfile = new JLabel();
        lblProfile.setPreferredSize(new Dimension(120,120));
        lblProfile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblProfile.setHorizontalAlignment(JLabel.CENTER);
        lblProfile.setText("No Image");

        panel.add(new JLabel("Full Name"));
        panel.add(txtName);

        panel.add(new JLabel("Username"));
        panel.add(txtUsername);

        panel.add(new JLabel("Password"));
        panel.add(txtPassword);

        panel.add(new JLabel("Sex"));

        JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sexPanel.add(rbMale);
        sexPanel.add(rbFemale);
        sexPanel.add(rbOthers);

        panel.add(sexPanel);

        // Profile picture section
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(new JLabel("Profile Picture"));
        imagePanel.add(new JLabel("                                         "));
        imagePanel.add(lblProfile);
        imagePanel.add(btnUpload);

        panel.add(imagePanel);
        

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnBack);
        buttonPanel.add(btnRegister);

        // UNIT 3 - Event Handling
        btnRegister.addActionListener(this);
        btnUpload.addActionListener(this);
        btnBack.addActionListener(this);
        
        //Add child panels to parent Panel
        parentPanel.add(panel, BorderLayout.NORTH);
        parentPanel.add(imagePanel, BorderLayout.CENTER);
        parentPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Add panel to frame
        add(parentPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Upload Image
        if(e.getSource() == btnUpload) {

            JFileChooser chooser = new JFileChooser();

            int result = chooser.showOpenDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {

                selectedFile = chooser.getSelectedFile();

                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());

                Image img = icon.getImage().getScaledInstance(
                        120,
                        120,
                        Image.SCALE_SMOOTH
                );

                lblProfile.setIcon(new ImageIcon(img));
                lblProfile.setText("");
            }
        }

        // Register
        if(e.getSource() == btnRegister) {

            String fullName = txtName.getText();
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());

            String gender =
                    rbMale.isSelected() ? "Male" :
                    (rbFemale.isSelected() ? "Female" :
                    (rbOthers.isSelected() ? "Others" : ""));

            try {

                // UNIT 5 - Exception Handling
                if(fullName.isEmpty() ||
                        username.isEmpty() ||
                        password.isEmpty() ||
                        gender.isEmpty() ||
                        selectedFile == null) {

                    throw new EmptyFieldException("All fields are required!");
                }

                // Convert image to byte array
                FileInputStream fis = new FileInputStream(selectedFile);

                byte[] profilePicture = new byte[(int) selectedFile.length()];

                fis.read(profilePicture);

                fis.close();

                User user = new User(
                        fullName,
                        username,
                        password,
                        profilePicture,
                        gender
                );

                // UNIT 7 - DAO Pattern
                UserDAOImpl dao = new UserDAOImpl();

                if(dao.registerUser(user)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Registration Successful!"
                    );

                    new LoginForm();

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Registration Failed",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        // Back button
        if(e.getSource() == btnBack) {

            new LoginForm();

            dispose();
        }
    }
}