package eapli.base.app.backoffice.console.presentation.customer;

import eapli.base.customerusermanagement.application.RegisterCustomerController;
import eapli.base.customerusermanagement.application.RegisterCustomerControllerImpl;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.Date;


public class RegisterCustomerUI extends AbstractUI{

   private final RegisterCustomerController theController;

   public RegisterCustomerUI(RegisterCustomerController registerCustomerController){
       this.theController = registerCustomerController;
   }

    protected boolean doShow() {
        final String vatID = Console.readLine("VAT code");
        final String name = Console.readLine("Name");
        final String email = Console.readLine("E-mail");
        final String phoneNumber = Console.readLine("Phone Number");

        //Optionals
        final String gender = Console.readLine("Gender");
        final Date birthDate = Console.readDate("Birth Date");

        final String cityBillingAddress = Console.readLine("Billing Address, city");
        final String streetNBillingAddress = Console.readLine("Billing Address, street name");
        final String doorNBillingAddress = Console.readLine("Billing Address, door number");

        final String cityDeliveryAddress = Console.readLine("Delivery Address, city");
        final String streetNDeliveryAddress = Console.readLine("Delivery Address, street name");
        final String doorNDeliveryAddress = Console.readLine("Delivery Address, door number");

        try{
            if(gender.equals("") && birthDate.equals("") && cityBillingAddress.equals("") && cityDeliveryAddress.equals("")){
                this.theController.registerCustomer(vatID,name,email,phoneNumber);
            }else if(cityBillingAddress.equals("") && cityDeliveryAddress.length()> 0){
                this.theController.registerCustomer(vatID,name,email,phoneNumber,gender,birthDate,"",
                        "","",cityDeliveryAddress,
                        streetNDeliveryAddress,doorNDeliveryAddress);
            }else if(cityDeliveryAddress.equals("")){
                this.theController.registerCustomer(vatID,name,email,phoneNumber,gender,birthDate,cityBillingAddress,
                        streetNBillingAddress,doorNBillingAddress,"",
                        "","");
            }else{
                this.theController.registerCustomer(vatID,name,email,phoneNumber,gender,birthDate,cityBillingAddress,
                        streetNBillingAddress,doorNBillingAddress,cityDeliveryAddress,
                        streetNDeliveryAddress,doorNDeliveryAddress);

                System.out.printf("Client with VAT %s was successfuly registered.\n", vatID);
            }

        } catch (final IntegrityViolationException e){
            System.out.println("You tried to enter a Customer that already exists in the database.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Product";
    }
}
