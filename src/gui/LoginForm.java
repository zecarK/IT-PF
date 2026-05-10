package gui;

import daoimpl.UserDAOImpl;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;

    JButton btnLogin;

    JLabel lblRegister;

    public LoginForm() {

        setTitle("Student Task Manager - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fonts
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setBackground(Color.WHITE);

        // Username
        JLabel lblUsername = new JLabel("Username                                          ");
        lblUsername.setFont(labelFont);
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        txtUsername = new JTextField();
        txtUsername.setFont(textFont);
        txtUsername.setMaximumSize(new Dimension(300,40));
        

        // Password
        JLabel lblPassword = new JLabel("Password                                          ");
        lblPassword.setFont(labelFont);
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setFont(textFont);
        txtPassword.setMaximumSize(new Dimension(300,40));

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setFont(buttonFont);
        btnLogin.setBackground(new Color(204,255,255));
        btnLogin.setPreferredSize(new Dimension(100,35));
        btnLogin.setMaximumSize(new Dimension(150,40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblRegister = new JLabel(
                "<html><u>Create new account</u></html>"
        );

        lblRegister.setFont(labelFont);
        lblRegister.setForeground(new Color(0,153,204));
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Components
        panel.add(lblUsername);
        panel.add(Box.createVerticalStrut(5));
        panel.add(txtUsername);

        panel.add(Box.createVerticalStrut(15));

        panel.add(lblPassword);
        panel.add(Box.createVerticalStrut(5));
        panel.add(txtPassword);

        panel.add(Box.createVerticalStrut(20));

        panel.add(btnLogin);

        panel.add(Box.createVerticalStrut(10));

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setBackground(Color.WHITE);

        registerPanel.add(lblRegister);

        panel.add(registerPanel);

        add(panel);

        // Login Event
        btnLogin.addActionListener(e -> login());

        // Register Label Click Event
        lblRegister.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                new RegisterForm();

                dispose();
            }
        });

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText();

        String password =
                String.valueOf(txtPassword.getPassword());

        UserDAOImpl dao = new UserDAOImpl();

        User user = dao.loginUser(username, password);

        if(user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful"
            );

            new Dashboard(user);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Credentials"
            );
        }
    }
}