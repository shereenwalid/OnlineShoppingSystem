package Interface;

import Factory.Item.A_Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemDetailsPage extends JFrame {
    private ItemsDisplayPage itemsDisplayPage;
    private A_Item item;

    public ItemDetailsPage(ItemsDisplayPage itemsDisplayPage, A_Item item) {
        // Set the background color first
        getContentPane().setBackground(Color.white);

        this.itemsDisplayPage = itemsDisplayPage;
        this.item = item;

        // Add a navbar with the application name
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.black);
        navbar.setPreferredSize(new Dimension(600, 50));

        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.white);

        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        // Create a panel to display item details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBackground(Color.white);

        // Create a panel for the image on the left
        JPanel imagePanel = new JPanel();

        // Construct the dynamic image path based on the item's name
        String imagePath = "images/" + item.getItemName() + ".jpeg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        imagePanel.add(new JLabel(scaledIcon));
        detailsPanel.add(imagePanel, BorderLayout.WEST);

        // Create a panel for item information on the right
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.white); // Set the background color to white

        JLabel nameLabel = new JLabel("" + item.getItemName() + "");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        infoPanel.add(nameLabel);

        JLabel priceLabel = new JLabel("$" + item.getPrice() + "");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        infoPanel.add(priceLabel);

        JLabel descriptionLabel = new JLabel("" + item.getDescription() + "");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        infoPanel.add(descriptionLabel);

        detailsPanel.add(infoPanel, BorderLayout.CENTER);
        detailsPanel.setBackground(Color.white);

        // Create a text area for feedback
        JTextArea feedbackTextArea = new JTextArea();
        feedbackTextArea.setEditable(true);
        feedbackTextArea.setRows(10);
        feedbackTextArea.setColumns(20);
        feedbackTextArea.setBorder(BorderFactory.createTitledBorder("Leave Feedback"));

        // Create buttons for navigation and feedback submission
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JButton submitFeedbackButton = new JButton("Submit Feedback");
        submitFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback(feedbackTextArea.getText());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(submitFeedbackButton);

        // Create a panel for the text box and text area
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(new BorderLayout());
        textBoxPanel.setBackground(Color.white);
        textBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textBoxPanel.add(feedbackTextArea, BorderLayout.CENTER);

        // Create a panel to hold the text box panel and button panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(textBoxPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to the frame
        add(detailsPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.SOUTH);

        setTitle("Item Details");
        setSize(800, 600); // Adjusted size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void goBack() {
        setVisible(false);
        itemsDisplayPage.setVisible(true);
    }

    private void submitFeedback(String feedback) {
        // Implement feedback submission logic here
        System.out.println("Feedback submitted: " + feedback);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // For testing purposes
                new ItemDetailsPage(null, null).setVisible(true);
            }
        });
    }
}
