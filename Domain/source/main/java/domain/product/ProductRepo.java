package domain.product;

import domain.GenericRepo;

import java.util.List;

public interface ProductRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Product> getAllProducts();
    List<Product> getProducts(List<String> productId);
}