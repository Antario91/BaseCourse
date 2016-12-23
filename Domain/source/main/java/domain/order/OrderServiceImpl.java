package domain.order;

import domain.ParamIsNullException;
import domain.customer.CustomerRepo;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.*;
import domain.product.Product;
import domain.product.ProductRepo;
import domain.product.exceptions.NoAvailableProductPriceException;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderServiceImpl(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) throws ParamIsNullException {
        if (customerRepo == null) {
            throw new ParamIsNullException("customerRepo");
        }
        if (orderRepo == null) {
            throw new ParamIsNullException("orderRepo");
        }
        if (productRepo == null) {
            throw new ParamIsNullException("productRepo");
        }
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public double getOrderPrice(String billingNumber) throws ParamIsNullException, NoAvailableProductPriceException {
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

    public double createOrder(String customerId, OrderItem ... orderItems) throws ParamIsNullException, CustomerDoesNotExistException,
            ProductInOrderIsNotUniqueException, NoAvailableProductPriceException {
        validateParamCustomerId(customerId);
        if (customerRepo.get(customerId) == null) {
            throw new CustomerDoesNotExistException();
        }

        Order order = new Order(customerId, orderItems);
        orderRepo.add(order);
        return getOrderPrice(order.getBillingNumber());
    }

    public Order getOrder (String billingNumber) throws ParamIsNullException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        return order;
    }

    public List<Order> getAllCustomersOrders(String customerId) throws ParamIsNullException {
        validateParamCustomerId(customerId);
        return orderRepo.getOrdersByCustomerId(customerId);
    }

    public void addOrderItems(String billingNumber, OrderItem ... orderItems) throws ParamIsNullException, OrderDoesNotExistException,
            ProductInOrderIsNotUniqueException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.addOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrderItems(String billingNumber, OrderItem ... orderItems) throws ParamIsNullException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.deleteOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrder(String billingNumber) throws ParamIsNullException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        orderRepo.delete(order);
    }

    private static void validateParamCustomerId(String customerId) throws ParamIsNullException {
        if (customerId == null || customerId.isEmpty()) {
            throw new ParamIsNullException("customerId");
        }
    }

    private void validateParamBillingNumber(String billingNumber) throws ParamIsNullException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new ParamIsNullException("billingNumber");
        }
    }

    private void validateOrdersExistence(Order order) throws OrderDoesNotExistException {
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
    }
}
