package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import User.Buyer;

public class BuyerMainPage extends JFrame {
    private JButton browse;
    private JButton info;
    private JButton orders;
    private Buyer buyer;

    CategorySelectionPage category;
    ProfilePage profile;

    PendingOrdersPage pendingPage;

    public BuyerMainPage(Buyer buyer) {
        this.buyer = buyer;
        setTitle("Online shopping");
        setSize(800, 600);
        getContentPane().setBackground(new Color(0x333333));

        JPanel navbar = new JPanel();
        navbar.setBackground(Color.white);
        navbar.setPreferredSize(new Dimension(600, 50));
        JLabel appNameLabel = new JLabel("Online Shopping System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appNameLabel.setForeground(new Color(0x333333));
        navbar.add(appNameLabel);
        add(navbar, BorderLayout.NORTH);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    public void placeComponents(JPanel panel) {
        browse = new JButton("Browse");
        info = new JButton("Personal Information");
        orders = new JButton("Pending Orders");

        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the CategorySelectionPage
                category = new CategorySelectionPage(buyer);
                category.setVisible(true);
                dispose();
            }
        });

        orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pendingPage = new PendingOrdersPage(buyer);
                pendingPage.setVisible(true);
                dispose();
            }
        });

        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the ProfilePage
                profile = new ProfilePage(buyer);
                profile.setVisible(true);
                dispose();
            }
        });

        panel.add(browse);
        panel.add(info);
        panel.add(orders);
    }
}
