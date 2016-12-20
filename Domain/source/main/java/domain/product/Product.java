package domain.product;

import domain.Entity;

import java.util.*;

public class Product extends Entity<String> {
    private final String name;
    private final String units;
    private List<ProductPrice> productPrices;

    private Product () {
        name = null;
        units = null;
    }

    public Product(String name, String units) {
        if (name == null || name.isEmpty() || units == null || units.isEmpty()){
            throw new IllegalArgumentException();
        }
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

    public void setProductPrices(List<ProductPrice> productPrices) throws DateIntersectionInProductPriceException {
        if (productPrices == null) {
            throw new IllegalArgumentException();
        }
        isIntersectProductPricesEffectDays(productPrices);
        this.productPrices = productPrices;
    }

    @Override
    public String getBusinessKey() {
        return name;
    }

    public double getProductPrice(Date placingDate) {
        double price = 0.0;

        List <ProductPrice> checkableProductPrices = new ArrayList<ProductPrice>(productPrices);
        Collections.sort(checkableProductPrices, getProductPricesComparator());

        Iterator<ProductPrice> itr = checkableProductPrices.iterator();
        if (itr.hasNext()) {
            ProductPrice currentPrice = itr.next();
            while (placingDate.after(currentPrice.getEndEffectDay()) && itr.hasNext()) {
                currentPrice = itr.next();
            }
            price = currentPrice.getPrice();
        }

        return price;
    }

    private boolean isIntersectProductPricesEffectDays (List<ProductPrice> productPrices) throws DateIntersectionInProductPriceException {
        HashSet<Date> checkableDate = new HashSet<Date>();
        for (ProductPrice productPrice : productPrices) {
            if (checkableDate.contains(productPrice.getEndEffectDay())) {
                throw new DateIntersectionInProductPriceException();
            } else {
                checkableDate.add(productPrice.getEndEffectDay());
            }
        }
        return true;
    }

    private Comparator<ProductPrice> getProductPricesComparator () {
        return new Comparator<ProductPrice>() {
            @Override
            public int compare(ProductPrice o1, ProductPrice o2) {
                if (o1.getEndEffectDay().before(o2.getEndEffectDay())){
                    return -1;
                } else if (o1.getEndEffectDay().after(o2.getEndEffectDay())){
                    return 1;
                }
                return 0;
            }
        };
    }
}
