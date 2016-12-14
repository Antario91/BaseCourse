package persistence.productRepository;

import domain.Entity;
import persistence.GenericRepoImpl;

import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

public class ProductRepoImpl extends GenericRepoImpl<Long, String> implements ProductRepo {
    public ProductRepoImpl (Class<Entity<Long, String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory){
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }
}
