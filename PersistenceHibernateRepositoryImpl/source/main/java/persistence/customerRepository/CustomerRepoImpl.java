package persistence.customerRepository;

import domain.Entity;
import domain.customer.Customer;
import persistence.GenericRepoImpl;

import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.List;

public class CustomerRepoImpl extends GenericRepoImpl<Long, String> implements CustomerRepo {
    public CustomerRepoImpl(Class<Entity<Long, String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    //TODO add PAGINATION
    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) getSessionFactory().getCurrentSession()
                .createQuery("FROM Customer")
                .list();
    }
}
