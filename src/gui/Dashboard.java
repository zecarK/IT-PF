package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

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
        JMenuItem itemLogout = new JMenuItem("Logout");
        JMenuItem itemNewAcc = new JMenuItem("Add new account");

        menuFile.add(itemLogout);
        menuFile.add(itemNewAcc);
        menuBar.add(menuFile);
        

        setJMenuBar(menuBar);

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