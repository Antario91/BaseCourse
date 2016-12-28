package domain.product;

import domain.GenericRepo;
import domain.NullIdException;

import java.util.List;

public interface ProductRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Product> getAllProducts ();
    List<Product> getProductsByIds (List<String> productId) throws NullIdException;
}