import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Login implements ActionListener {
    private JFrame frame;
    private JTextField userNameField;
    private JPasswordField passwordField;
    public JLabel userNameLabel, passwordMessageLabel;


public void Message(String s)
{

    JLabel userMessageLabel = new JLabel();
    userMessageLabel.setForeground(Color.RED);
    userMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    userMessageLabel.setBounds(150, 75, 250, 30);
    frame.add(userMessageLabel);
    userMessageLabel.setText(s);//("The user field is required.");

    userNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
}

    public Login() {
        frame = new JFrame("Login");
        frame.setSize(600, 600);
        frame.setLayout(null);


//        JLabel passwordMessageLabel = new JLabel("The password field is required.");
//        passwordMessageLabel.setForeground(Color.RED);
//        passwordMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//        passwordMessageLabel.setBounds(150, 135, 250, 30);
//        frame.add(passwordMessageLabel);

        JLabel userNameLabel = new JLabel("User Name:");
        userNameLabel.setBounds(50, 50, 100, 30);
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(userNameLabel);


        userNameField = new JTextField();
        userNameField.setBounds(150, 50, 200, 30);
        userNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(userNameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 160, 100, 30);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.addActionListener(this);
        frame.add(loginButton);

//        JButton registerButton = new JButton("Register");
//        registerButton.setBounds(200, 200, 100, 30);
//        registerButton.addActionListener(this);
//        frame.add(registerButton);

        JLabel registrationMsgLabel = new JLabel("Don't have an account?");
        registrationMsgLabel.setBounds(160, 200, 200, 30);
        registrationMsgLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(registrationMsgLabel);

        JLabel registrationLabel = new JLabel("Register");
        registrationLabel.setForeground(Color.BLUE);
        registrationLabel.setBounds(360, 200, 200, 30);
        registrationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        registrationLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registrationLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new Registration();
            }
        });
        frame.add(registrationLabel);

//        Error Message

//        JLabel userMessageLabel = new JLabel();
//        userMessageLabel.setForeground(Color.RED);
//        userMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//        userMessageLabel.setBounds(50, 250, 250, 30);
//        frame.add(userMessageLabel);



//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Login")) {
                login();
            } else if (button.getText().equals("Register")) {
                frame.dispose();
                new Registration();
            }
        }
    }

    private void login() {
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        User user = null;

        try {


//            JLabel userMessageLabel = new JLabel();
//            userMessageLabel.setForeground(Color.RED);
//            userMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//            userMessageLabel.setBounds(150, 75, 250, 30);
//            frame.add(userMessageLabel);
//
            JLabel passwordMessageLabel = new JLabel();
            passwordMessageLabel.setForeground(Color.RED);
            passwordMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordMessageLabel.setBounds(150, 135, 250, 30);
            frame.add(passwordMessageLabel);




            File file = new File("userdata.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No user registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean loggedIn = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(userName) && parts[2].equals(password)) {
                    loggedIn = true;
                    user = new User(parts[0], parts[1], parts[2], parts[3]);
                    break;
                }
            }
            reader.close();

            if (loggedIn) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                new Dashboard(user);
            }


                if (userName.isEmpty()) {
                    Message("The user field is required.");
//                    userMessageLabel.setText("The user field is required.");
//                    userNameField.setBorder(BorderFactory.createLineBorder(Color.RED));

                }

                if (password.isEmpty()) {
                    passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
                    passwordMessageLabel.setText("The password field is required.");
                }


            else {

                    JOptionPane.showMessageDialog(frame, "Invalid userName or password!");
                    Message("                     ");




            }
//                frame.getContentPane().remove(passwordMessageLabel);
//                userMessageLabel.setVisible(false);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
