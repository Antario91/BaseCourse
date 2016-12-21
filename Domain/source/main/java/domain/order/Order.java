package domain.order;

import domain.Entity;

import java.util.*;

public class Order extends Entity {
    private final String billingNumber;
    private final Date placingDate;
    private List<OrderItem> orderItems;
    private final String customerId;

    private Order() {
        billingNumber = null;
        placingDate = null;
        customerId = null;
    }

    public Order(String customerId, OrderItem ... orderItems) throws NullCustomerIdException,
            NullBillingNumberException,
            NullOrderItemsException,
            ProductInOrderIsNotUniqueException {
        OrderService.validateIncomingDataInConstructor(customerId, orderItems);
        this.orderItems = OrderService.ValidateAndFormOrderItems(orderItems);
        this.billingNumber = UUID.randomUUID().toString();
        this.placingDate = new Date();
        this.customerId = customerId;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public Date getPlacingDate() {
        return new Date(placingDate.getTime());
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<OrderItem>(orderItems);
    }

    public void addOrderItems (OrderItem ... newOrderItems) throws NullNewOrderItemsException, ProductInOrderIsNotUniqueException {
        OrderService.ValidateAndAddNewOrderItems(this.orderItems, newOrderItems);
    }

    public void deleteOrderItems () {
        
    }

    public String getCustomerId() {
        return customerId;
    }


}