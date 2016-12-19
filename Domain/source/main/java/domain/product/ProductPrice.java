package domain.product;

import java.util.Date;

public class ProductPrice {
    private final long price;
    private final Date endEffectDay;

    private ProductPrice () {
        price = 0;
        endEffectDay = null;
    }

    public ProductPrice(long price, Date endEffectDay) {
        if (price <= 0 || endEffectDay == null){
            throw new IllegalArgumentException();
        }
        this.price = price;
        this.endEffectDay = endEffectDay;
    }

    public long getPrice() {
        return price;
    }

    public Date getEndEffectDay() {
        return new Date(endEffectDay.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice)) return false;

        ProductPrice that = (ProductPrice) o;

        if (price != that.price) return false;
        return endEffectDay.equals(that.endEffectDay);

    }

    @Override
    public int hashCode() {
        int result = (int) (price ^ (price >>> 32));
        result = 31 * result + endEffectDay.hashCode();
        return result;
    }
}