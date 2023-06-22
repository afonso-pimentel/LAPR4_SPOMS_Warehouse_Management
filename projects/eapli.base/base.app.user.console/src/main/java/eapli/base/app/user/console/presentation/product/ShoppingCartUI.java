package eapli.base.app.user.console.presentation.product;

import eapli.base.app.user.console.presentation.FilterOptions;
import eapli.base.clientusermanagement.domain.ShoppingCart;
import eapli.base.clientusermanagement.domain.ShoppingCartLine;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import models.ProductDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class ShoppingCartUI extends AbstractUI {
    private ListProductsController theController = new ListProductsControllerImpl();

    protected boolean doShow() {
        FilterOptions filterOptions = new FilterOptions();

        //Filter
        final Iterable<String> filterOption = new ArrayList<>(
                List.of("Category", "Brand", "Short Description")
        );
        final String filterField = filterOptions.filterField(filterOption);
        final String filter = filterOptions.filter(filterField);

        //Sort
        final Iterable<String> sortOption = new ArrayList<> (
                List.of("Product Code","Category", "Brand", "Short Description")
        );
        String sortField = filterOptions.sortField(sortOption);
        boolean ascending = filterOptions.ascendingOption(sortField);

        Iterable<ProductDTO> currentCatalog = theController.sortAndFilterProducts(filterField,filter,sortField,ascending);

        ArrayList<ProductDTO> productList = new ArrayList((Collection) currentCatalog);
        int i=1;
        for (ProductDTO pr: productList) {
            System.out.println("Product Number: "+(i++));
            System.out.println(pr.toString());
        }

        final String addItem = Console.readLine("Want to add an Item to your Shopping Cart?(Y/N)");
        if (addItem.equals("Y")){
            ProductDTO productToAdd = null;
            final int productNumber = Console.readInteger("Insert the product Number you want to add to your shopping cart");
            if ((productNumber) > 0 && productNumber < productList.size()){
                productToAdd = productList.get((productNumber-1));
                int quantity = Console.readInteger("Quantity ");
                for (ShoppingCartLine scl: ShoppingCart.Cache.ShoppingCartList()) {
                    if (productToAdd.internalCode.equals(scl.getInternalCode()) && productToAdd.category.equals(scl.getCategory())){
                        quantity += scl.getQuantity();
                        ShoppingCart.Cache.ShoppingCartList().remove(scl);
                        ShoppingCart.Cache.addProduct(new ShoppingCartLine(productToAdd.internalCode,productToAdd.brand,productToAdd.category,productToAdd.price,productToAdd.shortDescription,quantity));
                        return true;
                    }
                }
                ShoppingCart.Cache.addProduct(new ShoppingCartLine(productToAdd.internalCode,productToAdd.brand,productToAdd.category,productToAdd.price,productToAdd.shortDescription,quantity));
                return true;
            }else{
                throw new IllegalArgumentException("Please select a valid Product Number.");
            }
        }else{
            return false;
        }
    }

    @Override
    public String headline() {
        return "Add product to shopping cart";
    }

}
