package domain.product;

import java.util.Date;

public class ProductPrice {
    private long price;
    private Date startEffectDay;
    private Date endEffectDay;

    private ProductPrice () {}

    public ProductPrice(long price, Date startEffectDay, Date endEffectDay) {
        this.price = price;
        this.startEffectDay = startEffectDay;
        this.endEffectDay = endEffectDay;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Date getStartEffectDay() {
        return startEffectDay;
    }

    public void setStartEffectDay(Date startEffectDay) {
        this.startEffectDay = startEffectDay;
    }

    public Date getEndEffectDay() {
        return endEffectDay;
    }

    public void setEndEffectDay(Date endEffectDay) {
        this.endEffectDay = endEffectDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice)) return false;

        ProductPrice that = (ProductPrice) o;

        if (price != that.price) return false;
        if (!startEffectDay.equals(that.startEffectDay)) return false;
        return endEffectDay.equals(that.endEffectDay);

    }

    @Override
    public int hashCode() {
        int result = (int) (price ^ (price >>> 32));
        result = 31 * result + startEffectDay.hashCode();
        result = 31 * result + endEffectDay.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "price=" + price +
                ", startEffectDay=" + startEffectDay +
                ", endEffectDay=" + endEffectDay +
                '}';
    }
}
