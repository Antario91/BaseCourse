package persistence.customerRepository;

import domain.Entity;
import domain.customer.Customer;
import domain.repositories.customerRepository.CustomerRepo;
import persistence.GenericRepoImpl;

import domain.repositories.EntityAlreadyExistException;
import domain.repositories.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.List;

public class CustomerRepoImpl extends GenericRepoImpl<String> implements CustomerRepo {
    public CustomerRepoImpl(Class<Entity> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    public void add (Entity entity) {
        if (get())
        get(entity.getBusinessKey());
        throw entityAlreadyExistException;

        sessionFactory.getCurrentSession()
                .save(entity);
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
