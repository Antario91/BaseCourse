package domain.product;

import domain.order.exceptions.OrderDoesNotExistException;
import domain.product.exceptions.*;

import java.util.*;

public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        if (productRepo == null) {
            throw new IllegalArgumentException("Parameter \"productRepo\" is NULL");
        }
        this.productRepo = productRepo;
    }

    public void createProduct(String name, String units, ProductPrice ... productPrices) throws ProductAlreadyExistException,
            DateIntersectionInProductPriceException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Product product = (Product) productRepo.get(name);
        if (product != null) {
            throw new ProductAlreadyExistException();
        }

        productRepo.add(new Product(name, units, productPrices));
    }

    public Product getProduct(String name) throws ProductDoesNotExistException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Product product = (Product) productRepo.get(name);
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    @Override
    public List<Product> getProducts(List<String> productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Parameter \"productId\" is NULL");
        }
        return productRepo.getProducts(productId);
    }

    public void addProductPrices(String productName, ProductPrice ... productPrices) throws ProductDoesNotExistException,
            DateIntersectionInProductPriceException, NotValidStartEffectDayException {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Product product = (Product) productRepo.get(productName);
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
        product.addProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProductPrices(String productName, ProductPrice ... productPrices) throws ProductDoesNotExistException {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Product product = (Product) productRepo.get(productName);
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
        product.deleteProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProduct(String productName) throws ProductDoesNotExistException, OrderDoesNotExistException {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Product product = (Product) productRepo.get(productName);
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
        ProductPrice[] productPrices = new ProductPrice[product.getProductPrices().size()];
        deleteProductPrices(productName, product.getProductPrices().toArray(productPrices));
        productRepo.delete(product);
    }
}
