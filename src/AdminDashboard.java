import javax.swing.*;
import java.awt.*;
import java.sql.*;

class AdminDashboard extends JFrame {

    JTextArea area = new JTextArea();
    JTextField editIdField = new JTextField();
    JTextField editNameField = new JTextField();
    JTextField editEmailField = new JTextField();
    JComboBox<String> editEvent = new JComboBox<>(new String[]{"Tech Fest","Music Night"});

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(500,400);
        setLayout(new BorderLayout());

        // Top panel buttons
        JPanel topPanel = new JPanel();
        JButton viewBtn = new JButton("View Participants");
        JButton deleteBtn = new JButton("Delete by Ticket ID");
        JButton editBtn = new JButton("Edit Participant");

        topPanel.add(viewBtn);
        topPanel.add(deleteBtn);
        topPanel.add(editBtn);

        add(topPanel, BorderLayout.NORTH);

        // Center display area
        add(new JScrollPane(area), BorderLayout.CENTER);

        // Edit panel
        JPanel editPanel = new JPanel(new GridLayout(4,2,5,5));
        editPanel.add(new JLabel("Ticket ID to Edit:")); editPanel.add(editIdField);
        editPanel.add(new JLabel("New Name:")); editPanel.add(editNameField);
        editPanel.add(new JLabel("New Email:")); editPanel.add(editEmailField);
        editPanel.add(new JLabel("New Event:")); editPanel.add(editEvent);
        add(editPanel, BorderLayout.SOUTH);

        // Button Actions
        viewBtn.addActionListener(e -> loadData());

        deleteBtn.addActionListener(e -> deleteParticipant());

        editBtn.addActionListener(e -> editParticipant());

        setVisible(true);
    }

    private void loadData() {
        area.setText("");
        String sql = "SELECT * FROM participants";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                area.append("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") +
                            ", Event: " + rs.getString("event") +
                            ", Ticket: " + rs.getInt("ticketId") + "\n");
            }

        } catch (Exception e) {
            area.setText("Error loading data");
        }
    }

    private void deleteParticipant() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Ticket ID to Delete:");
        if(idStr == null || idStr.isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            String sql = "DELETE FROM participants WHERE ticketId=?";
            try (Connection conn = DBConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                int rows = pstmt.executeUpdate();
                if(rows > 0) JOptionPane.showMessageDialog(this, "Deleted successfully!");
                else JOptionPane.showMessageDialog(this, "Ticket ID not found.");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
        loadData();
    }

    private void editParticipant() {
        try {
            int id = Integer.parseInt(editIdField.getText());
            String name = editNameField.getText();
            String email = editEmailField.getText();
            String event = editEvent.getSelectedItem().toString();

            String sql = "UPDATE participants SET name=?, email=?, event=? WHERE ticketId=?";
            try (Connection conn = DBConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, event);
                pstmt.setInt(4, id);

                int rows = pstmt.executeUpdate();
                if(rows > 0) JOptionPane.showMessageDialog(this, "Updated successfully!");
                else JOptionPane.showMessageDialog(this, "Ticket ID not found.");
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
        loadData();
    }
}
