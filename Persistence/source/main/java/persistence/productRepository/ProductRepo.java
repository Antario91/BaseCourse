package persistence.productRepository;

import domain.product.Product;
import persistence.GenericRepo;

import java.util.List;

public interface ProductRepo extends GenericRepo<Long, String> {
    List<Product> getAllProducts ();
}
