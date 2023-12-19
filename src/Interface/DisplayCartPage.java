// DisplayCartPage.java
package Interface;

import Factory.Item.A_Item;
import OrderFacade.*;
import User.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayCartPage extends JFrame {
    private ShoppingCart shoppingCart;
    private OrderFacade orderFacade;
    private Buyer user;
    private Receipt receipt;
    private Payments payment;
    private JPanel cartItemsPanel;
    private JPanel paymentPanel;
    private ItemsDisplayPage page;

    public ItemsDisplayPage getPage() {
        return page;
    }

    public void setPage(ItemsDisplayPage page) {
        this.page = page;
    }

    public DisplayCartPage(Buyer user) {

        this.user = user;
        this.shoppingCart = user.getCart();
        this.orderFacade = new OrderFacade();
        Order order = new Order();
        receipt = new Receipt();
        payment = new Payments();

        // set order facade for user
        this.user.setOrder(orderFacade);
        this.user.getOrder().setBuyer(user);
        
        // set order for order facade
        this.user.getOrder().setOrder(order);

        this.user.getOrder().setReceipt(receipt);
        this.user.getOrder().getReceipt().setOrder(order);
        this.user.getOrder().getOrder().setCart(shoppingCart);

        this.user.getOrder().setPayments(payment);
        this.user.getOrder().getPayments().setUser(this.user);
        this.user.getOrder().getPayments().setOrder(order);

        // set the order in receipt
        this.user.setReceipt(receipt);
        this.user.getReceipt().setOrder(this.user.getOrder().getOrder());
        this.user.getOrder().setReceipt(this.user.getReceipt());

        JPanel navbar = createNavbar();
        cartItemsPanel = createCartItemsPanel();
        paymentPanel = createPaymentPanel();
        JButton submitOrderButton = createSubmitOrderButton();
        JButton backButton = createBackButton();


        JPanel buttonPanel = new JPanel(new FlowLayout());


        buttonPanel.add(submitOrderButton);
        buttonPanel.add(backButton);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(cartItemsPanel, BorderLayout.CENTER);
        mainPanel.add(paymentPanel, BorderLayout.SOUTH);


        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(mainPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);


        add(southPanel);


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

       
        String imagePath = "images/" + item.getItemName() + ".jpeg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelItem(item);
            }
        });

        itemPanel.add(cancelButton, BorderLayout.SOUTH);
        itemPanel.add(imageLabel, BorderLayout.WEST);
        itemPanel.add(itemNameLabel, BorderLayout.CENTER);
        itemPanel.add(priceLabel, BorderLayout.EAST);

        return itemPanel;
    }

    private JPanel createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new GridLayout(2, 2));
        paymentPanel.setBackground(Color.WHITE);

        JLabel totalLabel = new JLabel("<html><b>Total Price:</b></html>");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel durationLabel = new JLabel("<html><b>Delivery Duration:</b></html>");
        durationLabel.setFont(new Font("Arial", Font.BOLD, 18));

        double finalTotal = orderFacade.displayTotalBeforeSubmitting();
        int duration = orderFacade.displayDurationBeforeSubmitting();

        JLabel totalValueLabel = new JLabel("<html><b>$" + finalTotal + "</b></html>");
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel durationValueLabel = new JLabel("<html><b>" + duration + " days</b></html>");
        durationValueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        paymentPanel.add(totalLabel);
        paymentPanel.add(totalValueLabel);
        paymentPanel.add(durationLabel);
        paymentPanel.add(durationValueLabel);

        return paymentPanel;
    }

    private void cancelItem(A_Item item) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this item?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            shoppingCart.removeItem(item);
            updateCartItemsPanel();
        }
    }

    private void updateCartItemsPanel() {
            // Remove all components 
            getContentPane().removeAll();
            // Recreate the DisplayCartPage with the updated ShoppingCart
            DisplayCartPage updatedCartPage = new DisplayCartPage(user);

            updatedCartPage.setVisible(true);
            dispose();
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

        System.out.println("Entered Card Number: " + cardNumber);
        System.out.println("Entered CVV: " + cvv);

        // Check if the user canceled the input
        if ((orderFacade.placeOrder(cvv, cardNumber, user.getUserID(), (int) user.getOrder().getReceipt().getTotal(), user.getOrder().getOrder().getDeliveryDuration(), user.getOrder().getOrder().getCart()))) {
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
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Card Number or CVV. Please check your input and try again.",
                    "Order Submission Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JButton createBackButton() {
        JButton backButton = new JButton("Back to Items");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToItemsDisplayPage();
            }
        });
        return backButton;
    }


    private void goBackToItemsDisplayPage() {
        // Create a new instance of ItemsDisplayPage and make it visible
        page.setVisible(true);

        // Close the current window
        setVisible(false);
        dispose();
    }
}
