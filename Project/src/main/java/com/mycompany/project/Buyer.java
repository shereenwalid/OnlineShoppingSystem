/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author USER
 */
public class Buyer {
   private int buyerId;
   private String fname;
   private String lname;
   private String ssn;
   private String password;
   private String shippingaddress;
   private List<ShoppingCart> carts;
   private List<Order> Orders;
   private Creditcard Card;
   
  public void browseCategories()
  {
      
  }
  
  public void addToCart()
  {
      
  }
  
  public void CancelOrder()
  {
      
  }
 public void GetFeedBack()
  {
      
  }
   
 public void updatePersonalInfo()
  {
      
  }
   
 public int getBuyerId() {
        return buyerId;
    }

    
 public <any> getCarts() {
        return carts;
    }

 
 public <any> getOrders() {
        return Orders;
    }

 
 public Creditcard getCard() {
        return Card;
    }
  
 

 
 public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

 
 public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

 
 public void setCarts(<any> carts) {
        this.carts = carts;
    }

 
 public void setOrders(<any> Orders) {
        this.Orders = Orders;
    }

 
 public void setCard(Creditcard Card) {
        this.Card = Card;
    }
  
  
}
