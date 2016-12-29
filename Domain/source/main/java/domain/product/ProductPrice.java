package domain.product;

import java.util.Date;

public class ProductPrice {
    private final double price;
    private final Date startEffectDay;

    private ProductPrice () {
        price = 0;
        startEffectDay = null;
    }

    public ProductPrice(double price, Date startEffectDay) {
        if (price <= 0){
            throw new IllegalArgumentException("Parameter \"price\" is NULL");
        }
        if (startEffectDay == null) {
            throw new IllegalArgumentException("Parameter \"startEffectDay\" is NULL");
        }
        this.price = price;
        this.startEffectDay = startEffectDay;
    }

    public double getPrice() {
        return price;
    }

    public Date getStartEffectDay() {
        return new Date(startEffectDay.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice)) return false;

        ProductPrice that = (ProductPrice) o;

        if (price != that.price) return false;
        return startEffectDay.equals(that.startEffectDay);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + startEffectDay.hashCode();
        return result;
    }
}