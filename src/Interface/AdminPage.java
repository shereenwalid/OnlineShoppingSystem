// AdminPage.java
package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AdminPage extends JFrame {
    private Admin admin;

    public AdminPage(Admin admin) {
        this.admin = admin;
        initComponents();
        setVisible(true);
        setSize(800, 600);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard");

        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCategoryButtonClick();
            }
        });

        JButton deleteCategoryButton = new JButton("Delete Category");
        deleteCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteCategoryButtonClick();
            }
        });

        JButton userManagementButton = new JButton("User Management");
        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserManagementButtonClick();
            }
        });

        JButton itemManagementButton = new JButton("Item Management");
        itemManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleItemManagementButtonClick();
            }
        });

        JButton orderPageButton = new JButton("Order Page");
        orderPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrderPage();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
                        .addComponent(deleteCategoryButton)
                        .addComponent(userManagementButton)
                        .addComponent(itemManagementButton)
                        .addComponent(orderPageButton) // Added Order Page button
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
                        .addComponent(deleteCategoryButton)
                        .addComponent(userManagementButton)
                        .addComponent(itemManagementButton)
                        .addComponent(orderPageButton) // Added Order Page button
        );

        pack();
    }

    private void handleItemManagementButtonClick() {
        DashboardItem dashboardItem = new DashboardItem(admin);
        setVisible(false);
        dashboardItem.setVisible(true);
    }

    private void handleAddCategoryButtonClick() {
        String categoryName = JOptionPane.showInputDialog(this, "Enter Category Name:");
        if (categoryName != null && !categoryName.isEmpty()) {
            admin.addCategory(categoryName);
            JOptionPane.showMessageDialog(this, "Category '" + categoryName + "' added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty.");
        }
    }

    private void handleDeleteCategoryButtonClick() {
        String categoryToDelete = JOptionPane.showInputDialog(this, "Enter Category Name to delete:");
        if (categoryToDelete != null && !categoryToDelete.isEmpty()) {
            admin.deleteCategory(categoryToDelete);
            JOptionPane.showMessageDialog(this, "Category '" + categoryToDelete + "' deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty.");
        }
    }

    private void handleUserManagementButtonClick() {
        String usernameToDelete = JOptionPane.showInputDialog(this, "Enter username to delete:");
        if (usernameToDelete != null && !usernameToDelete.isEmpty()) {
            admin.deleteUser(usernameToDelete);
            JOptionPane.showMessageDialog(this, "User '" + usernameToDelete + "' deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.");
        }
    }

    private void openOrderPage() {
        OrderPage orderPage = new OrderPage(admin);
        setVisible(false);
        orderPage.setVisible(true);
    }
}
