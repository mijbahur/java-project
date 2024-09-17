import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Registration implements ActionListener {
    private JFrame frame;
    private JTextField nameField, userNameField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox;

    public Registration() {
        frame = new JFrame("Registration");
        frame.setSize(600, 600);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        frame.add(nameField);

        JLabel userNameLabel = new JLabel("User Name:");
        userNameLabel.setBounds(50, 100, 100, 30);
        frame.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setBounds(150, 100, 200, 30);
        frame.add(userNameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        frame.add(passwordField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 200, 100, 30);
        frame.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(150, 200, 200, 30);
        frame.add(genderBox);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 250, 100, 30);
        registerButton.addActionListener(this);
        frame.add(registerButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(310, 250, 100, 30);
        clearButton.addActionListener(this);
        frame.add(clearButton);

        JLabel loginLabel = new JLabel("Already Registered? Login");
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setBounds(200, 300, 200, 30);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new Login();
            }
        });
        frame.add(loginLabel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Register")) {
                register();
            } else if (button.getText().equals("Clear")) {
                clearFields();
            }
        }
    }

    private void register() {
        String name = nameField.getText();
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        String gender = (String) genderBox.getSelectedItem();

        // Check for empty fields
        if (name.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        // Check for existing userName
        if (isUserNameExists(userName)) {
            JOptionPane.showMessageDialog(frame, "User with this userName already exists!");
            return;
        }

        User newUser = new User(name, userName, password, gender);

        try {
            FileWriter writer = new FileWriter("userdata.txt", true);
            writer.write(newUser.getName() + "," + newUser.getUserName() + "," + newUser.getPassword() + "," + newUser.getGender() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(frame, "Registration Successful!");
            frame.dispose();
            new Login();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isUserNameExists(String userName) {
        try {
            File file = new File("userdata.txt");
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(userName)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void clearFields() {
        nameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        genderBox.setSelectedIndex(0);
    }
}
