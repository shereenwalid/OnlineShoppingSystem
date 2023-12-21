package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import User.*;

public class ProfilePage extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField cardNumberField;
    private JTextField cvvField;
    Buyer buyer;
    Creditcard creditcard;

    public ProfilePage(Buyer buyer) {
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.white);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(new Color(0x333333));
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        this.buyer = buyer;
        creditcard = buyer.getCard();
        setTitle("Profile Page");
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
        cardNumberLabel.setBounds(10, 140, 100, 25);
        panel.add(cardNumberLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setBounds(120, 140, 165, 25);
        panel.add(cardNumberField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(10, 170, 80, 25);
        panel.add(cvvLabel);

        cvvField = new JTextField(20);
        cvvField.setBounds(100, 170, 165, 25);
        panel.add(cvvField);

        JButton updateButton = new JButton("Update Profile");
        updateButton.setBounds(10, 260, 140, 25);
        panel.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToBuyerMainPage();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                int cardNumber = 0;
                int cvv = 0;

                try {
                    if (!cardNumberField.getText().isEmpty()) {
                        cardNumber = Integer.parseInt(cardNumberField.getText());
                    }

                    if (!cvvField.getText().isEmpty()) {
                        cvv = Integer.parseInt(cvvField.getText());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Card number and CVV must be valid integers.");
                    return;
                }

                buyer.setFname(firstName, buyer.getUserID());
                buyer.setLname(lastName, buyer.getUserID());
                buyer.setemail(email, buyer.getUserID());
                buyer.setpassword(password, buyer.getUserID());
                creditcard.setcardNumber(cardNumber, buyer.getUserID());
                creditcard.setcvv(cvv, buyer.getUserID());

                JOptionPane.showMessageDialog(null, "Profile updated successfully!");
            }
        });
    }

    private void goBackToBuyerMainPage() {
        new BuyerMainPage(buyer).setVisible(true);
        dispose();
    }


}
