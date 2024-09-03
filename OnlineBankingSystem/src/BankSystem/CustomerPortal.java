package BankSystem;
import java.util.*;
import java.sql.*;
public class CustomerPortal {
	Scanner scanner=new Scanner(System.in);
  private String accountNumber;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("           "
        		+ "  "+"***********WELCOME***************");
        boolean validAccount = false;
        while (!validAccount) {
            System.out.print("Enter account number: ");
            accountNumber = scanner.nextLine();

            if (isAccountNumberExists(accountNumber)) {
                validAccount = true;
            } else {
                System.out.println("Account number does not exist. Please try again.");
            }
        }
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println();
        System.out.println();
        
        System.out.println("                                                        Welcome "+name+"!!");
        boolean t = false;
        while (!t) {
        	
          	  display1();
            
        
        
        int choice = readChoice();
        switch (choice) {
            case 1:
                deposit();
                break;
            case 2:
            	withdraw();
                break;
            case 3:
            	Balance();
              break;
            
            case 4:
            {
            	Transaction record=new Transaction();
            	//record.viewTransactionHistory(accountNumber);
            	 record.viewTransactionHistory(accountNumber);
            }
            case 5:
                t = true;
                BankMain end=new BankMain();
                end.dis();
                 System.out.println("Thank you. Exiting...");
                 break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        }}
       //scanner.close();
        //}
    private void display1() {
    System.out.println("Enter Your Choice:");
    System.out.println("1. Deposit");
    System.out.println("2. Withdrawal");
    System.out.println("3.View Balance");
    System.out.println("4.Transcation Details");
    System.out.println("5.Exit");
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
                display1(); 
            }
        }

        return choice;
    }

    
    private static boolean isAccountNumberExists(String accountNumber) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM customers WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
    private void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        boolean success = updateBalance(amount);
        if (success) {
            double currentBalance = getCurrentBalance();
            recordTransaction("DEPOSIT", amount);
            System.out.println("Amount deposited: " + amount);
            System.out.println("Current balance: " + currentBalance);
        } else {
            System.out.println("Failed to deposit amount.");
        }

        //scanner.close();
    }
    private void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
      double currentBalance = getCurrentBalance();
        if (currentBalance >= amount) {
             boolean success = updateBalance(-amount); 
            if (success) {
               currentBalance = getCurrentBalance();
               recordTransaction("WITHDRAWAL", amount);
                System.out.println("Amount withdrawn: " + amount);
                System.out.println("Current balance: " + currentBalance);
            } else {
                System.out.println("Failed to withdraw amount.");
            }
        } else {
            System.out.println("Insufficient balance.");
        }

     //   scanner.close();
    }


    private boolean updateBalance(double amount) {
     try(Connection connection = DatabaseConnector.getConnection()) {
            String sql = "UPDATE customers SET balance = balance + ? WHERE account_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, amount);
                statement.setString(2, accountNumber);
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private double getCurrentBalance() {
        
    	try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT balance FROM customers WHERE account_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountNumber);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDouble("balance");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
    private void Balance() {
        double currentBalance = getCurrentBalance();
        if (currentBalance >= 0) {
            System.out.println("Current balance: " + currentBalance);
        } else {
            System.out.println("Failed to retrieve balance.");
        }
    }   
  /*  public boolean registerAdmin(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    } */
    private void recordTransaction(String transactionType, double amount) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO transaction_history (account_number, transaction_type, amount, transaction_time) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountNumber);
                statement.setString(2, transactionType);
                statement.setDouble(3, amount);
                statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 }
