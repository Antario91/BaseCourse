package domain.order;

import domain.customer.CustomerRepo;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.customer.exceptions.NullCustomerRepoException;
import domain.order.exceptions.*;
import domain.product.Product;
import domain.product.ProductRepo;
import domain.product.exceptions.NoAvailableProductPriceException;
import domain.product.exceptions.NullProductRepoException;

import java.util.*;

public class OrderService {
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderService(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) throws NullCustomerRepoException, NullOrderRepoException, NullProductRepoException {
        if (customerRepo == null) {
            throw new NullCustomerRepoException();
        }
        if (orderRepo == null) {
            throw new NullOrderRepoException();
        }
        if (productRepo == null) {
            throw new NullProductRepoException();
        }
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    static void validateOrdersConstructorsParams(String customerId, OrderItem... orderItems) throws NullCustomerIdException, NullOrderItemsException {
        validateParamCustomerId(customerId);
        validateParamOrderItems(orderItems);
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
        validateParamOrderItems(orderItems);
    }

    private static void validateParamCustomerId(String customerId) throws NullCustomerIdException {
        if (customerId == null || customerId.isEmpty()) {
            throw new NullCustomerIdException();
        }
    }

    private static void validateParamOrderItems(OrderItem... orderItems) throws NullOrderItemsException {
        if (orderItems == null) {
            throw new NullOrderItemsException();
        }
    }

    private void validateParamBillingNumber(String billingNumber) throws NullBillingNumberException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new NullBillingNumberException();
        }
    }

    private void validateOrdersExistence(Order order) throws OrderDoesNotExistException {
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
    }

    public double getOrderPrice(String billingNumber) throws NullBillingNumberException, NoAvailableProductPriceException {
        validateParamBillingNumber(billingNumber);
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

    public double createOrder(String customerId, OrderItem ... orderItems) throws NullCustomerIdException, CustomerDoesNotExistException, ProductInOrderIsNotUniqueException, NullOrderItemsException, NoAvailableProductPriceException, NullBillingNumberException {
        validateParamCustomerId(customerId);
        if (customerRepo.get(customerId) == null) {
            throw new CustomerDoesNotExistException();
        }

        Order order = new Order(customerId, orderItems);
        orderRepo.add(order);
        return getOrderPrice(order.getBillingNumber());
    }

    public Order getOrder (String billingNumber) throws NullBillingNumberException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        return order;
    }

    public List<Order> getAllCustomersOrders(String customerId) throws NullCustomerIdException {
        validateParamCustomerId(customerId);
        return orderRepo.getOrdersByCustomerId(customerId);
    }

    public void addOrderItems(String billingNumber, OrderItem ... orderItems) throws NullBillingNumberException, OrderDoesNotExistException, ProductInOrderIsNotUniqueException, NullOrderItemsException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.addOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrderItems(String billingNumber, OrderItem ... orderItems) throws NullBillingNumberException, OrderDoesNotExistException, NullOrderItemsException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.deleteOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrder(String billingNumber) throws NullBillingNumberException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        orderRepo.delete(order);
    }
}
