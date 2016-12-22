package domain.order;

import domain.Entity;
import domain.order.exceptions.*;

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
            NullOrderItemsException,
            ProductInOrderIsNotUniqueException {
        OrderService.validateOrdersConstructorsParams(customerId, orderItems);
        this.orderItems = Arrays.asList(orderItems);
        isUniqueProductsInOrder(this.orderItems);
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

    public void addOrderItems (OrderItem ... newOrderItems) throws NullOrderItemsException, ProductInOrderIsNotUniqueException {
        OrderService.validateOrderItems(newOrderItems);
        List<OrderItem> tempItems = new ArrayList<OrderItem>(orderItems);
        tempItems.addAll(Arrays.asList(newOrderItems));
        isUniqueProductsInOrder(tempItems);
        orderItems.addAll(Arrays.asList(newOrderItems));
    }

    public void deleteOrderItems(OrderItem ... currentOrderItems) throws NullOrderItemsException {
        OrderService.validateOrderItems(currentOrderItems);
        List<OrderItem> tempItems = Arrays.asList(currentOrderItems);
        orderItems.removeAll(tempItems);
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<String> getOrdersProductsIds() throws NullOrderException {
        List<String> productIds = new ArrayList<String>();
        for (OrderItem item : orderItems) {
            productIds.add(item.getProductId());
        }
        return productIds;
    }

    private boolean isUniqueProductsInOrder(List<OrderItem> orderItems) throws ProductInOrderIsNotUniqueException {
        Set<String> productIds = new HashSet<String>();

        for (OrderItem currentItem : orderItems) {
            String currentProductId = currentItem.getProductId();
            if (productIds.contains(currentProductId)) {
                throw new ProductInOrderIsNotUniqueException();
            } else {
                productIds.add(currentProductId);
            }
        }
        return true;
    }
}