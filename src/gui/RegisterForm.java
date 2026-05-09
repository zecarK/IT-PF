package gui;

import daoimpl.UserDAOImpl;
import exception.EmptyFieldException;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame implements ActionListener {

    // UNIT 2
    // JTextField, JFrame, JPanel, JRadioButton

    JTextField txtName, txtUsername;
    JPasswordField txtPassword;

    JRadioButton rbMale, rbFemale;

    JButton btnRegister;

    public RegisterForm() {

        setTitle("Register Form");
        setSize(450,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        // UNIT 2
        // JRadioButton
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");

        ButtonGroup group = new ButtonGroup();
        group.add(rbMale);
        group.add(rbFemale);

        btnRegister = new JButton("Create Account");

        panel.add(new JLabel("Full Name"));
        panel.add(txtName);

        panel.add(new JLabel("Username"));
        panel.add(txtUsername);

        panel.add(new JLabel("Password"));
        panel.add(txtPassword);

        panel.add(new JLabel("Sex"));

        JPanel sexPanel = new JPanel(new FlowLayout());
        sexPanel.add(rbMale);
        sexPanel.add(rbFemale);

        panel.add(sexPanel);

        panel.add(btnRegister);
        
        // UNIT 3 - Event Handling
        btnRegister.addActionListener(this);
        
        // Add panel to frame
        add(panel);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == btnRegister) {
            
            String fullName = txtName.getText();
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "");
            
            try {
                // UNIT 5 - Exception Handling
                if(fullName.isEmpty() || username.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                    throw new EmptyFieldException("All fields are required!");
                }
                
                User user = new User(fullName, username, password);
                
                // UNIT 7 - DAO Pattern
                UserDAOImpl dao = new UserDAOImpl();
                
                if(dao.registerUser(user)) {
                    JOptionPane.showMessageDialog(this, "Registration Successful!");
                    new LoginForm();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration Failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch(EmptyFieldException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}