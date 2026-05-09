package gui;

import database.DBConnection;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TaskPanel extends JPanel {

    JTextField txtTitle;
    JTextArea txtDescription;

    JComboBox<String> cmbStatus;

    JCheckBox chkFinished;

    JButton btnAdd;
    JButton btnList;
    JButton btnUpdate;

    JList<String> taskList;

    DefaultListModel<String> model;

    Connection con = DBConnection.getConnection();

    User currentUser;

    public TaskPanel(User user) {

        this.currentUser = user;

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        txtTitle = new JTextField();
        txtDescription = new JTextArea();

        cmbStatus = new JComboBox<>(new String[]{"Pending", "Completed"});

        // UNIT 2
        // JCheckBox
        chkFinished = new JCheckBox("Finished?");

        btnAdd = new JButton("Add Task");
        btnList = new JButton("List Tasks");
        btnUpdate = new JButton("Update Tasks");

        form.add(new JLabel("Title"));
        form.add(txtTitle);

        form.add(new JLabel("Description"));
        form.add(new JScrollPane(txtDescription));

        form.add(new JLabel("Status"));
        form.add(cmbStatus);

        form.add(chkFinished);
        form.add(new JLabel(""));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnList);
        buttonPanel.add(btnUpdate);

        form.add(buttonPanel);

        add(form, BorderLayout.NORTH);

        // UNIT 2 - JList with DefaultListModel
        model = new DefaultListModel<>();
        taskList = new JList<>(model);
        taskList.setBorder(BorderFactory.createTitledBorder("Task List"));

        add(new JScrollPane(taskList), BorderLayout.CENTER);

        // UNIT 3 - Event Handling using Lambda Expressions
        btnAdd.addActionListener(e -> addTask());
        btnList.addActionListener(e -> listTasks());
        btnUpdate.addActionListener(e -> updateTask());
    }

    private void addTask() {
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        String status = (String) cmbStatus.getSelectedItem();

        if(title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String sql = "INSERT INTO tasks(user_id, title, description, status) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, currentUser.getUserId());
            pst.setString(2, title);
            pst.setString(3, description);
            pst.setString(4, status);

            if(pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Task added successfully");
                clearForm();
                listTasks();
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listTasks() {
        model.clear();

        try {
            String sql = "SELECT * FROM tasks WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, currentUser.getUserId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String taskInfo = "ID: " + rs.getInt("task_id") + " | " +
                                  rs.getString("title") + " | " +
                                  rs.getString("status");
                model.addElement(taskInfo);
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTask() {
        int selectedIndex = taskList.getSelectedIndex();

        if(selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String status = (String) cmbStatus.getSelectedItem();

        try {
            String taskInfo = model.getElementAt(selectedIndex);
            String taskIdStr = taskInfo.split(" ")[1];
            int taskId = Integer.parseInt(taskIdStr);

            String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, status);
            pst.setInt(2, taskId);

            if(pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Task updated successfully");
                listTasks();
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtTitle.setText("");
        txtDescription.setText("");
        cmbStatus.setSelectedIndex(0);
        chkFinished.setSelected(false);
    }
}