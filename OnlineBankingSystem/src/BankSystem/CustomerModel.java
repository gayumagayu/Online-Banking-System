package BankSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {

    public boolean registerCustomer(String username, String password, String name, String dob, String address, String email, String phone, double balance) {
        String accountNumber = generateUniqueAccountNumber();

        if (accountNumber == null) {
            System.out.println("Failed to generate account number.");
            return false;
        }

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO customers (account_number, username, password, name, date_of_birth, address, email, phone_number, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountNumber);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, name);
                statement.setString(5, dob);
                statement.setString(6, address);
                statement.setString(7, email);
                statement.setString(8, phone);
                statement.setDouble(9, balance);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    String generateUniqueAccountNumber() {
        String accountNumber = null;

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(CAST(SUBSTRING(account_number, 6) AS UNSIGNED)) FROM customers");
            ResultSet resultSet = statement.executeQuery();

            int maxNumericPart = 0;
            if (resultSet.next()) {
                maxNumericPart = resultSet.getInt(1);
            }

            int numericPart = maxNumericPart + 1;
            accountNumber = "ACCNO" + String.format("%05d", numericPart);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        return accountNumber;
    }
}
