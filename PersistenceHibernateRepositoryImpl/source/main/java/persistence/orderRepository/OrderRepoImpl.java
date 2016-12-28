package persistence.orderRepository;

import domain.NullIdException;
import domain.order.Order;
import org.hibernate.criterion.Restrictions;
import persistence.GenericRepoImpl;
import domain.order.OrderRepo;

import org.hibernate.SessionFactory;

import java.util.List;

public class OrderRepoImpl extends GenericRepoImpl<String, Order> implements OrderRepo {
    public OrderRepoImpl(Class<Order> entityClass,
                         String businessKeyPropertyName,
                         SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        if (customerId == null) {
            throw new NullIdException();
        }
        List<Order> orders = (List<Order>) getSessionFactory().getCurrentSession()
                .createCriteria(Order.class)
                .add(Restrictions.ilike("customerId", "%" + customerId + "%"))
                .list();
        return orders;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getOrdersByProductId(String productId) {
        if (productId == null) {
            throw new NullIdException();
        }
        return (List<Order>) getSessionFactory().getCurrentSession()
                .createQuery("SELECT o FROM Order o JOIN o.orderItems item WHERE item.productId = :productId")
                .setParameter("productId", productId)
                .list();
    }
}
