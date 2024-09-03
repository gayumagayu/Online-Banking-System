package BankSystem;
import java.util.*;
public class BankMain {
	static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	
        System.out.println("");
        System.out.println("");
    	System.out.println("                         *******************************************************");
    	System.out.println("                         *******************************************************");
    	System.out.println("                         ***                                                 ***");
    	System.out.println("                         ***               WELCOME   TO                      ***");
    	System.out.println("                         ***                   ONLINE                        ***");
    	System.out.println("                         ***              BANKING   SYSTEM                   ***");
    	System.out.println("                         ***                                                 ***");
    	System.out.println("                         *******************************************************");
    	System.out.println("                         *******************************************************");
    	System.out.println();
    	dis();
}
    static void dis() {
    	boolean exit=false;
    	while(!exit)
    	{
         System.out.println("");
    	System.out.println("Enter Your Choice");
    	System.out.println("1)LOGIN");
    	System.out.println("2)REGISTER");
    	System.out.println("3)Exit");
    	
    	try {
            int choice = sc.nextInt(); 
       
    	switch(choice)
    	{
    	case 1:
    	{
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);
         controller.processLogin();
         exit=true;
          break;
        
    	}
    	case 2:
    	{
    		
    		 CustomerModel customerModel = new CustomerModel();
    	 RegistrationView registrationView = new RegistrationView();
    	 RegistrationController registrationController = new RegistrationController(customerModel, registrationView);
    	    	registrationController.registerCustomer();
    		exit=true;
    		break;
    	}
    	case 3:
    	{
    		//System.out.print("Thank you!!!!!!");
    		exit=true;
    		break;
    	}
    	 default:
             System.out.println("Invalid choice. Please enter 1,2 or 3.");
             break;
    	}
    		}
    	catch (Exception e) {
    		BankMain r=new BankMain();
            r.dis();
            System.out.println("Error: " + e.getMessage());
            System.out.println("Invalid input. Please enter a valid number.");
            
            sc.nextLine();
    		//while(!exit);
    		//sc.close();
    	}
    }
    	sc.close();
    	
    }
}

		    
		        
		    
		

	


