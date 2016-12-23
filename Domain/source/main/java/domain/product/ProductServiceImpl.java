package domain.product;

import domain.ParamIsNullException;
import domain.product.exceptions.*;

import java.util.*;

public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) throws ParamIsNullException {
        if (productRepo == null) {
            throw new ParamIsNullException("productRepo");
        }
        this.productRepo = productRepo;
    }

    public void createProduct(String name, String units, ProductPrice ... productPrices) throws ParamIsNullException, ProductAlreadyExistException,
            DateIntersectionInProductPriceException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        if (product != null) {
            throw new ProductAlreadyExistException();
        }

        productRepo.add(new Product(name, units, productPrices));
    }

    public Product getProduct(String name) throws ParamIsNullException, ProductDoesNotExistException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        validateProductExistence(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public void addProductPrices(String productName, ProductPrice ... productPrices) throws ParamIsNullException, ProductDoesNotExistException,
            DateIntersectionInProductPriceException, NotValidStartEffectDayException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.addProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProductPrices(String productName, ProductPrice ... productPrices) throws ParamIsNullException, ProductDoesNotExistException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.deleteProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProduct(String productName) throws ParamIsNullException, ProductDoesNotExistException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        productRepo.delete(product);
    }

    private void validateParamName(String name) throws ParamIsNullException {
        if (name == null || name.isEmpty()) {
            throw new ParamIsNullException("name");
        }
    }

    private void validateProductExistence(Product product) throws ProductDoesNotExistException {
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
    }
}
