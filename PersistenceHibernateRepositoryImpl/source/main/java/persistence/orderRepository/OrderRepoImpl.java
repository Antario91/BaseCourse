package persistence.orderRepository;

import domain.Entity;
import domain.customer.Customer;
import domain.order.Order;
import persistence.GenericRepoImpl;
import domain.order.OrderRepo;

import domain.EntityAlreadyExistException;
import domain.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;

public class OrderRepoImpl extends GenericRepoImpl<Long> implements OrderRepo {
    public OrderRepoImpl(Class<Entity<Long>> entityClass,
                         String businessKeyPropertyName,
                         EntityAlreadyExistException entityAlreadyExistException,
                         EntityDoesNotExistException entityDoesNotExistException,
                         SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getAllCustomersOrders(Customer customer) {
        return (List<Order>) getSessionFactory().getCurrentSession()
                .createQuery("SELECT o FROM Order o WHERE o.customerId = :customerId")
                .setParameter("customerId", customer.getId())
                .list();
    }

    @Override
    public long getNextOrdersBillingNumber() {
        BigDecimal billingNumber = (BigDecimal) getSessionFactory().getCurrentSession()
                .createSQLQuery("SELECT billing_number_generator.NEXTVAL FROM dual").uniqueResult();

        return billingNumber.longValue();
    }
}
