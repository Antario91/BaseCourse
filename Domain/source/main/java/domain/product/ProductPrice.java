package domain.product;

import domain.ContractViolationException;

import java.util.Date;

public class ProductPrice {
    private final double price;
    private final Date startEffectDay;

    private ProductPrice () {
        price = 0;
        startEffectDay = null;
    }

    public ProductPrice(double price, Date startEffectDay) throws ContractViolationException {
        validateConstructorsParams(price, startEffectDay);
        this.price = price;
        this.startEffectDay = startEffectDay;
    }

    public double getPrice() {
        return price;
    }

    public Date getStartEffectDay() {
        return new Date(startEffectDay.getTime());
    }

    static void validateConstructorsParams(double price, Date startEffectDay) throws ContractViolationException {
        if (price <= 0){
            throw new ContractViolationException("Parameter \"price\" is NULL");
        }
        if (startEffectDay == null) {
            throw new ContractViolationException("Parameter \"startEffectDay\" is NULL");
        }
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