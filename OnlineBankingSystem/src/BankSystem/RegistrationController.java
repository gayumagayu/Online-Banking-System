package BankSystem;

public class RegistrationController {
    private CustomerModel customerModel;
    private RegistrationView registrationView;

    public RegistrationController(CustomerModel customerModel, RegistrationView registrationView) {
        this.customerModel = customerModel;
        this.registrationView = registrationView;
    }

    public void registerCustomer() {
        String[] customerInput = registrationView.getCustomerInput();
        String customerUsername = customerInput[0];
        String customerPassword = customerInput[1];
        String customerName = customerInput[2];
        String customerDOB = customerInput[3];
        String customerAddress = customerInput[4];
        String customerEmail = customerInput[5];
        String customerPhone = customerInput[6];
        double customerBalance = Double.parseDouble(customerInput[7]);

        String accountNumber = customerModel.generateUniqueAccountNumber();
        boolean registrationResult = customerModel.registerCustomer(customerUsername, customerPassword, customerName, customerDOB, customerAddress, customerEmail, customerPhone, customerBalance);

        if (registrationResult) {
            registrationView.displayRegistrationSuccess(accountNumber);
        } else {
            registrationView.displayRegistrationFailure();
        }
    }
}




/*package BankSystem;

public class RegistrationController {
    private CustomerModel customerModel;
    private RegistrationView registrationView;

    public RegistrationController(CustomerModel customerModel, RegistrationView registrationView) {
        this.customerModel = customerModel;
        this.registrationView = registrationView;
    }

    public void registerCustomer() {
        String[] customerInput = registrationView.getCustomerInput();
        String customerUsername = customerInput[0];
        String customerPassword = customerInput[1];
        String customerName = customerInput[2];
        String customerDOB = customerInput[3];
        String customerAddress = customerInput[4];
        String customerEmail = customerInput[5];
        String customerPhone = customerInput[6];
        double customerBalance = Double.parseDouble(customerInput[7]);

        boolean registrationResult = customerModel.registerCustomer(customerUsername, customerPassword, customerName, customerDOB, customerAddress, customerEmail, customerPhone, customerBalance);

        if (registrationResult) {
            registrationView.displayRegistrationSuccess();
        } else {
            registrationView.displayRegistrationFailure();
        }
    }
}



*/