package Interface;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

import OrderFacade.OrderFacade;
import User.Buyer;

public class PendingOrdersPage extends JFrame {

    private Buyer user;
    OrderFacade facade = new OrderFacade();
    Map<Integer, Map<Integer, String>> userOrders;

    public PendingOrdersPage(Buyer user) {
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.white);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(new Color(0x333333));
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        this.user = user;
        this.user.setOrder(facade);
        user.getOrder().setBuyer(user);
        this.facade = user.getOrder();

        setTitle("Pending Orders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Call display pending orders
        this.userOrders = user.displayPendingOrders();
        displayPendingOrders();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBackToBuyerMainPage());

        // Put back button at the south of the page
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void displayPendingOrders() {
        JPanel ordersPanel = new JPanel(new GridLayout(0, 1));

        for (Map.Entry<Integer, Map<Integer, String>> entry : userOrders.entrySet()) {
            int orderID = entry.getKey();
            Map<Integer, String> itemMap = entry.getValue();

            // Create a panel for each order
            JPanel orderPanel = new JPanel(new BorderLayout());

            // Create a label for the order ID and add it to the order panel
            JLabel orderLabel = new JLabel("Order ID: " + orderID);
            orderPanel.add(orderLabel, BorderLayout.NORTH);

            // Create a panel for the items
            JPanel itemPanel = new JPanel(new GridLayout(0, 1));

            for (Map.Entry<Integer, String> itemEntry : itemMap.entrySet()) {
                int itemID = itemEntry.getKey();
                String itemName = itemEntry.getValue();

                // Create a panel for each item with a cancel button
                JPanel itemRowPanel = new JPanel(new BorderLayout());

                JLabel itemLabel = new JLabel("   Item ID: " + itemID + ", Item Name: " + itemName);
                itemRowPanel.add(itemLabel, BorderLayout.WEST);

                // Create a cancel button for the item
                JButton cancelButton = new JButton("Cancel Item");
                cancelButton.addActionListener(e -> {
                    try {
                        facade.cancelOrderItem(orderID, itemID);
                        updatePage();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                itemRowPanel.add(cancelButton, BorderLayout.EAST);

                itemPanel.add(itemRowPanel);
            }

            // Add the item panel to the order panel
            orderPanel.add(itemPanel, BorderLayout.CENTER);

            // Create a cancel button for the order
            JButton cancelButton = new JButton("Cancel Order");
            cancelButton.addActionListener(e -> {
                try {
                    facade.cancelOrder(orderID);
                    updatePage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Add the cancel button to the order panel
            orderPanel.add(cancelButton, BorderLayout.SOUTH);

            // Add the order panel to the orders panel
            ordersPanel.add(orderPanel);

            // Add a separator between orders
            ordersPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        // Use a scroll pane in case there are many orders
        JScrollPane scrollPane = new JScrollPane(ordersPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updatePage() {
        getContentPane().removeAll();
        PendingOrdersPage updatedPendingOrdersPage = new PendingOrdersPage(user);
        updatedPendingOrdersPage.setVisible(true);
        dispose();
    }

    private void goBackToBuyerMainPage() {
        new BuyerMainPage(user).setVisible(true);
        dispose();
    }


}
