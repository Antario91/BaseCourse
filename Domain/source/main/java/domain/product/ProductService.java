package domain.product;

import domain.order.exceptions.OrderDoesNotExistException;
import domain.product.exceptions.DateIntersectionInProductPriceException;
import domain.product.exceptions.NotValidStartEffectDayException;
import domain.product.exceptions.ProductAlreadyExistException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface ProductService {
    void createProduct(String name, String units, ProductPrice ... productPrices) throws ProductAlreadyExistException,
            DateIntersectionInProductPriceException;

    Product getProduct(String name) throws ProductDoesNotExistException;

    List<Product> getAllProducts();

    List<Product> getProducts(List<String> productId);

    void addProductPrices(String productName, ProductPrice ... productPrices) throws ProductDoesNotExistException,
            DateIntersectionInProductPriceException, NotValidStartEffectDayException;

    void deleteProductPrices(String productName, ProductPrice ... productPrices) throws ProductDoesNotExistException;

    void deleteProduct(String productName) throws ProductDoesNotExistException, OrderDoesNotExistException;
}
