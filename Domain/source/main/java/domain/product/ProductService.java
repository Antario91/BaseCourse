package domain.product;

import domain.product.exceptions.*;

import java.util.*;

public class ProductService {

    static void validateProductsConstructorsParams(String name, String units, ProductPrice ... productPrices) throws NullProductsNameException, NullUnitsException, NullProductPricesException {
        if (name == null || name.isEmpty()) {
            throw new NullProductsNameException();
        }
        if (units == null || units.isEmpty()) {
            throw new NullUnitsException();
        }
        if (productPrices == null) {
            throw new NullProductPricesException();
        }
    }

    static void validateProductPricesConstructorsParams(double price, Date startEffectDay) throws NullPriceException, NullStartEffectDayException {
        if (price <= 0){
            throw new NullPriceException();
        }
        if (startEffectDay == null) {
            throw new NullStartEffectDayException();
        }
    }

    static void validateNewProductPrices (ProductPrice ... newProductPrices) throws NullNewProductPricesException, NotValidStartEffectDayException {
        if (newProductPrices == null) {
            throw new NullNewProductPricesException();
        }
        for (ProductPrice productPrice : newProductPrices) {
            if (productPrice.getStartEffectDay().before(new Date())) {
                throw new NotValidStartEffectDayException();
            }
        }
    }

    static void validateCurrentProductPrices (ProductPrice ... currentProductPrices) throws NullCurrentProductPricesException {
        if (currentProductPrices == null) {
            throw new NullCurrentProductPricesException();
        }
    }
}
