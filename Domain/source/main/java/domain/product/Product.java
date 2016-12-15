package domain.product;

import domain.Entity;
import domain.exceptions.DateIntersectionInProductPriceException;

import java.util.*;

public class Product extends Entity<Long, String> {
    private String name;
    private String units;
    private List<ProductPrice> productPrices;

    private Product () {}

    public Product(String name, String units) {
        this.name = name;
        this.units = units;
        productPrices = new ArrayList<ProductPrice>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
        if ( !checkProduct() ){
            throw new DateIntersectionInProductPriceException();
        }
    }

    public long getProductPrice(Date placingDate) {
        long price = 0;
        for (ProductPrice productPrice : productPrices) {
            if (placingDate.after(productPrice.getStartEffectDay()) && placingDate.before(productPrice.getEndEffectDay())) {
                price = productPrice.getPrice();
            }
        }
        return price;
    }

    @Override
    public String getBusinessKey() {
        return name;
    }

    public boolean checkProduct() {
        for (int i = 0; i < productPrices.size(); i++) {
            for (int j = 0; j < productPrices.size(); j++) {
                if (i == j) {
                    continue;
                }

                if (
                        (productPrices.get(i).getStartEffectDay()
                                .after(productPrices.get(j).getStartEffectDay()) &&
                                productPrices.get(i).getStartEffectDay()
                                        .before(productPrices.get(j).getEndEffectDay())) ||

                                (productPrices.get(i).getEndEffectDay()
                                        .after(productPrices.get(j).getStartEffectDay()) &&
                                        productPrices.get(i).getEndEffectDay()
                                                .before(productPrices.get(j).getEndEffectDay())) ||

                                productPrices.get(i).getStartEffectDay()
                                        .equals(productPrices.get(j).getStartEffectDay()) ||
                                productPrices.get(i).getStartEffectDay()
                                        .equals(productPrices.get(j).getEndEffectDay()) ||

                                productPrices.get(i).getEndEffectDay()
                                        .equals(productPrices.get(j).getStartEffectDay()) ||
                                productPrices.get(i).getEndEffectDay()
                                        .equals(productPrices.get(j).getEndEffectDay())

                        ) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if ( getId().equals(product.getId()) ) return false;
        if (!name.equals(product.name)) return false;
        if (!units.equals(product.units)) return false;
        return productPrices.equals(product.productPrices);

    }

    @Override
    public int hashCode() {
        int result = 31 + getId().hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + units.hashCode();
        result = 31 * result + productPrices.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", units='" + units + '\'' +
                '}';
    }
}
