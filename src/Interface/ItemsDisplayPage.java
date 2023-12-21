package Interface;

import Factory.Category.A_Item;
import Factory.Registry.I_ItemRegistry;
import Factory.RegistryFactory;
import OrderFacade.*;
import User.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ItemsDisplayPage extends JFrame {

    private CategorySelectionPage categorySelectionPage;
    private DisplayCartPage cartPage;
    private I_ItemRegistry registry;
    private JPanel itemsPanel;
    private ShoppingCart shoppingCart;
    Buyer user;



    public ItemsDisplayPage(Buyer user) {
        this.user = user;
        this.shoppingCart = new ShoppingCart();
        user.setCart(shoppingCart);
        itemsPanel = new JPanel(new GridBagLayout());
        itemsPanel.setBackground(Color.white);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);

        JPanel navbar = new JPanel();
        navbar.setBackground(Color.black);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(Color.white);
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JButton goToCart = new JButton("Go To Cart");
        goToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToCartPage();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(goToCart);

        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Items Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void updateRegistry(String selectedCategory) {
        RegistryFactory registryFactory = new RegistryFactory();
        registry = registryFactory.getCategoryRegistry(selectedCategory);
        displayItems();
    }

    private void displayItems() {
        itemsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        for (Map.Entry<String, A_Item> entry : registry.getItems().entrySet()) {
            A_Item item = entry.getValue();

            String imagePath = "images/" + item.getItemName() + ".jpeg";
            System.out.println("Image Path: " + imagePath);

            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.white);

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

            JLabel nameLabel = new JLabel("<html><b>" + wrapText(item.getItemName(), 20) + "</b></html>");
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            detailsPanel.add(nameLabel);

            JLabel priceLabel = new JLabel("<html><b>$" + item.getPrice() + "</b></html>");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            detailsPanel.add(priceLabel);

            JLabel descriptionLabel = new JLabel("<html>" + wrapText(item.getDescription(), 40) + "</html>");
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            detailsPanel.add(descriptionLabel);

            detailsPanel.add(Box.createVerticalStrut(10));

            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToCart(item);
                }
            });
            detailsPanel.add(addToCartButton);

            itemPanel.add(detailsPanel, BorderLayout.CENTER);

            itemsPanel.add(itemPanel, gbc);
            gbc.gridy++;

            itemsPanel.add(Box.createVerticalStrut(20), gbc);
            gbc.gridy++;

            itemsPanel.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
            gbc.gridy++;
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private String wrapText(String text, int charactersPerLine) {
        StringBuilder wrappedText = new StringBuilder("<html>");
        String[] words = text.split("\\s+");
        int count = 0;

        for (String word : words) {
            if (count + word.length() > charactersPerLine) {
                wrappedText.append("<br>");
                count = 0;
            }

            wrappedText.append(word).append(" ");
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
        ItemDetailsPage itemDetailsPage = new ItemDetailsPage(this.user, item);
        setVisible(false);
        itemDetailsPage.setVisible(true);
    }

//    Used the getProduct function in registry class so that we can return clone of the object not the object it's self
    private void addToCart(A_Item item){
        shoppingCart.addItem(registry.getProduct(item.getItemName()));
        System.out.println("Item added to cart: " + item.getItemName());
    }



    private void switchToCartPage() {
        cartPage = new DisplayCartPage(this.user);
//        cartPage.setPage(this);
        cartPage.setVisible(true);
        setVisible(false);
    }


    public CategorySelectionPage getCategorySelectionPage() {
        return categorySelectionPage;
    }

    public void setCategorySelectionPage(CategorySelectionPage categorySelectionPage) {
        this.categorySelectionPage = categorySelectionPage;
    }
}
