package Interface;

import User.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemDialog extends JDialog {
    private JTextField priceField;
    private JTextField salePercentField;

    private String currentItem;
    private Admin admin;

    public ItemDialog(JFrame parent, String title, boolean modal,Admin admin) {
        this.admin = admin;
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Sale Percent:"));
        salePercentField = new JTextField();
        add(salePercentField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveButtonClick();
            }
        });
        add(saveButton);
    }

    private void handleSaveButtonClick() {
        // Extract data from the input fields
        int price = Integer.parseInt(priceField.getText());
        float salePercent = Float.parseFloat(salePercentField.getText());

        // Call the corresponding methods in the Admin class
        if (currentItem != null) {
            admin.EditItemSalePercent(currentItem, (int) salePercent);
            admin.EditItemPrice(currentItem, price);
        }

        // Close the dialog after saving
        setVisible(false);
    }

    public void setCurrentItem(String currentItem) {
        this.currentItem = currentItem;
    }
}
