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

    public Order(String customerId, OrderItem ... orderItems) throws ProductInOrderIsAlreadyOrderedException {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"customerId\" is NULL");
        }
        if (orderItems == null) {
            throw new IllegalArgumentException("Parameter \"orderItems\" is NULL");
        }
        if ( !isUniqueProductsInOrder(Arrays.asList(orderItems)) ) {
            throw new ProductInOrderIsAlreadyOrderedException();
        }
        this.orderItems = Arrays.asList(orderItems);
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

    public void addOrderItems (OrderItem ... newOrderItems) throws ProductInOrderIsAlreadyOrderedException {
        if (orderItems == null) {
            throw new IllegalArgumentException("Parameter \"orderItems\" is NULL");
        }
        if ( !isUniqueProductsInOrder(Arrays.asList(newOrderItems)) ) {
            throw new ProductInOrderIsAlreadyOrderedException();
        }
        orderItems.addAll(Arrays.asList(newOrderItems));
    }

    public void deleteOrderItems(List<String> productIds) {
        if (productIds == null) {
            throw new IllegalArgumentException("Parameter \"productId\" is NULL");
        }
        Map<String, OrderItem> itemsMap = new HashMap<String, OrderItem>();
        for (OrderItem item : orderItems) {
            itemsMap.put(item.getProductId(), item);
        }
        for (String productId : productIds) {
            orderItems.remove(itemsMap.get(productId));
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    //for getOrderPrice() and deleteOrderItems -> ServiceImpl
    public List<String> getOrdersProductsIds() {
        List<String> productIds = new ArrayList<String>();
        for (OrderItem item : orderItems) {
            productIds.add(item.getProductId());
        }
        return productIds;
    }

    private boolean isUniqueProductsInOrder(List<OrderItem> newOrderItems) throws ProductInOrderIsAlreadyOrderedException {
        for (OrderItem currentItem : newOrderItems) {
            if (this.orderItems.contains(currentItem)) {
                return false;
            }
        }
        return true;
    }
}