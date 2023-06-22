package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.app.backoffice.console.presentation.FilterOptions;
import eapli.base.productmanagement.application.ListProductController;
import eapli.base.productmanagement.application.ListProductControllerImpl;
import eapli.base.productmanagement.domain.Product;
import eapli.base.usermanagement.application.ListUsersController;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import eapli.framework.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ListProductsUI extends AbstractListUI<Product> {
    private ListProductController theController = new ListProductControllerImpl();


    @Override
    public String headline() {
        return "List Products";
    }

    @Override
    protected Iterable<Product> elements() {
        FilterOptions filterOptions = new FilterOptions();

        //Filter
        final Iterable<String> filterOption = new ArrayList<> (
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

        return theController.sortAndFilterProducts(filterField,filter,sortField,ascending);
    }

    @Override
    protected Visitor<Product> elementPrinter() {
        return new ProductPrinter();
    }

    @Override
    protected String elementName() {
        return "Products";
    }

    @Override
    protected String listHeader() {
        return String.format("#  %-10s%-10s%-10s%-15s%-30s%n", "CODE", "CATEGORY", "PRICE", "BRAND", "DESCRIPTION");
    }

    @Override
    protected String emptyMessage() {
        return "No available ";
    }
}
