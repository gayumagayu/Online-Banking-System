package BankSystem;

import java.util.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {
    public static void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        scanner.nextLine();;
        System.out.print("Enter role (admin/customer): ");
        String role = scanner.nextLine().toLowerCase();

        if (role.equals("admin")) {
            registerAdmin(username, password);
        } else if (role.equals("customer")) {
            registerCustomer(username, password, scanner);
        } else {
            System.out.println("Invalid role. Please enter either 'admin' or 'customer'.");
        }
    }

    private static void registerAdmin(String username, String password) {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "INSERT INTO admin (username, password) VALUES ('" + username + "', '" + password + "')";
            int rowsInserted = statement.executeUpdate(sql);
            
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerCustomer(String username, String password, Scanner scanner) {
        String accountNumber = generateAccountNumber();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        int yearOfBirth = Integer.parseInt(dob.substring(0, 4));
        int currentYear = java.time.LocalDate.now().getYear();
        int age = currentYear - yearOfBirth;

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

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

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Initial Amount: ");
        double balance = scanner.nextDouble();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO customers (account_number, username, password, name, date_of_birth, age, address, email, phone_number, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, accountNumber);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, name);
                statement.setString(5, dob);
                statement.setInt(6, age);
                statement.setString(7, address);
                statement.setString(8, email);
                statement.setString(9, phone);
                statement.setDouble(10, balance);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Registration successful! Your account number is: " + accountNumber);
                } else {
                    System.out.println("Registration failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateAccountNumber() {
        String accountNumber = null;

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(account_number) FROM customers")) {

            if (resultSet.next()) {
                String maxAccountNumber = resultSet.getString(1);
                if (maxAccountNumber != null) {
                    int numericPart = Integer.parseInt(maxAccountNumber.replaceAll("\\D+", ""));
                    numericPart++;
                    accountNumber = "ACCNO" + String.format("%05d", numericPart);
                } else {
                    accountNumber = "ACCNO00001";
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        return accountNumber;
    }
}
