package domain.product;

import domain.ParamIsNullException;
import domain.product.exceptions.DateIntersectionInProductPriceException;
import domain.product.exceptions.NotValidStartEffectDayException;
import domain.product.exceptions.ProductAlreadyExistException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface ProductService {
    void createProduct(String name, String units, ProductPrice ... productPrices) throws ParamIsNullException, ProductAlreadyExistException,
            DateIntersectionInProductPriceException;

    Product getProduct(String name) throws ParamIsNullException, ProductDoesNotExistException;

    List<Product> getAllProducts();

    void addProductPrices(String productName, ProductPrice ... productPrices) throws ParamIsNullException, ProductDoesNotExistException,
            DateIntersectionInProductPriceException, NotValidStartEffectDayException;

    void deleteProductPrices(String productName, ProductPrice ... productPrices) throws ParamIsNullException, ProductDoesNotExistException;

    void deleteProduct(String productName) throws ParamIsNullException, ProductDoesNotExistException;
}
