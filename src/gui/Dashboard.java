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

        JMenu menuFile = new JMenu("File");
        JMenuItem itemLogout = new JMenuItem("Logout");

        menuFile.add(itemLogout);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        // UNIT 2
        // JTabbedPane + JPanel
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Tasks", new TaskPanel(currentUser));
        tabs.add("Notes", new NotePanel(currentUser));
        tabs.add("Student Tools", new StudentToolPanel(currentUser));

        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }
}