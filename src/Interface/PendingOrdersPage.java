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


        this.userOrders = user.displayPendingOrders();
        displayPendingOrders();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBackToBuyerMainPage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void displayPendingOrders() {
        JPanel ordersPanel = new JPanel(new GridLayout(0, 1));

        for (Map.Entry<Integer, Map<Integer, String>> entry : userOrders.entrySet()) {
            int orderID = entry.getKey();
            Map<Integer, String> itemMap = entry.getValue();

            JPanel orderPanel = new JPanel(new BorderLayout());

            JLabel orderLabel = new JLabel("Order ID: " + orderID);
            orderPanel.add(orderLabel, BorderLayout.NORTH);

            JPanel itemPanel = new JPanel(new GridLayout(0, 1));

            for (Map.Entry<Integer, String> itemEntry : itemMap.entrySet()) {
                int itemID = itemEntry.getKey();
                String itemName = itemEntry.getValue();

                JPanel itemRowPanel = new JPanel(new BorderLayout());

                JLabel itemLabel = new JLabel("   Item ID: " + itemID + ", Item Name: " + itemName);
                itemRowPanel.add(itemLabel, BorderLayout.WEST);

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

            orderPanel.add(itemPanel, BorderLayout.CENTER);

            JButton cancelButton = new JButton("Cancel Order");
            cancelButton.addActionListener(e -> {
                try {
                    facade.cancelOrder(orderID);
                    updatePage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            orderPanel.add(cancelButton, BorderLayout.SOUTH);

            ordersPanel.add(orderPanel);

            ordersPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

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
