package domain.order;

import domain.order.exceptions.*;
import domain.product.Product;
import domain.product.ProductRepo;
import domain.product.ProductService;
import domain.product.exceptions.NoAvailableProductPriceException;

import java.util.*;

public class OrderService {
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderService(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public static void validateOrdersConstructorsParams(String customerId, OrderItem ... orderItems) throws NullCustomerIdException, NullOrderItemsException {
        if (customerId == null || customerId.isEmpty()) {
            throw new NullCustomerIdException();
        }
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
    }

    public static void validateOrderItemConstructorsParams(double quantity, String productId) throws NullQuantityException, NullProductIdException {
        if (quantity <= 0) {
            throw new NullQuantityException();
        }
        if (productId == null || productId.isEmpty()) {
            throw new NullProductIdException();
        }
    }

    public static List<OrderItem> validateAndFormOrderItems(OrderItem ... orderItems) throws NullOrderItemsException, ProductInOrderIsNotUniqueException {
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
        if (orderItems.length == 0) {
            return new ArrayList<OrderItem>();
        }
        List<OrderItem> temp = Arrays.asList(orderItems);
        isUniqueProductsInOrder(temp);
        return temp;
    }

    public static void validateAndAddNewOrderItems(List<OrderItem> orderItems, OrderItem ... newOrderItems) throws NullOrderItemsException, NullNewOrderItemsException, ProductInOrderIsNotUniqueException {
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
        if (newOrderItems == null) {
            throw new NullNewOrderItemsException();
        }
        if (newOrderItems.length != 0) {
            List<OrderItem> tempItems = new ArrayList<OrderItem>(orderItems);
            tempItems.addAll(Arrays.asList(newOrderItems));
            isUniqueProductsInOrder(tempItems);
            orderItems.addAll(Arrays.asList(newOrderItems));
        }
    }

    public static void validateAndDeleteCurrentOrderItems(List<OrderItem> orderItems, OrderItem ... currentOrderItems) throws NullOrderItemsException, NullCurrentOrderItemsException {
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
        if (currentOrderItems == null) {
            throw new NullCurrentOrderItemsException();
        }
        if (currentOrderItems.length != 0) {
            List<OrderItem> tempItems = Arrays.asList(currentOrderItems);
            orderItems.removeAll(tempItems);
        }
    }

    public static List<String> getOrdersProductsIds(Order order) throws NullOrderException {
        if (order == null) {
            throw new NullOrderException();
        }
        List<String> productIds = new ArrayList<String>();
        for (OrderItem item : order.getOrderItems()) {
            productIds.add(item.getProductId());
        }
        return productIds;
    }

    public double getOrderPrice(long billingNumber) throws NullOrderException, NoAvailableProductPriceException {
        double price = 0;

        Order order = (Order) orderRepo.get(billingNumber);

        List<Product> orderProducts = productRepo.getProductsByIds(getOrdersProductsIds(order));

        for (OrderItem orderItem : order.getOrderItems()) {
            for (Product product : orderProducts) {
                if ( orderItem.getProductId().equals(product.getName()) ) {
                    double quantity = orderItem.getQuantity();
                    double prodPrice = ProductService.getProductPrice(product, order.getPlacingDate());
                    price += prodPrice * quantity;
                }
            }
        }

        return price;
    }
}
