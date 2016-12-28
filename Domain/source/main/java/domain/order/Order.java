package domain.order;

import domain.Entity;
import domain.ContractViolationException;
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

    public Order(String customerId, OrderItem ... orderItems) throws ContractViolationException, ProductInOrderIsNotUniqueException {
        validateConstructorsParams(customerId, orderItems);
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

    public void addOrderItems (OrderItem ... newOrderItems) throws ContractViolationException, ProductInOrderIsNotUniqueException {
        validateParamOrderItems(newOrderItems);
        List<OrderItem> tempItems = new ArrayList<OrderItem>(orderItems);
        tempItems.addAll(Arrays.asList(newOrderItems));
        isUniqueProductsInOrder(tempItems);
        orderItems.addAll(Arrays.asList(newOrderItems));
    }

    public void deleteOrderItems(List<String> productIds) throws ContractViolationException {
        if (productIds == null) {
            throw new ContractViolationException("Parameter \"productId\" is NULL");
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

    private void validateConstructorsParams(String customerId, OrderItem... orderItems) throws ContractViolationException {
        if (customerId == null || customerId.isEmpty()) {
            throw new ContractViolationException("Parameter \"customerId\" is NULL");
        }
        validateParamOrderItems(orderItems);
    }

    private void validateParamOrderItems(OrderItem... orderItems) throws ContractViolationException {
        if (orderItems == null) {
            throw new ContractViolationException("Parameter \"orderItems\" is NULL");
        }
    }
}