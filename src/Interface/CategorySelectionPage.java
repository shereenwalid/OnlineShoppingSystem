package Interface;

import Factory.Registry.I_ItemRegistry;
import Factory.RegistryFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategorySelectionPage extends JFrame {

    private ItemsDisplayPage itemsDisplayPage;
    private String selectedCategory;

    public CategorySelectionPage() {
        // Set the background color first
        getContentPane().setBackground(Color.white);

        // Add a navbar with the application name
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.black);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.white);
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

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

        add(booksButton);
        add(Box.createHorizontalStrut(150)); // Add space between buttons
        add(electronicsButton);

        setTitle("Category Selection");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void switchToItemsDisplayPage() {
        if (itemsDisplayPage == null) {
            itemsDisplayPage = new ItemsDisplayPage(this);
        }

        // Update the registry before showing the page
        itemsDisplayPage.updateRegistry(selectedCategory);

        setVisible(false);
        itemsDisplayPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CategorySelectionPage().setVisible(true);
            }
        });
    }
}