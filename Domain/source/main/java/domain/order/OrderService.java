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

    static void validateOrdersConstructorsParams(String customerId, OrderItem... orderItems) throws NullCustomerIdException, NullOrderItemsException {
        if (customerId == null || customerId.isEmpty()) {
            throw new NullCustomerIdException();
        }
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
    }

    static void validateOrderItemConstructorsParams(double quantity, String productId) throws NullQuantityException, NullProductIdException {
        if (quantity <= 0) {
            throw new NullQuantityException();
        }
        if (productId == null || productId.isEmpty()) {
            throw new NullProductIdException();
        }
    }

    static void validateOrderItems(OrderItem... orderItems) throws NullOrderItemsException {
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
    }

    public double getOrderPrice(long billingNumber) throws NullOrderException, NoAvailableProductPriceException {
        double price = 0;

        Order order = (Order) orderRepo.get(billingNumber);

        List<Product> orderProducts = productRepo.getProductsByIds(order.getOrdersProductsIds());
        Map<String, Product> productMap = new HashMap<String, Product>();
        for (Product product : orderProducts) {
            productMap.put(product.getName(), product);
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productMap.get(orderItem.getProductId());
            double quantity = orderItem.getQuantity();
            double prodPrice = product.getProductPrice(order.getPlacingDate());
            price += prodPrice * quantity;

        }

        return price;
    }
}
