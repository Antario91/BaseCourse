package domain.product;

import domain.Entity;
import domain.product.exceptions.*;

import java.util.*;

public class Product extends Entity {
    private final String name;
    private final String units;
    private List<ProductPrice> productPrices;

    private Product() {
        name = null;
        units = null;
    }

    public Product(String name, String units, ProductPrice... productPrices) throws DateIntersectionInProductPriceException {
        checkConstructorParametersForNull(name, units, productPrices);
        this.productPrices = Arrays.asList(productPrices);
        isIntersectProductPricesEffectDays();
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

    public void addProductPrices(ProductPrice... newProductPrices) throws NotValidStartEffectDayException, DateIntersectionInProductPriceException {
        checkParamProductPricesForNull(newProductPrices);
        areNewProductPricesUpToToday(newProductPrices);
        isIntersectProductPricesEffectDays(Arrays.asList(newProductPrices));
        productPrices.addAll(Arrays.asList(newProductPrices));
    }

    public void deleteProductPrices(ProductPrice... currentProductPrices) {
        checkParamProductPricesForNull(currentProductPrices);
        productPrices.removeAll(Arrays.asList(currentProductPrices));
    }

    /**
     * @see Product#getReverseProductPricesComparator  sort begining from latest day
     */
    public double getProductPrice(Date dateOfInterest) throws NotAvailableProductPriceException {
        double price = 0.0;

        List<ProductPrice> checkableProductPrices = new ArrayList<ProductPrice>(productPrices);
        Collections.sort(checkableProductPrices, getReverseProductPricesComparator());

        ProductPrice lastProductPrice = null;
        for (ProductPrice currentPrice : checkableProductPrices) {
            if ( dateOfInterest.after(currentPrice.getStartEffectDay()) ) {
                price = currentPrice.getPrice();
            }
            lastProductPrice = currentPrice;
        }

        if ( dateOfInterest.before(lastProductPrice.getStartEffectDay()) ) {
            throw new NotAvailableProductPriceException();
        }

        return price;
    }

    private Comparator<ProductPrice> getReverseProductPricesComparator() {
        return new Comparator<ProductPrice>() {
            @Override
            public int compare(ProductPrice o1, ProductPrice o2) {
                if (o1.getStartEffectDay().before(o2.getStartEffectDay())) {
                    return 1;
                } else if (o1.getStartEffectDay().after(o2.getStartEffectDay())) {
                    return -1;
                }
                return 0;
            }
        };
    }

    private boolean isIntersectProductPricesEffectDays(List<ProductPrice> newProductPrices) throws DateIntersectionInProductPriceException {
        HashSet<Date> checkableDate = new HashSet<Date>();
        for (ProductPrice currentPrice : this.productPrices) {
            checkableDate.add(currentPrice.getStartEffectDay());
        }

        for (ProductPrice productPrice : newProductPrices) {
            if (checkableDate.contains(productPrice.getStartEffectDay())) {
                throw new DateIntersectionInProductPriceException();
            } else {
                checkableDate.add(productPrice.getStartEffectDay());
            }
        }

        return false;
    }

    private boolean isIntersectProductPricesEffectDays() throws DateIntersectionInProductPriceException {
        HashSet<Date> checkableDate = new HashSet<Date>();
        for (ProductPrice productPrice : productPrices) {
            if (checkableDate.contains(productPrice.getStartEffectDay())) {
                throw new DateIntersectionInProductPriceException();
            } else {
                checkableDate.add(productPrice.getStartEffectDay());
            }
        }
        return false;
    }

    private boolean areNewProductPricesUpToToday(ProductPrice... productPrices) throws NotValidStartEffectDayException{
        checkParamProductPricesForNull(productPrices);
        for (ProductPrice productPrice : productPrices) {
            if (productPrice.getStartEffectDay().before(new Date())) {
                throw new NotValidStartEffectDayException();
            }
        }
        return false;
    }

    private void checkConstructorParametersForNull(String name, String units, ProductPrice... productPrices) {
        if (name == null) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        if (units == null) {
            throw new IllegalArgumentException("Parameter \"units\" is NULL");
        }
        checkParamProductPricesForNull(productPrices);
    }

    private void checkParamProductPricesForNull(ProductPrice... productPrices) {
        if (productPrices == null) {
            throw new IllegalArgumentException("Parameter \"productPrices\" is NULL");
        }
    }
}
