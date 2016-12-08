package domain.product;

import domain.Entity;

import java.util.HashSet;
import java.util.Set;

public class Product extends Entity<Long, String> {
    private String name;
    private String units;
    private Set<ProductPrice> productPrices;

    public Product(String name, String units) {
        this.name = name;
        this.units = units;
        productPrices = new HashSet<ProductPrice>();
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

    public Set<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void addProductPrice(ProductPrice productPrice) {
        productPrices.add(productPrice);
    }

    @Override
    public String getBusinessKey() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (getId() != product.getId()) return false;
        if (!name.equals(product.name)) return false;
        if (!units.equals(product.units)) return false;
        return productPrices.equals(product.productPrices);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
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
