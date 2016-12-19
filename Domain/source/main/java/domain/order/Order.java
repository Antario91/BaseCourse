package domain.order;

import domain.Entity;

import java.util.*;

public class Order extends Entity<Long> {
    private final long billingNumber;
    private final Date placingDate;
    private List<OrderItem> orderItems;
    private final long customerId;

    private Order() {
        billingNumber = 0;
        placingDate = null;
        customerId = 0;
    }

    public Order(long customerId, long billingNumber) {
        if (customerId <= 0 || billingNumber <= 0) {
            throw new IllegalArgumentException();
        }
        this.billingNumber = billingNumber;
        this.placingDate = new Date();
        this.customerId = customerId;
    }

    public long getBillingNumber() {
        return billingNumber;
    }

    public Date getPlacingDate() {
        return new Date(placingDate.getTime());
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<OrderItem>(orderItems);
    }

    public void setOrderItems(List<OrderItem> orderItems) throws ProductInOrderIsNotUniqueException {
        if (orderItems == null) {
            throw new IllegalArgumentException();
        }
        isUniqueProductsInOrder(orderItems);
        this.orderItems = orderItems;
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public Long getBusinessKey() {
        return billingNumber;
    }

    private boolean isUniqueProductsInOrder(List<OrderItem> orderItems) throws ProductInOrderIsNotUniqueException {
        Set<Long> productIds = new HashSet<Long>();

        for (OrderItem currentItem : orderItems) {
            Long currentProductId = currentItem.getProductId();
            if (productIds.contains(currentProductId)) {
                throw new ProductInOrderIsNotUniqueException();
            } else {
                productIds.add(currentProductId);
            }
        }
        return true;
    }
}