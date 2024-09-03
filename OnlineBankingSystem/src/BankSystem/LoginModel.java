package BankSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private static final String ADMIN_TABLE_NAME = "admin";
    private static final String CUSTOMER_TABLE_NAME = "customers";

    public String login(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
          
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE username=? AND password=?")) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return "admin";
                    }
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE username=? AND password=?")) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return "customer";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
