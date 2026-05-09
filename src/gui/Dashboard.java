package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    User currentUser;

    JMenuBar menuBar;

    public Dashboard(User user) {

        this.currentUser = user;

        setTitle("Student Dashboard");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // UNIT 4
        // Menu Bar
        menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Account");
        menuFile.setMnemonic('A'); // Alt + A

        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.setMnemonic('L'); // Alt + L

        JMenuItem itemNewAcc = new JMenuItem("Add new account");
        itemNewAcc.setMnemonic('N'); // Alt + N

        menuFile.add(itemLogout);
        menuFile.add(itemNewAcc);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        // Logout action
        itemLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // close dashboard

                // Open login page
                new LoginForm();
            }
        });

        // Add new account action
        itemNewAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // close dashboard

                // Open account creation page
                new RegisterForm();
            }
        });

        // UNIT 2
        // JTabbedPane + JPanel
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Tasks", new TaskPanel(currentUser));
        tabs.add("Notes", new NotePanel(currentUser));
        tabs.add("Account Setting", new StudentToolPanel(currentUser));

        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }
}