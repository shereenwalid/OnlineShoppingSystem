package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardCategory extends JFrame{

    private Admin admin;


    public DashboardCategory(Admin admin) {
        this.admin = admin;
        initComponents();
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

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
                        .addComponent(deleteCategoryButton)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
                        .addComponent(deleteCategoryButton)
        );

        pack();

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

}
