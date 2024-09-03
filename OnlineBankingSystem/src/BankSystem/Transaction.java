package BankSystem;
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Transaction {
    

    public void viewTransactionHistory(String accountNumber) {
    Scanner scanner=new Scanner(System.in);
    	 System.out.print("Please enter the start date (YYYY-MM-DD): ");
         LocalDate startDate = LocalDate.parse(scanner.nextLine());

         System.out.print("Please enter the end date (YYYY-MM-DD): ");
         LocalDate endDate = LocalDate.parse(scanner.nextLine());
         
             try (Connection connection = DatabaseConnector.getConnection()) {
                 String sql = "SELECT * FROM transaction_history WHERE account_number = ?  AND DATE(transaction_time) >= ?  AND DATE(transaction_time) <= ?";

                 try (PreparedStatement statement = connection.prepareStatement(sql)) {
                     statement.setString(1, accountNumber);
                     statement.setString(2, startDate.toString());
                     statement.setString(3, endDate.toString());
                     try (ResultSet resultSet = statement.executeQuery()) {
                    	 System.out.println("-----------------------------------------------------------------");
  	                    System.out.printf("| %-15s | %-10s | %-23s |\n", "Type", "Amount", "Transaction Time");
  	                    System.out.println("------------------------------------------------------------------");
                         while (resultSet.next()) {
                        	 
                             String transactionType = resultSet.getString("transaction_type");
                             double amount = resultSet.getDouble("amount");
                             String transactionTime = resultSet.getString("transaction_time");
                             System.out.printf("| %-15s | %-10.2f | %-23s |\n", transactionType, amount, transactionTime);
                            
                         }
                     }
                 }
             } catch (SQLException e) {
                 System.out.println("Database error: " + e.getMessage());
             }

    }

    
}
