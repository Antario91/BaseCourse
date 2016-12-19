package domain.repositories.productRepository;

import domain.product.Product;
import domain.repositories.GenericRepo;

import java.util.List;

public interface ProductRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Product> getAllProducts ();
}