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
        // will use the UserName for hello msg
//        Dashboard d = new Dashboard();

    }
}