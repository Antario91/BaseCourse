package domain.order;

import domain.ContractViolationException;
import domain.customer.Customer;
import domain.customer.CustomerRepo;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.*;
import domain.product.Product;
import domain.product.ProductRepo;
import domain.product.exceptions.NoAvailableProductPriceException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderServiceImpl(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) throws ContractViolationException {
        if (customerRepo == null) {
            throw new ContractViolationException("Parameter \"customerRepo\" is NULL");
        }
        if (orderRepo == null) {
            throw new ContractViolationException("Parameter \"orderRepo\" is NULL");
        }
        if (productRepo == null) {
            throw new ContractViolationException("Parameter \"productRepo\" is NULL");
        }
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public double getOrderPrice(String billingNumber) throws ContractViolationException, NoAvailableProductPriceException {
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

    public double createOrder(String customerId, OrderItem ... orderItems) throws ContractViolationException, CustomerDoesNotExistException,
            ProductInOrderIsNotUniqueException, NoAvailableProductPriceException, ProductDoesNotExistException {
        validateParamCustomerId(customerId);
        validateProductsExistence(orderItems);
        if (customerRepo.get(customerId) == null) {
            throw new CustomerDoesNotExistException();
        }

        Order order = new Order(customerId, orderItems);
        orderRepo.add(order);
        return getOrderPrice(order.getBillingNumber());
    }

    public Order getOrder (String billingNumber) throws ContractViolationException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        return order;
    }

    public List<Order> getAllCustomersOrders(String customerId) throws ContractViolationException, CustomerDoesNotExistException {
        validateParamCustomerId(customerId);
        Customer customer = (Customer) customerRepo.get(customerId);
        validateCustomerExistence(customer);
        return orderRepo.getOrdersByCustomerId(customerId);
    }

    public void addOrderItems(String billingNumber, OrderItem ... orderItems) throws ContractViolationException, OrderDoesNotExistException,
            ProductInOrderIsNotUniqueException, ProductDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        validateProductsExistence(orderItems);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.addOrderItems(orderItems);
        orderRepo.update(order);
    }

    public void deleteOrderItems(String billingNumber, List<String> productIds) throws ContractViolationException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        order.deleteOrderItems(productIds);
        orderRepo.update(order);
    }

    public void deleteOrder(String billingNumber) throws ContractViolationException, OrderDoesNotExistException {
        validateParamBillingNumber(billingNumber);
        Order order = (Order) orderRepo.get(billingNumber);
        validateOrdersExistence(order);
        deleteOrderItems(billingNumber, order.getOrdersProductsIds());
        orderRepo.delete(order);
    }

    private void validateParamCustomerId(String customerId) throws ContractViolationException {
        if (customerId == null || customerId.isEmpty()) {
            throw new ContractViolationException("Parameter \"customerId\" is NULL");
        }
    }

    private void validateParamBillingNumber(String billingNumber) throws ContractViolationException {
        if (billingNumber == null || billingNumber.isEmpty()) {
            throw new ContractViolationException("Parameter \"billingNumber\" is NULL");
        }
    }

    private void validateCustomerExistence(Customer customer) throws CustomerDoesNotExistException {
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
    }

    private void validateOrdersExistence(Order order) throws OrderDoesNotExistException {
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
    }

    private void validateProductsExistence(OrderItem ... orderItems) throws ProductDoesNotExistException {
        for (OrderItem item : orderItems) {
            if (productRepo.get(item.getProductId()) == null) {
                throw new ProductDoesNotExistException();
            }
        }
    }
}
