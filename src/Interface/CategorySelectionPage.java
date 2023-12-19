package Interface;

import Factory.Registry.I_ItemRegistry;
import Factory.RegistryFactory;
import User.Buyer;
import User.IUser;
import User.ProxyUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategorySelectionPage extends JFrame {

    private ItemsDisplayPage itemsDisplayPage;
    private String selectedCategory;
    Buyer user;

    public CategorySelectionPage(Buyer user) {
        this.user = user;

        getContentPane().setBackground(new Color(0x333333));

        setLayout(new BorderLayout());

        JPanel navbar = new JPanel();
        navbar.setBackground(Color.white);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(new Color(0x333333));
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton booksButton = new JButton("Books");
        JButton electronicsButton = new JButton("Electronics");

        booksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCategory = "Books";
                switchToItemsDisplayPage();
            }
        });

        electronicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCategory = "Electronics";
                switchToItemsDisplayPage();
            }
        });

        buttonPanel.add(booksButton);
        buttonPanel.add(Box.createHorizontalStrut(150));
        buttonPanel.add(electronicsButton);

        add(buttonPanel, BorderLayout.CENTER);

        // South Panel for the Back Button
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToBuyerMainPage();
            }
        });
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);


        setTitle("Category Selection");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);



        setTitle("Category Selection");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void goBackToBuyerMainPage() {
        new BuyerMainPage(user).setVisible(true);
        dispose();
    }

    private void switchToItemsDisplayPage() {
        if (itemsDisplayPage == null) {
            itemsDisplayPage = new ItemsDisplayPage(this.user);
            itemsDisplayPage.setCategorySelectionPage(this);
        }

        itemsDisplayPage.updateRegistry(selectedCategory);

        setVisible(false);
        itemsDisplayPage.setVisible(true);
    }
}