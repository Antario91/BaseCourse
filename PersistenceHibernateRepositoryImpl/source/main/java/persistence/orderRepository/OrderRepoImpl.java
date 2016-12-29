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
    public List<Order> getOrders(String orderCustomerId) {
        if (orderCustomerId == null) {
            throw new NullIdException();
        }
        List<Order> orders = (List<Order>) getSessionFactory().getCurrentSession()
                .createCriteria(Order.class)
                .add(Restrictions.ilike("customerId", "%" + orderCustomerId + "%"))
                .list();
        return orders;
    }
}
