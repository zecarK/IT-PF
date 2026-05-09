package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class StudentToolPanel extends JPanel {

    User currentUser;

    public StudentToolPanel(User user) {
        
        this.currentUser = user;
        
        setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Student Tools - Coming Soon", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        
        add(label, BorderLayout.CENTER);
    }
}