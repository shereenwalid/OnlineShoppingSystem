package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import User.*;

public class RegisterPage extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    private JTextField cardNumberField;
    private JTextField cvvField;
    private JComboBox<String> roleComboBox;

    public RegisterPage() {
        setTitle("Register Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(10, 20, 80, 25);
        panel.add(firstNameLabel);

        firstNameField = new JTextField(20);
        firstNameField.setBounds(100, 20, 165, 25);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(10, 50, 80, 25);
        panel.add(lastNameLabel);

        lastNameField = new JTextField(20);
        lastNameField.setBounds(100, 50, 165, 25);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 80, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 80, 165, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 110, 165, 25);
        panel.add(passwordField);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(10, 140, 80, 25);
        panel.add(cardNumberLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setBounds(100, 140, 165, 25);
        panel.add(cardNumberField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(10, 170, 80, 25);
        panel.add(cvvLabel);

        cvvField = new JTextField(20);
        cvvField.setBounds(100, 170, 165, 25);
        panel.add(cvvField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 200, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Buyer"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(100, 200, 165, 25);
        panel.add(roleComboBox);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 230, 120, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String cardNumber = cardNumberField.getText();
                String cvv = cvvField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                ProxyUser proxyUser = new ProxyUser();
                proxyUser.BuyerRegister(firstName, lastName, cardNumber, email, password, role, cardNumber, cvv);
            }
        });
    }
}
