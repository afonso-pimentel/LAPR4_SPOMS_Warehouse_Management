package eapli.base.app.backoffice.console.presentation.product;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.application.RegisterProductController;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.strings.util.StringPredicates;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegisterProductUI extends AbstractUI {

    private final RegisterProductController theController;

    public RegisterProductUI(@NotNull RegisterProductController registerProductControllerImpl) {
        this.theController = registerProductControllerImpl;
    }

    @Override
    protected boolean doShow() {
        final Iterable<ProductCategory> productCategories = this.theController.allProductCategories();
        String prompt = null;

        prompt = "InternalCode";
        final String internalCode = Console.readLine(prompt);
        if(isNullInput(internalCode, prompt)) return false;

        prompt = "Brand";
        final String brand = Console.readLine(prompt);
        if(isNullInput(brand, prompt)) return false;

        prompt = "Reference";
        final String reference = Console.readLine(prompt);
        if(isNullInput(reference, prompt)) return false;

        final SelectWidget<ProductCategory> selector = new SelectWidget<>("Product Categories:", productCategories,
                new ProductCategoriesPrinter());
        selector.show();
        final ProductCategory theProductCategory = selector.selectedElement();

        prompt = "Price";
        final double price = Console.readDouble(prompt);
        if (price<=0){
            System.out.println("Price can't be a negative value or zero.");
            return false;
        }

        prompt = "Short Description";
        final String shortDescription = Console.readLine(prompt);
        if(isNullInput(shortDescription, prompt)) return false;

        prompt = "Extended Description";
        final String extendedDescription = Console.readLine(prompt);
        if(isNullInput(extendedDescription, prompt)) return false;

        prompt = "Barcode number";
        final String barcode = Console.readLine(prompt);
        if(isNullInput(barcode, prompt)) return false;

        prompt = "Barcode Image Path";
        final String barcodePath = Console.readLine(prompt);
        if(!isNullInput(barcodePath,prompt)) {
            try {
                final InputStream barcodeStream =  new FileInputStream(barcodePath);
                final byte[] bars = IOUtils.toByteArray(barcodeStream);
                System.out.println("Barcode image added.");
                try {
                    this.theController.registerProduct(internalCode, brand, reference,
                            theProductCategory, barcode, bars,
                            price, shortDescription, extendedDescription);
                    System.out.printf("Product with internal code %s has been successfully added. \n", internalCode);
                } catch (final IntegrityViolationException e) {
                    System.out.printf("Product with internal code %s already exists in the database. \n", internalCode);
                }
            } catch (final IOException e) {
                System.out.println("Barcode image path is invalid.");
                return false;
            }
        } else return false;
        return false;
    }

    private boolean isNullInput(String input, String prompt){
        if(StringPredicates.isNullOrEmpty(input)) {
            System.out.println(prompt + " cannot be null or empty.");
            return true;
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Product";
    }
}
