package persistence.customerRepository;

import domain.Entity;
import domain.customer.Customer;
import domain.customer.CustomerRepo;
import persistence.GenericRepoImpl;

import domain.EntityAlreadyExistException;
import domain.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.List;

public class CustomerRepoImpl extends GenericRepoImpl<String> implements CustomerRepo {
    public CustomerRepoImpl(Class<Entity<String>> entityClass,
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
       List<Customer> customers = (List<Customer>) getSessionFactory().getCurrentSession()
               .createQuery("FROM Customer")
               .list();
        return customers;
    }
}
