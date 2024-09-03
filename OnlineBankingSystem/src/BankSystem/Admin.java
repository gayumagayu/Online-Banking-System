package BankSystem;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin {
  
	Scanner scanner=new Scanner(System.in);
     
    public void startAdminInterface() {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println();
        System.out.println();
       System.out.println("                                "
       		+ " ************************* WELCOME ADMIN!!!!***********************");
       System.out.println("");
	   System.out.println("");
       boolean f = false;
      while(!f)
      {
    	  displayMenu();
    	    int choice = readChoice();
    	     //scanner.nextLine();
            switch (choice) {
                case 1:
                {
                	
                	addCustomer();
                    break;
                }
                case 2:
                {
                    removeCustomer();
                   
                    break;
                }
                case 3:
                {
                    viewCustomerDetails();
                    
                    break;
                }
                case 4:
                {
                    viewAllCustomers();
                    break;
                }
                case 5:
                {
                   f= true;
                   System.out.println("Logged out.");
                   BankMain end=new BankMain();
                   end.dis();
                  
                    System.out.println("                          "
                    		+" **************************** THANK YOU ***************************");
                    break;
                }
                default:
                	
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }}
             
    	   }private void displayMenu() {
    	        System.out.println("\n1) Add Customer");
    	        System.out.println("2) Remove Customer");
    	        System.out.println("3) View Customer Details by Account Number");
    	        System.out.println("4) View All Customers");
    	        System.out.println("5) Logout");
    	        System.out.print("Enter your choice: ");
    	    }
    	   private int readChoice() {
    	        boolean validChoice = false;
    	        int choice = 0;

    	        while (!validChoice) {
    	            try {
    	                choice = scanner.nextInt();
    	                scanner.nextLine(); 
    	                validChoice = true;
    	            } catch (Exception e) {
    	                System.out.println("Invalid input. Please enter a number.");
    	                scanner.nextLine(); 
    	                displayMenu();
    	            }
    	        }
   	        return choice;
    	    }
  
    	   
    private void addCustomer() {
    
    	System.out.println("Enter customer details:");
        System.out.print("Enter customer username: ");
        String customerUsername = scanner.nextLine();
        System.out.print("Enter customer password: ");
        String customerPassword = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter customer date of birth (YYYY-MM-DD): ");
        String customerDOB = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String customerAddress = scanner.nextLine();
        String email;
        Pattern pattern;
        Matcher matcher;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        do {
            System.out.print("Enter email address: ");
            email = scanner.nextLine();
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email address. Please enter a valid email.");
            }
        } while (!matcher.matches());
         String customerEmail = email;
        System.out.print("Enter customer phone: ");
        String customerPhone = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        String customerBalance1 = scanner.nextLine();
        double customerBalance = Double.parseDouble(customerBalance1);
        int age = calculateAge(customerDOB);

        String accountNumber = generateAccountNumber();
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO customers (account_number, username, password, name, date_of_birth, age, address, email, phone_number, balance) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
       
        statement.setString(1, accountNumber);
        statement.setString(2, customerUsername);
        statement.setString(3, customerPassword);
        statement.setString(4, customerName);
        statement.setString(5, customerDOB);
        statement.setInt(6, age);
        statement.setString(7, customerAddress);
        statement.setString(8, email);
        statement.setString(9, customerPhone);
        statement.setDouble(10, customerBalance);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New account created successfully.");
        } else {
            System.out.println("Failed to create new account.");
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    } /*finally {
        scanner.close();
    }*/
}
	
	private static int calculateAge(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
       LocalDate currentDate = LocalDate.now();
    Period period = Period.between(birthDate, currentDate);
   return period.getYears();
}
public static String generateAccountNumber() {
   String maxAccountNumber = getMaxAccountNumber();
   int nextAccountNumber = extractNumber(maxAccountNumber) + 1;
       String l=Integer.toString(nextAccountNumber);
   return "ACCNO" + l;
}

private static String getMaxAccountNumber() {
   String maxAccountNumber = "ACCNO";
   try (Connection connection = DatabaseConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT MAX(account_number) FROM customers");
        ResultSet resultSet = statement.executeQuery()) {
       if (resultSet.next()) {
           maxAccountNumber = resultSet.getString(1);
       }
   } catch (SQLException e) {
       System.out.println("Database error: " + e.getMessage());
   }
   return maxAccountNumber;
}

private static int extractNumber(String accountNumber) {
   String number = accountNumber.replaceAll("\\D+", "");
   return Integer.parseInt(number);
}	
    //}   
/***********************************************************************/
/***********************************************************************/
    private void removeCustomer() {
    	
    	System.out.print("Enter account number of customer to remove: ");
        
		String accountNumber = scanner.nextLine();
        System.out.print("Enter name of customer to remove: ");
        String customerName = scanner.nextLine();
        try
        {
        	Connection connection = DatabaseConnector.getConnection();
        String sql = "DELETE FROM customers WHERE account_number = ? AND name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, accountNumber);
        statement.setString(2, customerName);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Customer removed successfully.");
        } else {
            System.out.println("Customer not found or removal failed.");
        }
        }
        catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
    }}
/************************************************************************************************/
/************************************************************************************************/   
    private void viewCustomerDetails() {
    	System.out.print("Enter account number of customer to view details: ");
        String accountNumber = scanner.nextLine();
        try
        {
        	Connection connection = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM customers WHERE account_number = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Customer Details:");
                    System.out.println("Account Number: " + resultSet.getString("account_number"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Date of Birth: " + resultSet.getString("date_of_birth"));
                    System.out.println("Age: " +resultSet.getString("Age"));
                    System.out.println("Address: " + resultSet.getString("address"));
                    System.out.println("Email: " + resultSet.getString("email"));
                    System.out.println("Phone: " + resultSet.getString("phone_number"));
                    System.out.println("Balance: " + resultSet.getDouble("balance"));
                     } else {
                    System.out.println("Customer not found."); }
                }
        }
     catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());}
        }
    /************************************************************************************************/
    /************************************************************************************************/ 
    private void viewAllCustomers() {
    	try {
        	Connection connection = DatabaseConnector.getConnection();
            Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT account_number, name, date_of_birth, age, address, email, phone_number, balance, username, password FROM customers");

            System.out.println("Customer Details:");
         // Printing the records
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-15s %-20s %-30s %-20s %-15s %-15s %-15s %-15s\n", "Account Number", "Name", "Date of Birth", "Age", "Address", "Email", "Phone", "Balance", "Username", "Password");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (resultSet.next()) {
                String accountNumber = resultSet.getString("account_number");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("date_of_birth");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone_number");
                double balance = resultSet.getDouble("balance");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.printf("%-15s %-20s %-15s %-20s %-30s %-20s %-15s %-15s %-15s %-15s\n", accountNumber, name, dob, age, address, email, phone, balance, username, password);
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
 resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching customer details: " + e.getMessage());
        }
    }
}


