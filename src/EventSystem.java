import javax.swing.*;
import java.awt.*;

public class EventSystem extends JFrame {

    public EventSystem() {
        DBSetup.createTable();
        setTitle("Event Management System");
        setSize(400, 250);
        setLayout(new GridLayout(3,1,10,10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton regBtn = new JButton("Register for Event");
        JButton adminBtn = new JButton("Admin Login");
        JButton exitBtn = new JButton("Exit");

        add(regBtn);
        add(adminBtn);
        add(exitBtn);

        regBtn.addActionListener(e -> new RegistrationForm());
        adminBtn.addActionListener(e -> new AdminDashboard());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new EventSystem();
    }
}
