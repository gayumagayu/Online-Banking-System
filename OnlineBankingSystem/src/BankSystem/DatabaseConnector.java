package BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String url = "jdbc:mysql://localhost:3306/banking";
    private static final String user = "root";
    private static final String password = "Mg0809";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}

