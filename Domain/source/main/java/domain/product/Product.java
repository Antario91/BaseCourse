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
        if (name == null) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        if (units == null) {
            throw new IllegalArgumentException("Parameter \"units\" is NULL");
        }
        if (productPrices == null) {
            throw new IllegalArgumentException("Parameter \"productPrices\" is NULL");
        }
        if ( isIntersectProductPricesEffectDays(Arrays.asList(productPrices)) ) {
            throw new DateIntersectionInProductPriceException();
        }
        this.productPrices = Arrays.asList(productPrices);
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
        if (productPrices == null) {
            throw new IllegalArgumentException("Parameter \"productPrices\" is NULL");
        }
        if (areNewProductPricesUpToToday(newProductPrices)) {
            throw new NotValidStartEffectDayException();
        }
        if ( isIntersectProductPricesEffectDays(Arrays.asList(newProductPrices)) ) {
            throw new DateIntersectionInProductPriceException();
        }
        productPrices.addAll(Arrays.asList(newProductPrices));
    }

    public void deleteProductPrices(ProductPrice... currentProductPrices) {
        if (productPrices == null) {
            throw new IllegalArgumentException("Parameter \"productPrices\" is NULL");
        }
        productPrices.removeAll(Arrays.asList(currentProductPrices));
    }

    /**
     * @see Product#getReverseProductPricesComparator  sort begining from latest day
     */
    public double getProductPrice(Date dateOfInterest) throws NotAvailableProductPriceException {
        double price = 0.0;

        List<ProductPrice> checkableProductPrices = new ArrayList<ProductPrice>(productPrices);
        Collections.sort(checkableProductPrices, getReverseProductPricesComparator());

        Iterator<ProductPrice> itr = checkableProductPrices.iterator();
        if (itr.hasNext()) {
            ProductPrice currentPrice = itr.next();
            while (dateOfInterest.before(currentPrice.getStartEffectDay()) && itr.hasNext()) {
                currentPrice = itr.next();
                if (dateOfInterest.before(currentPrice.getStartEffectDay()) && !itr.hasNext()) {
                    throw new NotAvailableProductPriceException();
                }
            }
            price = currentPrice.getPrice();
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

        if (this.productPrices != null) {
            for (ProductPrice currentPrice : this.productPrices) {
                checkableDate.add(currentPrice.getStartEffectDay());
            }
        }

        for (ProductPrice productPrice : newProductPrices) {
            if (checkableDate.contains(productPrice.getStartEffectDay())) {
                return true;
            } else {
                checkableDate.add(productPrice.getStartEffectDay());
            }
        }

        return false;
    }

    private boolean areNewProductPricesUpToToday(ProductPrice... productPrices) throws NotValidStartEffectDayException {
        for (ProductPrice productPrice : productPrices) {
            if (productPrice.getStartEffectDay().before(new Date())) {
                return true;
            }
        }
        return false;
    }
}
