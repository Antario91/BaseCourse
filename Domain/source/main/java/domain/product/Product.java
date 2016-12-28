package domain.product;

import domain.Entity;
import domain.ContractViolationException;
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

    public Product(String name, String units, ProductPrice... productPrices) throws ContractViolationException, DateIntersectionInProductPriceException {
        validateConstructorsParams(name, units, productPrices);
        this.productPrices = Arrays.asList(productPrices);
        isIntersectProductPricesEffectDays(this.productPrices);
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

    public void addProductPrices(ProductPrice... newProductPrices) throws ContractViolationException, NotValidStartEffectDayException, DateIntersectionInProductPriceException {
        validateNewProductPrices(newProductPrices);
        List<ProductPrice> temp = new ArrayList<ProductPrice>(productPrices);
        temp.addAll(Arrays.asList(newProductPrices));
        isIntersectProductPricesEffectDays(temp);
        productPrices.addAll(Arrays.asList(newProductPrices));
    }

    public void deleteProductPrices(ProductPrice... currentProductPrices) throws ContractViolationException {
        validateParamProductPrices(currentProductPrices);
        List<ProductPrice> tempPrices = Arrays.asList(currentProductPrices);
        productPrices.removeAll(tempPrices);
    }

    /**
     * @see Product#getReverseProductPricesComparator  sort begining from latest day
     */
    public double getProductPrice(Date dateOfInterest) throws NoAvailableProductPriceException {
        double price = 0.0;

        List<ProductPrice> checkableProductPrices = new ArrayList<ProductPrice>(productPrices);
        Collections.sort(checkableProductPrices, getReverseProductPricesComparator());

        Iterator<ProductPrice> itr = checkableProductPrices.iterator();
        if (itr.hasNext()) {
            ProductPrice currentPrice = itr.next();
            while (dateOfInterest.before(currentPrice.getStartEffectDay()) && itr.hasNext()) {
                currentPrice = itr.next();
                if (dateOfInterest.before(currentPrice.getStartEffectDay()) && !itr.hasNext()) {
                    throw new NoAvailableProductPriceException();
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

    private boolean isIntersectProductPricesEffectDays(List<ProductPrice> productPrices) throws DateIntersectionInProductPriceException {
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

    private void validateConstructorsParams(String name, String units, ProductPrice... productPrices) throws ContractViolationException {
        if (name == null) {
            throw new ContractViolationException("Parameter \"name\" is NULL");
        }
        if (units == null) {
            throw new ContractViolationException("Parameter \"units\" is NULL");
        }
        validateParamProductPrices(productPrices);
    }

    private void validateNewProductPrices(ProductPrice... productPrices) throws ContractViolationException, NotValidStartEffectDayException{
        validateParamProductPrices(productPrices);
        for (ProductPrice productPrice : productPrices) {
            if (productPrice.getStartEffectDay().before(new Date())) {
                throw new NotValidStartEffectDayException();
            }
        }
    }

    private void validateParamProductPrices(ProductPrice... productPrices) throws ContractViolationException {
        if (productPrices == null) {
            throw new ContractViolationException("Parameter \"productPrices\" is NULL");
        }
    }
}
