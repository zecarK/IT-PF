package gui;

import daoimpl.UserDAOImpl;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    // UNIT 2
    // JFrame, JTextField, JButton, JPanel, GridLayout

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin, btnRegister;

    public LoginForm() {

        setTitle("Student Task Manager - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        panel.add(new JLabel("Username"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password"));
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel);

        // UNIT 3
        // Event-Driven Programming using Lambda Expressions
        btnLogin.addActionListener(e -> login());

        btnRegister.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        // UNIT 7
        // DAO Pattern + JDBC Login Validation
        UserDAOImpl dao = new UserDAOImpl();
        User user = dao.loginUser(username, password);

        // UNIT 4
        // Message Dialog
        if(user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            new Dashboard(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        }
    }
}