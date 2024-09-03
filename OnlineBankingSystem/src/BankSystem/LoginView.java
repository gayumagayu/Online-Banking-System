package BankSystem;

import java.util.Scanner;

public class LoginView {
    private String username;
    private String password;

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        username = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();
          
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void showInvalidCredentialsMessage() {
        System.out.println("Invalid username or password.");
        //getUserInput();
    }

    public void showAdminPortalMessage() {
    	Admin call=new Admin();
    	call.startAdminInterface();
        
    }

    public void showCustomerPortalMessage() {
    	CustomerPortal c=new CustomerPortal();
    	c.start();
    	
       
    }
}
