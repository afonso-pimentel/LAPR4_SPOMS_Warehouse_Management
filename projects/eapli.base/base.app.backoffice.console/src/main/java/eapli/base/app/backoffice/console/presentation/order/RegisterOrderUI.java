package eapli.base.app.backoffice.console.presentation.order;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.application.RegisterOrderController;
import eapli.base.ordersmanagement.domain.PaymentMethod;
import eapli.base.ordersmanagement.domain.ShipmentMethod;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.strings.util.StringPredicates;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterOrderUI extends AbstractUI {
    private RegisterOrderController controller;

    public RegisterOrderUI(@NotNull RegisterOrderController registerOrderController){
        this.controller = registerOrderController;
    }

    @Override
    protected boolean doShow() {
        final String customerId = Console.readLine("Customer ID");

        if(StringPredicates.isNullOrEmpty(customerId)){
            System.out.println("Customer ID cannot be null or empty.");
            return false;
        }
        Map<String,Long> orderLines = inputOrderLines();
        ShipmentMethod shipmentMethod = shipmentMethodChoice();
        if ( shipmentMethod == null) return false;
        PaymentMethod paymentMethod = paymentMethodChoice();
        if ( paymentMethod == null) return false;
        System.out.println("The Shipment address considered will be the customer's address.");

        try{
            Long orderId = this.controller.registerOrder(orderLines,customerId,shipmentMethod,paymentMethod).identity();
            System.out.println("The order has been successfully registered.");
            String paymentConfirmation = Console.readLine("Is the payment confirmed? If yes, press Y, if not, press N.");
            if(paymentConfirmation.equals("Y")){
                String confirmationPath = Console.readLine("Please insert the path of the confirmation file.");
                final InputStream confirmationStream =  new FileInputStream(confirmationPath);
                final byte[] payFile = IOUtils.toByteArray(confirmationStream);
                this.controller.confirmPayment(payFile, orderId);
                System.out.println("Confirmation file added. The order is now preparation pending.");
            }else{
                System.out.println("The order will stay on Payment Pending.");
            }
        }catch (final IntegrityViolationException e){
            System.out.println("This order already exists.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;


    }

    private Map<String, Long> inputOrderLines(){
        Map<String,Long> orderLines = new HashMap<>();
        System.out.println("You will now add the products and quantity of the products you want to order.");
        String keepReading = Console.readLine("To continue press Y");
        if(keepReading.equals("Y")) {
            do {
                String productId = Console.readLine("Product ID");
                if (StringPredicates.isNullOrEmpty(productId)) {
                    System.out.println("Product ID cannot be bull or empty.");
                    return orderLines;
                }
                Long orderQuantity = Console.readLong("Order Quantity");
                if (orderQuantity < 1) {
                    System.out.println("The quantity must be equal or greater than 1.");
                    return orderLines;
                }
                orderLines.put(productId, orderQuantity);
                keepReading = Console.readLine("Press Y if you  want to continue inserting.");
            } while (keepReading.equals("Y"));
        }
        return orderLines;
    }

    private ShipmentMethod shipmentMethodChoice(){
        System.out.println("Choose the wanted Shipment Method:");
        for (ShipmentMethod shipmentMethod:ShipmentMethod.values()) {
            System.out.println("For "+shipmentMethod+" the cost is "+shipmentMethod.getCost()+".");
        }
        String methodChoice = Console.readLine("Write:");
        if(StringPredicates.isNullOrEmpty(methodChoice)){
            System.out.println("That Shipment Method doesn't exist.");
            return null;
        }
        return ShipmentMethod.valueOf(methodChoice);
    }

    private PaymentMethod paymentMethodChoice(){
        System.out.println("Choose the wanted Payment Method:");
        for (PaymentMethod paymentMethod:PaymentMethod.values()) {
            System.out.println(paymentMethod);
        }
        String methodChoice = Console.readLine("Write:");
        if(StringPredicates.isNullOrEmpty(methodChoice)){
            System.out.println("That Payment Method doesn't exist.");
            return null;
        }
        return PaymentMethod.valueOf(methodChoice);
    }

    @Override
    public String headline() {
        return "Register Order";
    }
}
