package persistence.orderRepository;

import domain.Entity;
import domain.customer.Customer;
import domain.order.Order;
import domain.product.Product;
import persistence.GenericRepoImpl;

import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.List;

public class OrderRepoImpl extends GenericRepoImpl<Long, Long> implements OrderRepo {
    public OrderRepoImpl(Class<Entity<Long, Long>> entityClass,
                         String businessKeyPropertyName,
                         EntityAlreadyExistException entityAlreadyExistException,
                         EntityDoesNotExistException entityDoesNotExistException,
                         SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getAllOrdersProducts(Order order) {
        return (List<Product>) getSessionFactory().getCurrentSession()
//                .createQuery("SELECT p FROM Product p, Order o INNER JOIN o.orderItems item WHERE o.id = :orderId AND p.id = item.productId")
                .createQuery("SELECT p FROM Product p WHERE p.id IN (SELECT item.productId FROM Order o INNER JOIN o.orderItems item WHERE o.id = :orderId)")
//                        + "JOIN Order o JOIN o.orderItems item WITH p.id = item.productId WHERE o.id = :orderId")
                .setParameter("orderId", order.getId())
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getAllCustomersOrders(Customer customer) {
        return (List<Order>) getSessionFactory().getCurrentSession()
                .createQuery("SELECT o FROM Order o WHERE o.customerId = :customerId")
                .setParameter("customerId", customer.getId())
                .list();
    }
}
