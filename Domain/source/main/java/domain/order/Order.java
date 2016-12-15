package domain.order;

import domain.Entity;
import domain.exceptions.ProductInOrderIsNotUniqueException;
import domain.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends Entity<Long, Long> {
    private static long generator = 1000000;
    private long billingNumber = generator++;
    private Date placingDate;
    private List<OrderItem> orderItems;
    private long customerId;

    private Order () {}

    public Order(long customerId) {
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

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        if ( !checkOrder() ){
            throw new ProductInOrderIsNotUniqueException();
        }
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public Long getBusinessKey() {
        return billingNumber;
    }

    public long getOrderPrice(List<Product> orderProducts) {
        long price = 0;

        for (OrderItem orderItem : orderItems){
            for (Product product : orderProducts){
                if (orderItem.getProductId() == product.getId()){
                    price += product.getProductPrice(placingDate) * orderItem.getQuantity();
                }
            }

        }

        return price;
    }

    public boolean checkOrder() {
//        if (billingNumber == 0){
//            throw new NullOrderBillingNumberException();
//        }
//        if (orderItems == null || orderItems.isEmpty()){
//            throw new NullOrderItemsException();
//        }
//        for (OrderItem orderItem : orderItems){
//            orderItem.checkOrderItem();
//        }

        long checkableProductId;

        for (int i = 0; i < orderItems.size(); i++) {

            checkableProductId = orderItems.get(i).getProductId();

            if (i != orderItems.size() - 1)
                for (int j = i + 1; j < orderItems.size(); j++) {
                    if (checkableProductId == orderItems.get(j).getProductId()) {
                        return false;
                    }
                }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if ( getId().equals(order.getId()) ) return false;
        if (billingNumber != order.billingNumber) return false;
        if (customerId != order.customerId) return false;
        if (!placingDate.equals(order.placingDate)) return false;
        return orderItems.equals(order.orderItems);

    }

    @Override
    public int hashCode() {
        int result = 31 + getId().hashCode();
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
