package persistence.productRepository;

import domain.Entity;
import domain.NullIdException;
import domain.order.OrderItem;
import domain.product.Product;
import org.hibernate.criterion.Restrictions;
import persistence.GenericRepoImpl;

import domain.product.ProductRepo;

import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductRepoImpl extends GenericRepoImpl<String, Product> implements ProductRepo {
    public ProductRepoImpl (Class<Product> entityClass,
                            String businessKeyPropertyName,
                            SessionFactory sessionFactory){
        super(entityClass, businessKeyPropertyName, sessionFactory);
    }

    //TODO add PAGINATION
    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) getSessionFactory().getCurrentSession()
                .createQuery("FROM Product")
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getProducts(List<String> productId) {
        if (productId == null) {
            throw new NullIdException();
        }
        StringBuilder builder = new StringBuilder();
        Iterator<String> itr = productId.iterator();
        builder.append("\'");
        while (itr.hasNext()) {
            String temp = itr.next();
            if (temp != null) {
                builder.append(temp);
            }
            if (itr.hasNext()) {
                builder.append("\',\'");
            } else {
                builder.append("\'");
            }
        }

        return getSessionFactory().getCurrentSession()
                .createQuery("FROM Product WHERE name in (" + builder + ")")
                .list();
    }


}
