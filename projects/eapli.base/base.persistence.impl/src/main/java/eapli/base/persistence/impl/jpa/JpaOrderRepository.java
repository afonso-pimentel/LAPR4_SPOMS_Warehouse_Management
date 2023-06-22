package eapli.base.persistence.impl.jpa;

import com.sun.istack.NotNull;
import eapli.base.Application;
import eapli.base.customerusermanagement.domain.Address;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.domain.PaymentMethod;
import eapli.base.ordersmanagement.domain.ShipmentMethod;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import eapli.framework.time.domain.model.TimeInterval;

import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class JpaOrderRepository
        extends JpaAutoTxRepository<Order_, Long, Long>
        implements OrderRepository {

    public JpaOrderRepository(final TransactionalContext autoTx) {
        super(autoTx,"orderId" );
    }

    public JpaOrderRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "orderId");
    }

    @Override
    public Iterable<Order_> findCustomerOrders(VatCode customerID) {
        String where = "e.customerId="+customerID.toString();
        return match(where);
    }

    @Override
    public Iterable<Order_> findOrdersByStatus(OrderStatus orderStatus) {
        String where = "e.orderStatus="+orderStatus.ordinal();
        return match(where);
    }

    @Override
    public Iterable<Order_> findOldestRegisteredOrders() {
        return match(" e.orderStatus="+OrderStatus.REGISTERED.ordinal()+" ORDER BY e.orderDate ASC, e.orderId ASC");
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Order_> findOrdersWithSpecificProduct(final AlphaNumericCode productId, final java.util.Date startDate, final Date endDate) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Order_ e ");
        customQuery.append("WHERE e IN ");
        customQuery.append("(SELECT DISTINCT o FROM OrderLine o WHERE o.productId = :productId) AND e.orderDate BETWEEN :startDate AND :endDate");

        final TypedQuery<Order_> query = entityManager().createQuery(customQuery.toString(), Order_.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("productId", productId);

        return query.getResultList();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Order_> findOrdersWithProductFromSpecificBrand(Designation brand, Date startDate, Date endDate) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Order_ e ");
        customQuery.append("WHERE e IN ");
        customQuery.append("(SELECT DISTINCT o FROM OrderLine o INNER JOIN Product p ON p.internalCode = o.productId WHERE p.category_code = :brand) AND e.orderDate BETWEEN :startDate AND :endDate");

        final TypedQuery<Order_> query = entityManager().createQuery(customQuery.toString(), Order_.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("brand", brand);

        return query.getResultList();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Order_> findOrdersWithProductFromSpecificCategory(ProductCategory category, Date startDate, Date endDate) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Order_ e ");
        customQuery.append("WHERE e IN ");
        customQuery.append("(SELECT DISTINCT o FROM OrderLine o INNER JOIN Product p ON p.internalCode = o.productId WHERE p.category = :category) AND e.orderDate BETWEEN :startDate AND :endDate");

        final TypedQuery<Order_> query = entityManager().createQuery(customQuery.toString(), Order_.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("category", category);

        return query.getResultList();
    }
}
