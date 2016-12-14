package persistence.customerRepository;

import domain.Entity;
import persistence.GenericRepoImpl;

import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

public class CustomerRepoImpl extends GenericRepoImpl<Long, String> implements CustomerRepo {
    public CustomerRepoImpl(Class<Entity<Long, String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }
}
