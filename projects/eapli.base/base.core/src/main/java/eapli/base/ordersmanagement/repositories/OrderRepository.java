package eapli.base.ordersmanagement.repositories;

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;
import java.util.Date;

public interface OrderRepository extends DomainRepository<Long, Order_> {

    /**
     *
     * @param customerID
     * @return
     */
    Iterable<Order_> findCustomerOrders(VatCode customerID);

    /**
     *
     * @param orderStatus
     * @return
     */
    Iterable<Order_> findOrdersByStatus(OrderStatus orderStatus);

    /**
     *
     * @return
     */
    Iterable<Order_> findOldestRegisteredOrders();

    /**
     * Find orders that contain a specific product
     * @param productId ProductId
     * @param startDate Start date
     * @param endDate End date
     * @return List of orders
     */
    Iterable<Order_> findOrdersWithSpecificProduct(final AlphaNumericCode productId, final Date startDate, final Date endDate);

    /**
     * Find orders that contain a product from a specific brand
     * @param brand Brand
     * @param startDate Start date
     * @param endDate End date
     * @return List of orders
     */
    Iterable<Order_> findOrdersWithProductFromSpecificBrand(final Designation brand, final Date startDate, final Date endDate);

    /**
     * Find orders that contain a product from a specific category
     * @param category Category
     * @param startDate Start date
     * @param endDate End date
     * @return List of orders
     */
    Iterable<Order_> findOrdersWithProductFromSpecificCategory(final ProductCategory category, final Date startDate, final Date endDate);
}
