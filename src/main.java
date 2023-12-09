import OrderFacade.*;
import User.*;
import Factory.Item.*;

import java.sql.SQLException;
import java.time.LocalDate;
import OrderFacade. *;
import User.Buyer;
import User.ProxyUser;

public class main {

                public static void main(String[] args) {
                    // Test user registration and login
                    testUserRegistrationAndLogin();

                    // Test shopping cart and order placement
                    testShoppingCartAndOrderPlacement();

                    // Test order cancellation
                    testOrderCancellation();

                    // Additional tests can be added based on your requirements
                }

                private static void testUserRegistrationAndLogin() {
                    // Create a new user (Buyer or Admin)
                    ProxyUser proxyUser = new ProxyUser();
                    proxyUser.BuyerRegister("TestUser", "LastName", "123456789", "test@example.com", "password", "Buyer", "1234567890123456", "123", 500.0, LocalDate.parse("2023-12-31"));

                    // Test user login
                    proxyUser.login("TestUser", "password");
                }

                private static void testShoppingCartAndOrderPlacement() {
                    // Create a new user (Buyer or Admin)
                    ProxyUser proxyUser = new ProxyUser();
                    proxyUser.BuyerRegister("TestUser", "LastName", "123456789", "test@example.com", "password", "Buyer", "1234567890123456", "123", 500.0, LocalDate.parse("2023-12-31"));

                    // Create a shopping cart for the user
                    Buyer buyer = (Buyer) proxyUser.BuyerMap.get("TestUser");
                    ShoppingCart shoppingCart = new ShoppingCart();

                    Book book = new Book(1,"test",23,3,"dsfrf",0,1,3);
                    // Add items to the shopping cart
                    // For simplicity, assume the items are retrieved from the database
                    // and added to the cart
                    shoppingCart.addItem( book);

                    // Create an order
                    Order order = new Order();
                    order.setUser(buyer);
                    order.setCart(shoppingCart);

                    // Confirm the order
                    order.ConfirmOrder();
                }

                private static void testOrderCancellation() {
                    // Assume there is an existing order in the database with ID = 1
                    Order orderToCancel = new Order();
                    Buyer proxyUser = new Buyer(1, "LastName", "123456789", "test@example.com", "password", "Buyer","User");



                    try {
                        Payments p = new Payments(proxyUser,orderToCancel);
                        // Create an OrderFacade to handle order cancellation
                        OrderFacade orderFacade = new OrderFacade(proxyUser);

                        // Cancel the entire order
                        orderFacade.cancelOrder();

                        // Additional tests for cancelling order items can be added
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }