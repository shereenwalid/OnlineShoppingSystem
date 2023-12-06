package Interface;

import Factory.Item.A_Item;
import Factory.Registry.I_ItemRegistry;
import Factory.RegistryFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ItemsDisplayPage extends JFrame {

    private CategorySelectionPage categorySelectionPage;
    private I_ItemRegistry registry;
    private JPanel itemsPanel;

    public ItemsDisplayPage(CategorySelectionPage categorySelectionPage) {
        this.categorySelectionPage = categorySelectionPage;
        itemsPanel = new JPanel(new GridBagLayout());
        itemsPanel.setBackground(Color.white);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);

        // Add a navbar with the application name
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.black);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.white);
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

//        Set up a scrollable panel to display items.
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        add(backButton, BorderLayout.SOUTH);
        setTitle("Items Display");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        To center the JFrame in the middle of the screen, both horizontally and vertically
        setLocationRelativeTo(null);
    }

    public void updateRegistry(String selectedCategory) {
        RegistryFactory registryFactory = new RegistryFactory();
        registry = registryFactory.getCategoryRegistry(selectedCategory);
        displayItems();
    }

    private void displayItems() {
        // Clear existing items
        itemsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        // space around the component.
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a set out of the same elements contained in the hash map
        for (Map.Entry<String, A_Item> entry : registry.getItems().entrySet()) {
            A_Item item = entry.getValue();

            // Construct the dynamic image path based on the item's name
            String imagePath = "images/" + item.getItemName() + ".jpeg";

            // Create an ImageIcon to display the image
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create a panel for each item
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.white);

            // Add components to the item panel
            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(Color.white);

            JLabel imageLabel = new JLabel(scaledIcon);
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showItemDetails(item);
                }
            });

            imagePanel.add(imageLabel);
            itemPanel.add(imagePanel, BorderLayout.NORTH);

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBackground(Color.white);

            // To render that only 20 characters per line will be displayed
            JLabel nameLabel = new JLabel("<html><b>" + wrapText(item.getItemName(), 20) + "</b></html>");
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            detailsPanel.add(nameLabel);

            JLabel priceLabel = new JLabel("<html><b>$" + item.getPrice() + "</b></html>");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            detailsPanel.add(priceLabel);

            // To render that only 40 characters per line will be displayed
            JLabel descriptionLabel = new JLabel("<html>" + wrapText(item.getDescription(), 40) + "</html>");
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            detailsPanel.add(descriptionLabel);

            // Add space between the image and the item information
            detailsPanel.add(Box.createVerticalStrut(10));

            JButton addToCartButton = new JButton("Add to Cart");
            detailsPanel.add(addToCartButton);

            itemPanel.add(detailsPanel, BorderLayout.CENTER);

            // Add the item panel to the items panel
            itemsPanel.add(itemPanel, gbc);

            // Increment gridy for the next row
            gbc.gridy++;

            // Add space between items
            itemsPanel.add(Box.createVerticalStrut(20), gbc);
            gbc.gridy++;

            // Add a separator line between items
            itemsPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
            gbc.gridy++;
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    /**
     * Wraps the given text into an HTML-formatted string, breaking the lines at the specified number of characters per line.
     *
     * @param text              The text to be wrapped.
     * @param charactersPerLine The maximum number of characters per line.
     * @return The HTML-formatted string with line breaks inserted at the specified number of characters per line.
     */
    private String wrapText(String text, int charactersPerLine) {
        // We'll add the html tags also in the function to ensure that
        // the returned string is properly formatted & can be used independently
        StringBuilder wrappedText = new StringBuilder("<html>");
        // Split the text into individual words using whitespace as the delimiter
        String[] words = text.split("\\s+");
        int count = 0;

        for (String word : words) {
            if (count + word.length() > charactersPerLine) {
                wrappedText.append("<br>");
                count = 0;
            }

            wrappedText.append(word).append(" ");
            // Increment the character count by the length of the word, add 1 for the space that follows
            count += word.length() + 1;
        }

        wrappedText.append("</html>");
        return wrappedText.toString();
    }

    private void goBack() {
        setVisible(false);
        categorySelectionPage.setVisible(true);
    }

    private void showItemDetails(A_Item item) {
        ItemDetailsPage itemDetailsPage = new ItemDetailsPage(this, item);
        setVisible(false);
        itemDetailsPage.setVisible(true);
    }
}
