// DisplayCartPage.java
package Interface;

import Factory.Item.A_Item;
import OrderFacade.OrderFacade;
import OrderFacade.*;
import User.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayCartPage extends JFrame {
    private ShoppingCart shoppingCart;
    private OrderFacade orderFacade;
    private JTextField cardNumberField;
    private JPasswordField cvvField;
    ShoppingCart cart;
    Buyer user;

    public DisplayCartPage(Buyer user, ShoppingCart cart) {
        this.user = user;
        this.shoppingCart = cart;
        this.orderFacade = new OrderFacade(user);
        Order order = new Order();

        // set order facade for user
        this.user.setOrder(orderFacade);

        // set order for order facade
        this.user.getOrder().setOrder(order);

        // set cart for order
        this.user.getOrder().getOrder().setCart(shoppingCart);

        this.user.getOrder().getPayments().setOrder(order);

        this.user.getOrder().getPayments().setUser(this.user);


        JPanel navbar = createNavbar();
        JPanel cartItemsPanel = createCartItemsPanel();
        JPanel paymentPanel = createPaymentPanel();
        JButton submitOrderButton = createSubmitOrderButton();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(cartItemsPanel, BorderLayout.CENTER);
        mainPanel.add(paymentPanel, BorderLayout.SOUTH);
        mainPanel.add(submitOrderButton, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Shopping Cart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.BLACK);
        navbar.setPreferredSize(new Dimension(800, 50));

        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.WHITE);

        navbar.add(appNameLabel);
        return navbar;
    }

    private JPanel createCartItemsPanel() {
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new GridLayout(0, 1));
        cartItemsPanel.setBackground(Color.WHITE);

        for (A_Item item : shoppingCart.getItems()) {
            JPanel itemPanel = createItemPanel(item);
            cartItemsPanel.add(itemPanel);
        }

        return cartItemsPanel;
    }

    private JPanel createItemPanel(A_Item item) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(Color.WHITE);

        JLabel itemNameLabel = new JLabel("<html><b>" + item.getItemName() + "</b></html>");
        itemNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel priceLabel = new JLabel("<html><b>$" + item.getPrice() + "</b></html>");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // Display the image
        String imagePath = "images/" + item.getItemName() + ".jpeg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        itemPanel.add(imageLabel, BorderLayout.WEST);

        itemPanel.add(itemNameLabel, BorderLayout.CENTER);
        itemPanel.add(priceLabel, BorderLayout.EAST);

        return itemPanel;
    }

    private JPanel createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new GridLayout(2, 2));
        paymentPanel.setBackground(Color.WHITE);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField();

        paymentPanel.add(cardNumberLabel);
        paymentPanel.add(cardNumberField);
        paymentPanel.add(cvvLabel);
        paymentPanel.add(cvvField);

        return paymentPanel;
    }

    private JButton createSubmitOrderButton() {
        JButton submitOrderButton = new JButton("Submit Order");
        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitOrder();
            }
        });
        submitOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return submitOrderButton;
    }

    private void submitOrder() {
        // Prompt the user to enter CVV and Card Number
        String cardNumber = JOptionPane.showInputDialog(this, "Enter Card Number:");
        String cvv = JOptionPane.showInputDialog(this, "Enter CVV:");

        // Check if the user canceled the input
        if (cardNumber == null || cvv == null) {
            return;
        }

        // Use the OrderFacade to submit the order
        orderFacade.placeOrder(cvv, cardNumber);

        // Display a thank you and confirmation message
        JOptionPane.showMessageDialog(this, "Thank you for your order! Your order has been submitted successfully.",
                "Order Submitted", JOptionPane.INFORMATION_MESSAGE);

        // Clear the shopping cart after submitting the order
        shoppingCart.clearCart();

        // Take the user back to the category selection page
        CategorySelectionPage categorySelectionPage = new CategorySelectionPage(user);
        categorySelectionPage.setVisible(true);

        // Close the current window
        setVisible(false);
        dispose();
    }
}
