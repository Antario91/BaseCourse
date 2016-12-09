package persistence.productRepository;

import domain.Entity;
import domain.product.Product;
import org.hibernate.SessionFactory;
import persistence.GenericRepoImpl;
import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

/**
 * Created by olgo on 08-Dec-16.
 */
public class ProductRepoImpl extends GenericRepoImpl<Long, String> implements ProductRepo {
    public ProductRepoImpl (Class<Entity<Long, String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory){
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    @Override
    public Product getById(Long id) {
        return (Product) getSessionFactory().getCurrentSession()
                .get(getEntityClass(), id);
    }
}
