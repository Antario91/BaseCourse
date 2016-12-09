package persistence.customerRepository;

import domain.Entity;
import domain.customer.Customer;
import org.hibernate.SessionFactory;
import persistence.GenericRepoImpl;
import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

/**
 * Created by olgo on 06-Dec-16.
 */
public class CustomerRepoImpl extends GenericRepoImpl<Long, String> implements CustomerRepo {
    public CustomerRepoImpl(Class<Entity<Long, String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    @Override
    public Customer getById(Long id) {
        return (Customer) getSessionFactory().getCurrentSession()
                .get(getEntityClass(), id);
    }
}
