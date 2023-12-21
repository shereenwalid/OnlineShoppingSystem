package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Dashboard extends JFrame {
    private Admin admin;

    public Dashboard(Admin admin) {
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

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(addCategoryButton)
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

//    public static void main(String[] args) {
//        ADMIN_TRIAL admin = new ADMIN_TRIAL(1, "Admin", "LastName", "123-45-6789", "admin@example.com", "admin123", "Admin");
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Dashboard(admin).setVisible(true);
//            }
//        });
//    }
}
