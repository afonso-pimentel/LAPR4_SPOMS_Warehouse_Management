package eapli.base.app.backoffice.console.presentation;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.SelectWidget;

import java.util.ArrayList;
import java.util.List;

public class FilterOptions {

    public String filterField(Iterable<String> filterOption){
        final SelectWidget<String> filterSelector = new SelectWidget<>("Filter By:", filterOption,
                visitee -> System.out.print(visitee));
        filterSelector.show();
        final String filterField = filterSelector.selectedElement();
        return filterField;
    }

    public String filter(String filterField){
        String filter = "";
        if(filterField != null)
            filter = Console.readLine(filterField + " like:");
        System.out.println();
        return filter;
    }

    public String sortField(Iterable<String> sortOption){
        final SelectWidget<String> sortSelector = new SelectWidget<>("Sort By:", sortOption,
                visitee -> System.out.print(visitee));
        sortSelector.show();
        final String sortField = sortSelector.selectedElement();
        return sortField;
    }

    public boolean ascendingOption(String sortField){
        boolean ascending = false;
        if(sortField != null)
            ascending = Console.readBoolean("Ascending?");
        System.out.println();
        return ascending;
    }

    public FilterOptions() {
    }
}
