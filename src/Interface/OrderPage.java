package Interface;

import OrderFacade.Order;
import User.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class OrderPage extends JFrame {
    private Admin admin;
    private DefaultTableModel orderTableModel;
    private JTable orderTable;
    private JTextArea orderDetailsArea;
    private JTextField startDateField;
    private JTextField endDateField;

    public OrderPage(Admin admin) {
        this.admin = admin;
        initComponents();
        setSize(800, 600);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Order Page");

        orderTableModel = new DefaultTableModel();
        orderTableModel.addColumn("Order ID");
        orderTableModel.addColumn("User ID");
        orderTableModel.addColumn("Total");

        orderTable = new JTable(orderTableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.getSelectionModel().addListSelectionListener(e -> handleOrderSelection());

        JScrollPane tableScrollPane = new JScrollPane(orderTable);

        orderDetailsArea = new JTextArea(5, 30);
        orderDetailsArea.setEditable(false);

        startDateField = new JTextField(10);
        endDateField = new JTextField(10);

        JButton showOrdersButton = new JButton("Show Orders");
        showOrdersButton.addActionListener(e -> handleShowOrdersButtonClick());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBackToAdminPage());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date:"));
        inputPanel.add(endDateField);
        inputPanel.add(showOrdersButton);
        inputPanel.add(backButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(orderDetailsArea, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleShowOrdersButtonClick() {
        String startDateText = startDateField.getText();
        String endDateText = endDateField.getText();

        if (isValidDate(startDateText) && isValidDate(endDateText)) {
            LocalDate startDate = LocalDate.parse(startDateText);
            LocalDate endDate = LocalDate.parse(endDateText);

            refreshOrderList(startDate, endDate);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private boolean isValidDate(String dateText) {
        try {
            LocalDate.parse(dateText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void refreshOrderList(LocalDate startDate, LocalDate endDate) {
        orderTableModel.setRowCount(0);
        orderDetailsArea.setText("");

        List<Order> orders = admin.showOrderWithInPeriod(startDate, endDate);
        for (Order order : orders) {
            Object[] rowData = {order.getOrderID(), order.getUserID(), order.getTotal()};
            orderTableModel.addRow(rowData);
        }
    }

    private void handleOrderSelection() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String orderID = orderTable.getValueAt(selectedRow, 0).toString();
            String userID = orderTable.getValueAt(selectedRow, 1).toString();
            String total = orderTable.getValueAt(selectedRow, 2).toString();

            String orderDetails = String.format("Order ID: %s\nUser ID: %s\nTotal: %s", orderID, userID, total);
            orderDetailsArea.setText(orderDetails);
        }
    }

    private void goBackToAdminPage() {
        AdminPage adminPage = new AdminPage(admin);
        adminPage.setVisible(true);
        dispose();
    }


}
