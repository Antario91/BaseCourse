package domain.order;

import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.order.exceptions.ProductInOrderIsAlreadyOrderedException;
import domain.product.exceptions.NotAvailableProductPriceException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface OrderService {
    double getOrderPrice(String billingNumber) throws NotAvailableProductPriceException;

    void createOrder(String customerId, OrderItem ... orderItems) throws CustomerDoesNotExistException,
            ProductInOrderIsAlreadyOrderedException, NotAvailableProductPriceException, ProductDoesNotExistException;

    Order getOrder (String billingNumber) throws OrderDoesNotExistException;

    List<Order> getAllCustomerOrders(String customerId) throws CustomerDoesNotExistException;

    void addOrderItems(String billingNumber, OrderItem ... orderItems) throws OrderDoesNotExistException,
            ProductInOrderIsAlreadyOrderedException, ProductDoesNotExistException;

    void deleteOrderItems(String billingNumber, List<String> productIds) throws OrderDoesNotExistException;

    void deleteOrder(String billingNumber) throws OrderDoesNotExistException;

}
