package persistence.productRepository;

import domain.Entity;
import domain.order.OrderItem;
import domain.product.Product;
import persistence.GenericRepoImpl;

import domain.repositories.productRepository.ProductRepo;
import domain.repositories.EntityAlreadyExistException;
import domain.repositories.EntityDoesNotExistException;

import org.hibernate.SessionFactory;

import java.util.ArrayList;
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

    @Override
    public List<Product> getOrdersProducts(List<OrderItem> orderItems) {
        List<Product> products = new ArrayList<Product>();
        for (OrderItem item : orderItems) {
            products.add( (Product) getSessionFactory().getCurrentSession()
                    .get(Product.class, item.getProductId()));
        }
        return products;
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public List<Product> getOrdersProducts(long billingNumber) {
//        return (List<Product>) getSessionFactory().getCurrentSession()
//                .createQuery("SELECT p FROM Product p WHERE p.id in (select item.productId FROM Order o JOIN o.orderItems item WHERE o.billingNumber = :billingNumber)")
//                .setParameter("billingNumber", billingNumber)
//                .list();
//    }
}
