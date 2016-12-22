package domain.product;

import domain.product.exceptions.*;

import java.util.*;

public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    static void validateProductsConstructorsParams(String name, String units, ProductPrice ... productPrices) throws NullProductsNameException, NullUnitsException, NullProductPricesException {
        validateParamName(name);
        validateParamUnits(units);
        validateParamProductPrices(productPrices);
    }

    static void validateProductPricesConstructorsParams(double price, Date startEffectDay) throws NullPriceException, NullStartEffectDayException {
        if (price <= 0){
            throw new NullPriceException();
        }
        if (startEffectDay == null) {
            throw new NullStartEffectDayException();
        }
    }

    static void validateNewProductPrices(ProductPrice ... productPrices) throws NullProductPricesException, NotValidStartEffectDayException {
        validateParamProductPrices(productPrices);
        for (ProductPrice productPrice : productPrices) {
            if (productPrice.getStartEffectDay().before(new Date())) {
                throw new NotValidStartEffectDayException();
            }
        }
    }

    static void validateCurrentProductPrices(ProductPrice ... productPrices) throws NullProductPricesException {
        validateParamProductPrices(productPrices);
    }

    private static void validateParamName(String name) throws NullProductsNameException {
        if (name == null || name.isEmpty()) {
            throw new NullProductsNameException();
        }
    }

    private static void validateParamUnits(String units) throws NullUnitsException {
        if (units == null || units.isEmpty()) {
            throw new NullUnitsException();
        }
    }

    private static void validateParamProductPrices(ProductPrice ... productPrices) throws NullProductPricesException {
        if (productPrices == null) {
            throw new NullProductPricesException();
        }
    }

    private void validateProductExistence(Product product) throws ProductDoesNotExistException {
        if (product == null) {
            throw new ProductDoesNotExistException();
        }
    }

    public void createProduct(String name, String units, ProductPrice ... productPrices) throws NullProductsNameException, ProductAlreadyExistException, NullUnitsException, DateIntersectionInProductPriceException, NullProductPricesException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        if (product != null) {
            throw new ProductAlreadyExistException();
        }

        productRepo.add(new Product(name, units, productPrices));
    }

    public Product getProduct(String name) throws NullProductsNameException, ProductDoesNotExistException {
        validateParamName(name);
        Product product = (Product) productRepo.get(name);
        validateProductExistence(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public void addProductPrices(String productName, ProductPrice ... productPrices) throws NullProductsNameException, ProductDoesNotExistException, NullProductPricesException, DateIntersectionInProductPriceException, NotValidStartEffectDayException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.addProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProductPrices(String productName, ProductPrice ... productPrices) throws NullProductsNameException, ProductDoesNotExistException, NullProductPricesException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        product.deleteProductPrices(productPrices);
        productRepo.update(product);
    }

    public void deleteProduct(String productName) throws NullProductsNameException, ProductDoesNotExistException {
        validateParamName(productName);
        Product product = (Product) productRepo.get(productName);
        validateProductExistence(product);
        productRepo.delete(product);
    }
}
