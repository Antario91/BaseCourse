package domain.product;

import domain.Entity;
import domain.product.exceptions.*;

import java.util.*;

public class Product extends Entity {
    private final String name;
    private final String units;
    private List<ProductPrice> productPrices;

    private Product () {
        name = null;
        units = null;
    }

    public Product(String name, String units, ProductPrice ... productPrices) throws NullProductsNameException, NullUnitsException, NullProductPricesException, DateIntersectionInProductPriceException {
        ProductService.validateIncomingDataInProductsConstructor(name, units, productPrices);
        this.productPrices = ProductService.validateAndFormProductPrices(productPrices);
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public String getUnits() {
        return units;
    }

    public List<ProductPrice> getProductPrices() {
        return new ArrayList<ProductPrice>(productPrices);
    }

    public void addProductPrices(ProductPrice ... newProductPrices) throws NullProductPricesException, NullNewProductPricesException, NotValidStartEffectDayException, DateIntersectionInProductPriceException {
        ProductService.validateAndAddNewProductPrices(this.productPrices, newProductPrices);
    }

    public void deleteProductPrices(ProductPrice ... currentProductPrices) throws NullProductPricesException, NullCurrentProductPricesException {
        ProductService.validateAndDeleteCurrentProductPrices(this.productPrices, currentProductPrices);
    }
}
