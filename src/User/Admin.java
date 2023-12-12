package User;

public class Admin extends IUser {
    public Admin(int userID, String fname, String lname, String ssn, String email, String password, String Role) {
        UserID = userID;
        Fname = fname;
        Lname = lname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.Role = Role;
    }
    @Override
    public void login(String UserName, String password) {
        // will use the UserName for a hello msg
        // Dashboard has 2 buttons 1 for categories & other for items each take to the respective page
//        Dashboard d = new Dashboard();

    }

  // Use removeProduct(String itemName) to remove product from registery by name
}
