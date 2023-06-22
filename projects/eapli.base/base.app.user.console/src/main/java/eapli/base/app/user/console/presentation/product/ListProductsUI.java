package eapli.base.app.user.console.presentation.product;

import eapli.base.app.user.console.presentation.FilterOptions;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import models.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ListProductsUI extends AbstractListUI<ProductDTO> {
    private ListProductsController theController = new ListProductsControllerImpl();


    @Override
    public String headline() {
        return "List Products";
    }

    @Override
    protected Iterable<ProductDTO> elements() {
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
    protected Visitor<ProductDTO> elementPrinter() {
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
