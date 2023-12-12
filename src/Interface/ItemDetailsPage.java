package Interface;

import Factory.Item.A_Item;
import OrderFacade.ShoppingCart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemDetailsPage extends JFrame {
    private ItemsDisplayPage itemsDisplayPage;
    private A_Item item;
    private ShoppingCart shoppingCart;

    public ItemDetailsPage(ItemsDisplayPage itemsDisplayPage, A_Item item, ShoppingCart shoppingCart) {
        // Your existing constructor code
        this.itemsDisplayPage = itemsDisplayPage;
        this.item = item;
        this.shoppingCart = shoppingCart;

        JPanel navbar = createNavbar();
        JPanel detailsPanel = createDetailsPanel();
        JTextArea feedbackTextArea = createFeedbackTextArea();
        JButton backButton = createBackButton();
        JButton submitFeedbackButton = createSubmitFeedbackButton(feedbackTextArea);
        JButton addToCartButton = createAddToCartButton();

        JPanel contentPanel = createContentPanel(feedbackTextArea, backButton, submitFeedbackButton, addToCartButton);

        add(navbar, BorderLayout.NORTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.SOUTH);

        setTitle("Item Details");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.black);
        navbar.setPreferredSize(new Dimension(600, 50));

        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.white);

        navbar.add(appNameLabel);
        return navbar;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBackground(Color.white);

        JPanel imagePanel = createImagePanel();
        JPanel infoPanel = createInfoPanel();

        detailsPanel.add(imagePanel, BorderLayout.WEST);
        detailsPanel.add(infoPanel, BorderLayout.CENTER);

        return detailsPanel;
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel();

        String imagePath = "images/" + item.getItemName() + ".jpeg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        imagePanel.add(new JLabel(scaledIcon));
        return imagePanel;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.white);

        JLabel nameLabel = new JLabel("<html><b>" + item.getItemName() + "</b></html>");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel priceLabel = new JLabel("<html><b>$" + item.getPrice() + "</b></html>");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel descriptionLabel = new JLabel("<html>" + item.getDescription() + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(descriptionLabel);

        return infoPanel;
    }

    private JTextArea createFeedbackTextArea() {
        JTextArea feedbackTextArea = new JTextArea();
        feedbackTextArea.setEditable(true);
        feedbackTextArea.setRows(10);
        feedbackTextArea.setColumns(20);
        feedbackTextArea.setBorder(BorderFactory.createTitledBorder("Leave Feedback"));
        return feedbackTextArea;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        return backButton;
    }

    private JButton createSubmitFeedbackButton(JTextArea feedbackTextArea) {
        JButton submitFeedbackButton = new JButton("Submit Feedback");
        submitFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback(feedbackTextArea.getText());
            }
        });
        return submitFeedbackButton;
    }

    private JButton createAddToCartButton() {
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        return addToCartButton;
    }

    private JPanel createContentPanel(JTextArea feedbackTextArea, JButton backButton,
                                      JButton submitFeedbackButton, JButton addToCartButton) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(feedbackTextArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(submitFeedbackButton);
        buttonPanel.add(addToCartButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setBackground(Color.white);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return contentPanel;
    }

    private void goBack() {
        setVisible(false);
        itemsDisplayPage.setVisible(true);
    }

    private void submitFeedback(String feedback) {
        System.out.println("Feedback submitted: " + feedback);
        // Implement logic for submitting feedback to the system after finishing buyer class
    }

    private void addToCart() {
        shoppingCart.addItem(item);
        System.out.println("Item added to cart: " + item.getItemName());
    }
}
