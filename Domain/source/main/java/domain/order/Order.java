package domain.order;

import domain.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends Entity<Long, Long> {
    static private long generatorBillingNumber = 1000000;
    private long billingNumber;
    private Date placingDate;
    private List<OrderItem> orderItems;
    private long customerId;

    public Order(long customerId) {
        this.billingNumber = generatorBillingNumber;
        generatorBillingNumber++;
        this.placingDate = new Date();
        orderItems = new ArrayList<OrderItem>();
        this.customerId = customerId;
    }

    public long getBillingNumber() {
        return billingNumber;
    }

    public Date getPlacingDate() {
        return placingDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public Long getBusinessKey() {
        return billingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getId() != order.getId()) return false;
        if (billingNumber != order.billingNumber) return false;
        if (customerId != order.customerId) return false;
        if (!placingDate.equals(order.placingDate)) return false;
        return orderItems.equals(order.orderItems);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (billingNumber ^ (billingNumber >>> 32));
        result = 31 * result + placingDate.hashCode();
        result = 31 * result + orderItems.hashCode();
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", billingNumber=" + billingNumber +
                ", placingDate=" + placingDate +
                ", orderItems=" + orderItems +
                ", customerId=" + customerId +
                '}';
    }
}
