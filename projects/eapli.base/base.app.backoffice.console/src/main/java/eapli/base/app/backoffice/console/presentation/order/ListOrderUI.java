package eapli.base.app.backoffice.console.presentation.order;

import com.sun.istack.NotNull;
import eapli.base.app.backoffice.console.presentation.FilterOptions;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.ordersmanagement.application.ListOrderController;
import eapli.base.ordersmanagement.domain.OrderLine;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOrderUI extends AbstractListUI<Order_> {

    private ListOrderController controller;

    public ListOrderUI(@NotNull ListOrderController controller) {
        this.controller = controller;
    }

    @Override
    protected Iterable<Order_> elements() {
        FilterOptions filterOptions = new FilterOptions();
        //Filter
        final Iterable<String> filterOption = new ArrayList<>(
                List.of("Customer","Prepared Orders")
        );
        final String filterField = filterOptions.filterField(filterOption);
        if(filterField.equals("Customer")){
            System.out.println("Please insert the Customer's VAT Code.");
            final String filter = filterOptions.filter(filterField);
            VatCode customerId = VatCode.valueOf(filter);
            return controller.customerOrders(customerId);
        }else if(filterField.equals("Prepared Orders")){
            return controller.preparedOrders();
        }
        return null;
    }

    @Override
    protected Visitor<Order_> elementPrinter() {
        return new OrderPrinter();
    }

    @Override
    protected String elementName() {
        return "Orders";
    }

    @Override
    protected String listHeader() {
        return "";
    }

    @Override
    protected String emptyMessage() {
        return "None available.";
    }

    @Override
    protected boolean doShow() {
        final Iterable<Order_> elems = elements();
        if (!elems.iterator().hasNext()) {
            System.out.println(emptyMessage() + elementName());
        } else {
            final SelectWidget<Order_> selector = new SelectWidget<>(listHeader(), elems,
                    elementPrinter());
            selector.show();
            if(selector.selectedOption()!=0){
                Order_ orderToShow = selector.selectedElement();
                Iterable<OrderLine> orderLines = orderToShow.orderLines();
                printOrderLines(orderLines);
                System.out.println("Total Price: "+orderToShow.totalBeforeTax());
                Console.readLine("Click any button to get to the main menu.");
                return false;
            }
        }
        return false;
    }

    private void printOrderLines(Iterable<OrderLine> orderLines){
        int c = 0;
        Iterator<OrderLine> iterator = orderLines.iterator();
        while(iterator.hasNext()){
            c++;
            System.out.println(c+". "+iterator.next());
        }
    }

    @Override
    public String headline() {
        return "List Orders";
    }
}
