// AddItemPage.java
package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddItemPage extends JFrame {
    private Admin admin;

    public AddItemPage(Admin admin) {
        this.admin = admin;
        initComponents();
        setVisible(true);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Add Item");

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Item Name:"));
        JTextField itemNameField = new JTextField();
        panel.add(itemNameField);

        panel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Sale Percent:"));
        JTextField salePercentField = new JTextField();
        panel.add(salePercentField);

        panel.add(new JLabel("Category:"));
        JTextField CategoryField = new JTextField();
        panel.add(CategoryField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                int price = Integer.parseInt(priceField.getText());
                float salePercent = Float.parseFloat(salePercentField.getText());
                int categoryId = Integer.parseInt(CategoryField.getText());

                admin.addItem(itemName, price, "", 0, salePercent, categoryId);

                // Close the current page after saving
                dispose();
            }
        });

        panel.add(saveButton);

        add(panel);
    }
}
