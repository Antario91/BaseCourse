package domain.product;

import domain.product.exceptions.*;

import java.util.*;

/**
 * Created by olgo on 21-Dec-16.
 */
public class ProductService {

    public static void validateIncomingDataInProductsConstructor(String name, String units, ProductPrice ... productPrices) throws NullProductsNameException, NullUnitsException, NullProductPricesException {
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

    public static void validateIncomingDataInProductPricesConstructor(double price, Date startEffectDay) throws NullPriceException, NullStartEffectDayException {
        if (price <= 0){
            throw new NullPriceException();
        }
        if (startEffectDay == null) {
            throw new NullStartEffectDayException();
        }
    }

    public static List<ProductPrice> validateAndFormProductPrices(ProductPrice ... productPrices) throws NullProductPricesException, DateIntersectionInProductPriceException {
        if (productPrices == null) {
            throw new NullProductPricesException();
        }
        if (productPrices.length == 0) {
            return new ArrayList<ProductPrice>();
        }
        List<ProductPrice> temp = Arrays.asList(productPrices);
        isIntersectProductPricesEffectDays(temp);
        return temp;
    }

    public static void validateAndAddNewProductPrices(List<ProductPrice> productPrices, ProductPrice ... newProductPrices) throws NullProductPricesException, NullNewProductPricesException, NotValidStartEffectDayException, DateIntersectionInProductPriceException {
        if (productPrices == null) {
            throw new NullProductPricesException();
        }
        if (newProductPrices == null) {
            throw new NullNewProductPricesException();
        }
        if (newProductPrices.length != 0) {
            Date lastStartEffectDay = new Date(0);
            for (ProductPrice currentProductPrice : productPrices) {
                if ( lastStartEffectDay.before(currentProductPrice.getStartEffectDay()) ) {
                    lastStartEffectDay = currentProductPrice.getStartEffectDay();
                }
            }

            for (ProductPrice currentProductPrice : newProductPrices) {
                if ( lastStartEffectDay.after(currentProductPrice.getStartEffectDay()) ) {
                    throw new NotValidStartEffectDayException();
                }
            }

            List<ProductPrice> temp = new ArrayList<ProductPrice>(productPrices);
            temp.addAll(Arrays.asList(newProductPrices));
            isIntersectProductPricesEffectDays(temp);

            productPrices.addAll(Arrays.asList(newProductPrices));
        }
    }

    public static void validateAndDeleteCurrentProductPrices(List<ProductPrice> productPrices, ProductPrice ... currentProductPrices) throws NullProductPricesException, NullCurrentProductPricesException {
        if (productPrices == null) {
            throw new NullProductPricesException();
        }
        if (currentProductPrices == null) {
            throw new NullCurrentProductPricesException();
        }
        if (currentProductPrices.length != 0) {
            List<ProductPrice> tempPrices = Arrays.asList(currentProductPrices);
            productPrices.removeAll(tempPrices);
        }
    }

    private static boolean isIntersectProductPricesEffectDays(List<ProductPrice> productPrices) throws DateIntersectionInProductPriceException {
        HashSet<Date> checkableDate = new HashSet<Date>();
        for (ProductPrice productPrice : productPrices) {
            if (checkableDate.contains(productPrice.getStartEffectDay())) {
                throw new DateIntersectionInProductPriceException();
            } else {
                checkableDate.add(productPrice.getStartEffectDay());
            }
        }
        return true;
    }

    /**
      * @see ProductService#getReverseProductPricesComparator  sort begining from latest day
      */
    public static double getProductPrice(Product product, Date placingDate) throws NoAvailableProductPriceException {
        double price = 0.0;

        List <ProductPrice> checkableProductPrices = new ArrayList<ProductPrice>(product.getProductPrices());
        Collections.sort(checkableProductPrices, getReverseProductPricesComparator());

        Iterator<ProductPrice> itr = checkableProductPrices.iterator();
        if (itr.hasNext()) {
            ProductPrice currentPrice = itr.next();
            while (placingDate.before(currentPrice.getStartEffectDay()) && itr.hasNext()) {
                currentPrice = itr.next();
                if (placingDate.before(currentPrice.getStartEffectDay()) && !itr.hasNext()) {
                    throw new NoAvailableProductPriceException();
                }
            }
            price = currentPrice.getPrice();
        }

        return price;
    }

    private static Comparator<ProductPrice> getReverseProductPricesComparator() {
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
}
