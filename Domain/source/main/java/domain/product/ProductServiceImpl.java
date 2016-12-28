package domain.product;

import domain.ContractViolationException;
import domain.order.OrderService;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.product.exceptions.*;

import java.util.*;

public class ProductServiceImpl implements ProductService {
    private OrderService orderService;
    private ProductRepo productRepo;

    public ProductServiceImpl(OrderService orderService, ProductRepo productRepo) throws ContractViolationException {
        if (orderService == null) {
            throw new ContractViolationException("Parameter \"orderService\" is NULL");
        }
        if (productRepo == null) {
            throw new ContractViolationException("Parameter \"productRepo\" is NULL");
        }
        this.orderService = orderService;
        this.productRepo = productRepo;
    }

    public void createProduct(String name, String units, ProductPrice ... productPrices) throws ContractViolationException, ProductAlreadyExistException,
            DateIntersectionInProductPriceException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        if (product != null) {
            throw new ProductAlreadyExistException();
        }

        productRepo.add(new Product(name, units, productPrices));
    }

    public Product getProduct(String name) throws ContractViolationException, ProductDoesNotExistException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        validateProductExistence(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    @Override
    public List<Product> getProductsById(List<String> productId) throws ContractViolationException {
        if (productId == null) {
            throw new ContractViolationException("Parameter \"productId\" is NULL");
        }
        return productRepo.getProductsByIds(productId);
    }

    public void addProductPrices(String productName, ProductPrice ... productPrices) throws ContractViolationException, ProductDoesNotExistException,
            DateIntersectionInProductPriceException, NotValidStartEffectDayException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.addProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProductPrices(String productName, ProductPrice ... productPrices) throws ContractViolationException, ProductDoesNotExistException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.deleteProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProduct(String productName) throws ContractViolationException, ProductDoesNotExistException, OrderDoesNotExistException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        ProductPrice[] productPrices = new ProductPrice[product.getProductPrices().size()];
        deleteProductPrices(productName, product.getProductPrices().toArray(productPrices));
        productRepo.delete(product);
    }

    private void validateParamName(String name) throws ContractViolationException {
        if (name == null || name.isEmpty()) {
            throw new ContractViolationException("Parameter \"name\" is NULL");
        }
    }

    private void validateProductExistence(Product product) throws ProductDoesNotExistException {
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
    }
}
