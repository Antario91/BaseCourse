package domain.order;

import domain.order.Order;
import domain.order.OrderItem;
import domain.product.Product;
import domain.EntityDoesNotExistException;
import domain.order.OrderRepo;
import domain.product.ProductRepo;

import java.util.*;

public class OrderService {
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderService (OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public static void validateIncomingDataInConstructor (String customerId, OrderItem ... orderItems) throws NullCustomerIdException, NullBillingNumberException, NullOrderItemsException {
        if (customerId == null || customerId.isEmpty()) {
            throw new NullCustomerIdException();
        }
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
    }

    public static List<OrderItem> ValidateAndFormOrderItems(OrderItem ... orderItems) throws ProductInOrderIsNotUniqueException {
        if (orderItems.length == 0) {
            return new ArrayList<OrderItem>();
        }
        List<OrderItem> temp = Arrays.asList(orderItems);
        isUniqueProductsInOrder(temp);
        return temp;
    }

    public static void ValidateAndAddNewOrderItems(List<OrderItem> orderItems, OrderItem ... newOrderItems) throws NullNewOrderItemsException, ProductInOrderIsNotUniqueException {
        if (newOrderItems == null) {
            throw new NullNewOrderItemsException();
        }
        List<OrderItem> tempItems = new ArrayList<OrderItem>(orderItems);
        tempItems.addAll(Arrays.asList(newOrderItems));
        OrderService.isUniqueProductsInOrder(tempItems);
        orderItems.addAll(Arrays.asList(newOrderItems));
    }

    private static boolean isUniqueProductsInOrder(List<OrderItem> orderItems) throws ProductInOrderIsNotUniqueException {
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

    public double getOrderPrice(long billingNumber) throws EntityDoesNotExistException {
        double price = 0;

        Order order = (Order) orderRepo.get(billingNumber);

        List<Product> orderProducts = productRepo.getOrdersProducts(order.getOrderItems());

        for (OrderItem orderItem : order.getOrderItems()) {
            for (Product product : orderProducts) {
                if (orderItem.getProductId() == product.getId()) {
                    double quantity = orderItem.getQuantity();
                    double prodPrice = product.getProductPrice(order.getPlacingDate());
                    price += prodPrice * quantity;
                }
            }
        }

        return price;
    }
}
