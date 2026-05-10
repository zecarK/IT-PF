package gui;

import database.DBConnection;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TaskPanel extends JPanel {

    JTextField txtTitle;
    JTextArea txtDescription;

    JComboBox<String> cmbStatus;
    JComboBox<String> cmbUrgency;

    JButton btnAdd;
    JButton btnUpdate;
    JButton btnDelete;

    JTable taskTable;

    DefaultTableModel model;

    Connection con = DBConnection.getConnection();

    User currentUser;

    public TaskPanel(User user) {

        this.currentUser = user;

    // Fonts
    Font labelFont = new Font("Arial", Font.BOLD, 18);
    Font textFont = new Font("Arial", Font.PLAIN, 18);
    Font buttonFont = new Font("Arial", Font.BOLD, 18);

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    JPanel form = new JPanel(new GridLayout(5,2,10,10));
    form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    form.setBackground(Color.WHITE);

    txtTitle = new JTextField();
    txtTitle.setFont(textFont);

    txtDescription = new JTextArea(3,20);
    txtDescription.setFont(textFont);

    cmbStatus = new JComboBox<>(
            new String[]{"Pending", "Completed"}
    );
    cmbStatus.setFont(textFont);

    cmbUrgency = new JComboBox<>(
            new String[]{
                    "Most important",
                    "Important",
                    "Least important"
            }
    );
    cmbUrgency.setFont(textFont);

    btnAdd = new JButton("Add Task");
    btnUpdate = new JButton("Update Task");
    btnDelete = new JButton("Delete Task");

    // Button Styling
    JButton[] buttons = {
            btnAdd,
            btnUpdate,
            btnDelete
    };

    for(JButton btn : buttons) {

        btn.setFont(buttonFont);

        btn.setBackground(new Color(204,255,255));

        btn.setPreferredSize(new Dimension(145,35));
    }

    // Labels
    JLabel lblTitle = new JLabel("Title");
    lblTitle.setFont(labelFont);

    JLabel lblDescription = new JLabel("Description");
    lblDescription.setFont(labelFont);

    JLabel lblStatus = new JLabel("Status");
    lblStatus.setFont(labelFont);

    JLabel lblUrgency = new JLabel("Urgency");
    lblUrgency.setFont(labelFont);

    form.add(lblTitle);
    form.add(txtTitle);

    form.add(lblDescription);
    form.add(new JScrollPane(txtDescription));

    form.add(lblStatus);
    form.add(cmbStatus);

    form.add(lblUrgency);
    form.add(cmbUrgency);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.WHITE);

    buttonPanel.add(btnAdd);
    buttonPanel.add(btnUpdate);
    buttonPanel.add(btnDelete);

    form.add(buttonPanel);

    add(form, BorderLayout.NORTH);

    // JTable
    String[] columns = {
            "Task ID",
            "Title",
            "Description",
            "Status",
            "Urgency"
    };

    model = new DefaultTableModel(columns,0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    taskTable = new JTable(model);

    // Table Fonts
    taskTable.setFont(new Font("Arial", Font.PLAIN, 18));

    taskTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 18)
    );

    // Row height
    taskTable.setRowHeight(35);

    // Set column widths
    taskTable.getColumnModel().getColumn(0).setPreferredWidth(60);
    taskTable.getColumnModel().getColumn(1).setPreferredWidth(150);
    taskTable.getColumnModel().getColumn(2).setPreferredWidth(300);
    taskTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    taskTable.getColumnModel().getColumn(4).setPreferredWidth(150);

    // Renderer for urgency colors
    taskTable.getColumnModel().getColumn(4)
            .setCellRenderer(new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component c = super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column
            );

            c.setFont(new Font("Arial", Font.PLAIN, 18));

            String urgency = value.toString();

            if(urgency.equals("Most important")) {

                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);

            } else if(urgency.equals("Important")) {

                c.setBackground(Color.BLUE);
                c.setForeground(Color.WHITE);

            } else {

                c.setBackground(Color.GREEN);
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    });

    JScrollPane scrollPane = new JScrollPane(taskTable);

    add(scrollPane, BorderLayout.CENTER);

    // Event Handling
    btnAdd.addActionListener(e -> addTask());
    btnUpdate.addActionListener(e -> updateTask());
    btnDelete.addActionListener(e -> deleteTask());

    taskTable.getSelectionModel().addListSelectionListener(e -> {

        int selectedRow = taskTable.getSelectedRow();

        if(selectedRow != -1) {

            txtTitle.setText(
                    model.getValueAt(selectedRow, 1).toString()
            );

            txtDescription.setText(
                    model.getValueAt(selectedRow, 2).toString()
            );

            cmbStatus.setSelectedItem(
                    model.getValueAt(selectedRow, 3).toString()
            );

            cmbUrgency.setSelectedItem(
                    model.getValueAt(selectedRow, 4).toString()
            );
        }
    });

    listTasks();
    }

    private void addTask() {

        String title = txtTitle.getText();

        String description = txtDescription.getText();

        String status = (String) cmbStatus.getSelectedItem();

        String urgency = (String) cmbUrgency.getSelectedItem();

        if(title.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            String sql =
                    "INSERT INTO tasks(user_id, title, description, status, urgency) " +
                    "VALUES(?,?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, currentUser.getUserId());
            pst.setString(2, title);
            pst.setString(3, description);
            pst.setString(4, status);
            pst.setString(5, urgency);

            if(pst.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Task added successfully"
                );

                clearForm();

                listTasks();
            }

        } catch(SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listTasks() {

        model.setRowCount(0);

        try {

            String sql = "SELECT * FROM tasks WHERE user_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, currentUser.getUserId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("task_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("urgency")
                });
            }

        } catch(SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateTask() {

         int selectedRow = taskTable.getSelectedRow();

    if(selectedRow == -1) {

        JOptionPane.showMessageDialog(
                this,
                "Please select a task",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    // Get task ID
    int taskId = (int) model.getValueAt(selectedRow, 0);

    // Get updated values from form
    String title = txtTitle.getText();

    String description = txtDescription.getText();

    String status = (String) cmbStatus.getSelectedItem();

    String urgency = (String) cmbUrgency.getSelectedItem();

    // Validation
    if(title.isEmpty() || description.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    try {

        String sql =
                "UPDATE tasks " +
                "SET title=?, description=?, status=?, urgency=? " +
                "WHERE task_id=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, title);

        pst.setString(2, description);

        pst.setString(3, status);

        pst.setString(4, urgency);

        pst.setInt(5, taskId);

        if(pst.executeUpdate() > 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Task updated successfully"
            );

            clearForm();

            listTasks();
        }

    } catch(SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }

    private void deleteTask() {

        int selectedRow = taskTable.getSelectedRow();

        if(selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a task",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this task?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if(confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int taskId = (int) model.getValueAt(selectedRow, 0);

        try {

            String sql = "DELETE FROM tasks WHERE task_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, taskId);

            if(pst.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Task deleted successfully"
                );

                listTasks();
            }

        } catch(SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {

        txtTitle.setText("");

        txtDescription.setText("");

        cmbStatus.setSelectedIndex(0);

        cmbUrgency.setSelectedIndex(0);
    }
}