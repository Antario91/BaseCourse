package persistence.orderRepository;

import domain.Entity;
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
                .createQuery("FROM Product p LEFT JOIN Order.orderItems orderItem " +
                        "ON p.id = orderItem.productId WHERE Order.id = :orderId")
                .setParameter("orderId", order.getId())
                .list();
    }
    /*return (List<Product>) getSessionFactory().getCurrentSession()
                .createSQLQuery( "SELECT * FROM PRODUCT LEFT OUTER JOIN ORDER_ITEMS " +
                                         "ON PRODUCT.id = ORDER_ITEMS.PRODUCT_ID WHERE ORDER_ITEMS.id = " + order.getId() ).list();*/
}
