package gui;

import database.DBConnection;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentToolPanel extends JPanel {

    User currentUser;

    JTextField txtName, txtUsername;

    JPasswordField txtPassword;

    JRadioButton rbMale, rbFemale, rbOthers;

    JButton btnUpload, btnEdit, btnSave;

    JLabel lblProfile;

    File selectedFile;

    Connection con = DBConnection.getConnection();

    public StudentToolPanel(User user) {

        this.currentUser = user;

        setLayout(new BorderLayout());

        JPanel parentPanel = new JPanel(new BorderLayout(10,10));

        parentPanel.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );

        txtName = new JTextField();

        txtUsername = new JTextField();

        txtPassword = new JPasswordField();

        rbMale = new JRadioButton("Male");

        rbFemale = new JRadioButton("Female");

        rbOthers = new JRadioButton("Others");

        ButtonGroup group = new ButtonGroup();

        group.add(rbMale);
        group.add(rbFemale);
        group.add(rbOthers);

        btnUpload = new JButton("Upload");

        btnEdit = new JButton("Edit");

        btnSave = new JButton("Save");

        lblProfile = new JLabel();

        lblProfile.setPreferredSize(
                new Dimension(120,120)
        );

        lblProfile.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );

        lblProfile.setHorizontalAlignment(JLabel.CENTER);

        lblProfile.setText("No Image");


        txtName.setText(currentUser.getFullName());

        txtUsername.setText(currentUser.getUsername());

        txtPassword.setText(currentUser.getPassword());

        String gender = currentUser.getGender();

        if(gender != null) {

            if(gender.equalsIgnoreCase("Male")) {
                rbMale.setSelected(true);

            } else if(gender.equalsIgnoreCase("Female")) {
                rbFemale.setSelected(true);

            } else {
                rbOthers.setSelected(true);
            }
        }

        
        if(currentUser.getProfilePicture() != null) {

            ImageIcon icon = new ImageIcon(
                    currentUser.getProfilePicture()
            );

            Image img = icon.getImage().getScaledInstance(
                    120,
                    120,
                    Image.SCALE_SMOOTH
            );

            lblProfile.setIcon(new ImageIcon(img));

            lblProfile.setText("");
        }

        
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setPreferredSize(new Dimension(200,25));
        panel.add(nameLabel);
        txtName.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(txtName);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setPreferredSize(new Dimension(200,25));
        panel.add(usernameLabel);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(txtUsername);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setPreferredSize(new Dimension(200,25));
        panel.add(passLabel);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(txtPassword);
        
        JLabel sexLabel = new JLabel("Password:");
        sexLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sexLabel.setPreferredSize(new Dimension(200,25));
        panel.add(sexLabel);

        JPanel sexPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );
        rbMale.setFont(new Font("Arial", Font.BOLD, 18));
        rbFemale.setFont(new Font("Arial", Font.BOLD, 18));
        rbOthers.setFont(new Font("Arial", Font.BOLD, 18));
        sexPanel.add(rbMale);
        sexPanel.add(rbFemale);
        sexPanel.add(rbOthers);

        panel.add(sexPanel);

        // Image Panel
        JPanel imagePanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        JLabel imageLabel = new JLabel("Profile Picture:");
        imageLabel.setBounds(40, 300, 200, 35);
        imageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        parentPanel.add(imageLabel);

        

        imagePanel.add(lblProfile);
        btnUpload.setFont(new Font("Arial", Font.BOLD, 18));
        btnUpload.setPreferredSize(new Dimension(100,35));
        btnUpload.setBackground(new Color(204,255,255));
        imagePanel.add(btnUpload);
        imagePanel.add(new JLabel("                 "));
        imagePanel.add(new JLabel("                 "));

        // Button Panel
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER,10,10)
        );
        btnEdit.setFont(new Font("Arial", Font.BOLD, 18));
        btnEdit.setPreferredSize(new Dimension(100,35));
        btnEdit.setBackground(new Color(204,255,255));
        btnSave.setFont(new Font("Arial", Font.BOLD, 18));
        btnSave.setPreferredSize(new Dimension(100,35));
        btnSave.setBackground(new Color(204,255,255));
        
        buttonPanel.add(btnEdit);

        buttonPanel.add(btnSave);

        // Add Child Panels
        parentPanel.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        parentPanel.add(panel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        parentPanel.add(imagePanel, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.WHITE);
        parentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(parentPanel);

        // =========================
        // INITIAL STATE
        // =========================

        setEditable(false);

        btnSave.setEnabled(false);

        // =========================
        // EVENTS
        // =========================

        // Edit Button
        btnEdit.addActionListener(e -> {

            setEditable(true);

            txtUsername.setEditable(false);

            btnEdit.setEnabled(false);

            btnSave.setEnabled(true);
        });

        // Upload Button
        btnUpload.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            int result = chooser.showOpenDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {

                selectedFile = chooser.getSelectedFile();

                ImageIcon icon = new ImageIcon(
                        selectedFile.getAbsolutePath()
                );

                Image img = icon.getImage().getScaledInstance(
                        120,
                        120,
                        Image.SCALE_SMOOTH
                );

                lblProfile.setIcon(new ImageIcon(img));

                lblProfile.setText("");
            }
        });

        // Save Button
        btnSave.addActionListener(e -> saveProfile());
    }

    private void setEditable(boolean editable) {

        txtName.setEditable(editable);

        txtPassword.setEditable(editable);

        rbMale.setEnabled(editable);

        rbFemale.setEnabled(editable);

        rbOthers.setEnabled(editable);

        btnUpload.setEnabled(editable);

        // Username cannot be edited
        txtUsername.setEditable(false);
    }

    private void saveProfile() {

        try {

            String fullName = txtName.getText();

            String password = String.valueOf(
                    txtPassword.getPassword()
            );

            String gender =
                    rbMale.isSelected() ? "Male" :
                    (rbFemale.isSelected() ? "Female" :
                    (rbOthers.isSelected() ? "Others" : ""));

            byte[] imageBytes =
                    currentUser.getProfilePicture();

            // New Image
            if(selectedFile != null) {

                FileInputStream fis =
                        new FileInputStream(selectedFile);

                imageBytes =
                        new byte[(int) selectedFile.length()];

                fis.read(imageBytes);

                fis.close();
            }

            String sql =
                    "UPDATE users SET full_name=?, password=?, sex=?, profile_picture=? " +
                    "WHERE user_id=?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, fullName);

            pst.setString(2, password);

            pst.setString(3, gender);

            pst.setBytes(4, imageBytes);

            pst.setInt(5, currentUser.getUserId());

            if(pst.executeUpdate() > 0) {

                // Update current user object
                currentUser.setFullName(fullName);

                currentUser.setPassword(password);

                currentUser.setGender(gender);

                currentUser.setProfilePicture(imageBytes);

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully"
                );

                setEditable(false);

                btnEdit.setEnabled(true);

                btnSave.setEnabled(false);
            }

        } catch(Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}