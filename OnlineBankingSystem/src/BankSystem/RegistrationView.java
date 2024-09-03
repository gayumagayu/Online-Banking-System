package BankSystem;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationView {
    private Scanner scanner;

    public RegistrationView() {
        scanner = new Scanner(System.in);
    }

    public String[] getCustomerInput() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        String email;
        Pattern pattern;
        Matcher matcher;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email address. Please enter a valid email.");
            }
        } while (!matcher.matches());

        System.out.print("Enter your phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter initial amount: ");
        String balance = scanner.nextLine();
        return new String[]{username, password, name, dob, address, email, phone, balance};
    }

    public void displayRegistrationSuccess(String accountNumber) {
        System.out.println();
        System.out.println("       Registration successful!!");
        System.out.println("       Your account number is: " + accountNumber);
    }

    public void displayRegistrationFailure() {
        System.out.println("       Registration failed. Please try again.");
    }
}

/*package BankSystem;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationView {
    private Scanner scanner;

    public RegistrationView() {
        scanner = new Scanner(System.in);
    }

    public String[] getCustomerInput() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        String email;
        Pattern pattern;
        Matcher matcher;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email address. Please enter a valid email.");
            }
        } while (!matcher.matches());

        System.out.print("Enter your phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter initial amount: ");
        String balance = scanner.nextLine();
        return new String[]{username, password, name, dob, address, email, phone, balance};
    }

    public void displayRegistrationSuccess() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("       Registration successful!!");
    }

    public void displayRegistrationFailure() {
        System.out.println("       Registration failed. Please try again.");
    }
}






*/