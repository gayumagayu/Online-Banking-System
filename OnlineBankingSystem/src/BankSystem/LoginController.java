package BankSystem;

public class LoginController {
    private LoginModel model;
    private LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void processLogin() {
    	boolean l=false;
    	while(!l)
    	{
        view.getUserInput();

        String username = view.getUsername();
        String password = view.getPassword();

        String role = model.login(username, password);

        if (role != null) {
            if (role.equals("admin")) {
                view.showAdminPortalMessage();
                l=true;
            } else if (role.equals("customer")) {
                view.showCustomerPortalMessage();
                l=true;
            } else {
                System.out.println("Unknown role.");
            }
        } else {
            view.showInvalidCredentialsMessage();
            
        }
    	}
    }
}
