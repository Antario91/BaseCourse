package domain.order;

import domain.customer.Customer;
import domain.customer.CustomerRepo;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.*;
import domain.product.Product;
import domain.product.ProductRepo;
import domain.product.exceptions.NotAvailableProductPriceException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderServiceImpl(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) {
        if (customerRepo == null) {
            throw new IllegalArgumentException("Parameter \"customerRepo\" is NULL");
        }
        if (orderRepo == null) {
            throw new IllegalArgumentException("Parameter \"orderRepo\" is NULL");
        }
        if (productRepo == null) {
            throw new IllegalArgumentException("Parameter \"productRepo\" is NULL");
        }
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public double getOrderPrice(String billingNumber) throws NotAvailableProductPriceException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"billingNumber\" is NULL");
        }
        double price = 0;

        Order order = (Order) orderRepo.get(billingNumber);

        List<Product> orderProducts = productRepo.getProducts(order.getOrdersProductsIds());
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

    public void createOrder(String customerId, OrderItem ... orderItems) throws CustomerDoesNotExistException, ProductInOrderIsAlreadyOrderedException,
            NotAvailableProductPriceException, ProductDoesNotExistException {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"customerId\" is NULL");
        }
        if (customerRepo.get(customerId) == null) {
            throw new CustomerDoesNotExistException();
        }

        for (OrderItem item : orderItems) {
            if (productRepo.get(item.getProductId()) == null) {
                throw new ProductDoesNotExistException();
            }
        }

        Order order = new Order(customerId, orderItems);
        orderRepo.add(order);
    }

    public Order getOrder (String billingNumber) throws OrderDoesNotExistException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"billingNumber\" is NULL");
        }
        Order order = (Order) orderRepo.get(billingNumber);
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
        return order;
    }

    public List<Order> getAllCustomerOrders(String customerId) throws CustomerDoesNotExistException {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"customerId\" is NULL");
        }
        Customer customer = (Customer) customerRepo.get(customerId);
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
        return orderRepo.getOrders(customerId);
    }

    public void addOrderItems(String billingNumber, OrderItem ... orderItems) throws OrderDoesNotExistException, ProductInOrderIsAlreadyOrderedException,
            ProductDoesNotExistException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"billingNumber\" is NULL");
        }
        for (OrderItem item : orderItems) {
            if (productRepo.get(item.getProductId()) == null) {
                throw new ProductDoesNotExistException();
            }
        }
        Order order = (Order) orderRepo.get(billingNumber);
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
        order.addOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrderItems(String billingNumber, List<String> productIds) throws OrderDoesNotExistException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"billingNumber\" is NULL");
        }
        Order order = (Order) orderRepo.get(billingNumber);
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
        order.deleteOrderItems(productIds);
        orderRepo.update(order);
    }

    public void deleteOrder(String billingNumber) throws OrderDoesNotExistException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"billingNumber\" is NULL");
        }
        Order order = (Order) orderRepo.get(billingNumber);
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
        deleteOrderItems(billingNumber, order.getOrdersProductsIds());
        orderRepo.delete(order);
    }
}
