import javax.swing.*;
import java.awt.*;
import java.sql.*;

class RegistrationForm extends JFrame {
    public RegistrationForm() {
        setTitle("Register");
        setSize(350,250);
        setLayout(new GridLayout(4,2,10,10));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> events = new JComboBox<>(new String[]{"Tech Fest","Music Night"});
        JButton submit = new JButton("Register");

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Event:")); add(events);
        add(new JLabel()); add(submit);

        submit.addActionListener(e -> {
    try {
        String name = nameField.getText();
        String email = emailField.getText();
        String event = events.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty())
            throw new Exception("All fields required!");

        int ticket = TicketGenerator.generateTicket();

        String sql = "INSERT INTO participants(name,email,event,ticketId) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, event);
            pstmt.setInt(4, ticket);
            pstmt.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Registered! Ticket ID: " + ticket);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
});


        setVisible(true);
    }
}
    