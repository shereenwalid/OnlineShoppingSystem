// DashboardItem.java
package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class DashboardItem extends JFrame {
    private Admin admin;
    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;

    public DashboardItem(Admin admin) {
        this.admin = admin;
        initComponents();
        setSize(800, 600);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Item Management");

        itemListModel = new DefaultListModel<String>();
        itemList = new JList<>(itemListModel);
        refreshItemList();

        JScrollPane scrollPane = new JScrollPane(itemList);
        add(scrollPane, BorderLayout.CENTER);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddItemButtonClick();
            }
        });

        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteItemButtonClick();
            }
        });

        JButton editItemButton = new JButton("Edit Item");
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditItemButtonClick();
            }
        });

        JButton showOrdersButton = new JButton("Show Orders");
        showOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleShowOrdersButtonClick();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToAdminPage();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(deleteItemButton);
        buttonPanel.add(editItemButton);
        buttonPanel.add(showOrdersButton); // Add the show orders button
        buttonPanel.add(backButton); // Add the back button

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleShowOrdersButtonClick() {
        // Create and show the order page
        new OrderPage(admin);
    }

    private void goBackToAdminPage() {
        AdminPage adminPage = new AdminPage(admin);

        // Set the visibility of DashboardItem to false
        setVisible(false);

        // Make AdminPage visible
        adminPage.setVisible(true);
    }

    private void handleAddItemButtonClick() {
        new AddItemPage(admin);
        refreshItemList();
    }

    private void handleDeleteItemButtonClick() {
        String selectedItemName = itemList.getSelectedValue();
        if (selectedItemName != null) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Perform deletion logic using the selected item name
                admin.deleteItem(selectedItemName);
                refreshItemList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void handleEditItemButtonClick() {
        String selectedItem = itemList.getSelectedValue();
        if (selectedItem != null) {
            ItemDialog editItemDialog = new ItemDialog(this, "Edit Item", true, admin);
            editItemDialog.setCurrentItem(itemList.getSelectedValue());
            editItemDialog.setVisible(true);
            refreshItemList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.");
        }
    }

    private void refreshItemList() {
        itemListModel.clear();
        List<String> items = admin.getAllItems();
        for (String item : items) {
            itemListModel.addElement(item);
        }
    }
}
