package persistence.orderRepository;

import domain.Entity;
import org.hibernate.SessionFactory;
import domain.order.Order;
import persistence.GenericRepoImpl;
import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

/**
 * Created by olgo on 08-Dec-16.
 */
public class OrderRepoImpl extends GenericRepoImpl<Long, Long> implements OrderRepo {
    public OrderRepoImpl(Class<Entity<Long, Long>> entityClass,
                         String businessKeyPropertyName,
                         EntityAlreadyExistException entityAlreadyExistException,
                         EntityDoesNotExistException entityDoesNotExistException,
                         SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    @Override
    public Order getById(Long id) {
        return (Order) getSessionFactory().getCurrentSession()
                .get(getEntityClass(), id);
    }
}
