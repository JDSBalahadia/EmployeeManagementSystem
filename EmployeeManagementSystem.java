package employeeManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EmployeeManagementSystem {
    static Map<String, String> users = new HashMap<>(); // User database
    static Map<String, String[]> employeeData = new HashMap<>(); // Stores employee input

    static {
        users.put("admin", "password123");
        users.put("employee", "password456");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}

class LoginScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Employee Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (EmployeeManagementSystem.users.containsKey(username) &&
                EmployeeManagementSystem.users.get(username).equals(password)) {
                dispose();
                if (username.equals("admin")) {
                    new EmployeeProfile(username);
                } else {
                    new ViewSavedEmployeeDetails();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class EmployeeProfile extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField, emailField, addressField, deductionsField, taxField, overtimeField,
                       attendanceField, payDateField, paySlipsField, salaryField;
    private boolean isAdmin;

    public EmployeeProfile(String username) {
        this.isAdmin = username.equals("admin");

        setTitle("Employee Profile");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(13, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Deductions:"));
        deductionsField = new JTextField();
        panel.add(deductionsField);

        panel.add(new JLabel("Tax:"));
        taxField = new JTextField();
        panel.add(taxField);

        panel.add(new JLabel("Overtime:"));
        overtimeField = new JTextField();
        panel.add(overtimeField);

        panel.add(new JLabel("Attendance:"));
        attendanceField = new JTextField();
        panel.add(attendanceField);

        panel.add(new JLabel("Pay Date:"));
        payDateField = new JTextField();
        panel.add(payDateField);

        panel.add(new JLabel("Pay Slips:"));
        paySlipsField = new JTextField();
        panel.add(paySlipsField);

        panel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        panel.add(salaryField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveEmployeeData());

        JButton viewButton = new JButton("View Saved Data");
        viewButton.addActionListener(e -> viewSavedEmployeeDetails());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        if (isAdmin) {
            panel.add(saveButton);
        } else {
        	new ViewSavedEmployeeDetails(); // Employee cannot edit
        }

        panel.add(viewButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    private void saveEmployeeData() {
        if (!isAdmin) return;

        String[] employeeDetails = {
                nameField.getText(), emailField.getText(), addressField.getText(),
                deductionsField.getText(), taxField.getText(), overtimeField.getText(),
                attendanceField.getText(), payDateField.getText(), paySlipsField.getText(),
                salaryField.getText()
        };
        
        for (String detail : employeeDetails) {
            if (detail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled before saving!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        EmployeeManagementSystem.employeeData.put("admin", employeeDetails);
        JOptionPane.showMessageDialog(this, "Employee details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewSavedEmployeeDetails() {
    	 setTitle("Saved Employee Details");
         setSize(500, 500);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
        String[] details = EmployeeManagementSystem.employeeData.get("admin"); // Show admin's saved data
        if (details == null) {
            JOptionPane.showMessageDialog(this, "No data found. Please save first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder message = new StringBuilder("Employee Details:\n");
        message.append("Name: ").append(details[0]).append("\n");
        message.append("Email: ").append(details[1]).append("\n");
        message.append("Address: ").append(details[2]).append("\n");
        message.append("Deductions: ").append(details[3]).append("\n");
        message.append("Tax: ").append(details[4]).append("\n");
        message.append("Overtime: ").append(details[5]).append("\n");
        message.append("Attendance: ").append(details[6]).append("\n");
        message.append("Pay Date: ").append(details[7]).append("\n");
        message.append("Pay Slips: ").append(details[8]).append("\n");
        message.append("Salary: ").append(details[9]).append("\n");

        JOptionPane.showMessageDialog(this, message.toString(), "Saved Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginScreen();
        }
    }
}
