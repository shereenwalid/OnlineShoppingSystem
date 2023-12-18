package User;

import OrderFacade.ShoppingCart;

public abstract class IUser {
    int UserID;
    String Fname;
    String Lname;
    String ssn;
    String email;
    String password;
    String Role;

    ShoppingCart cart;


    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public abstract void login(String UserName, String password);





    //    IUser Register();
}