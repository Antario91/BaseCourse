package persistence.productRepository;

import domain.Entity;
import domain.product.Product;
import persistence.GenericRepoImpl;

import domain.repositories.productRepository.ProductRepo;
import domain.repositories.EntityAlreadyExistException;
import domain.repositories.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.List;

public class ProductRepoImpl extends GenericRepoImpl<String> implements ProductRepo {
    public ProductRepoImpl (Class<Entity<String>> entityClass,
                            String businessKeyPropertyName,
                            EntityAlreadyExistException entityAlreadyExistException,
                            EntityDoesNotExistException entityDoesNotExistException,
                            SessionFactory sessionFactory){
        super(entityClass, businessKeyPropertyName, entityAlreadyExistException, entityDoesNotExistException, sessionFactory);
    }

    //TODO add PAGINATION
    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) getSessionFactory().getCurrentSession()
                .createQuery("FROM Product")
                .list();
    }
}
