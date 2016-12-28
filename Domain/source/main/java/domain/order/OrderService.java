package domain.order;

import domain.ContractViolationException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.order.exceptions.ProductInOrderIsNotUniqueException;
import domain.product.exceptions.NoAvailableProductPriceException;
import domain.product.exceptions.ProductDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface OrderService {
    double getOrderPrice(String billingNumber) throws ContractViolationException, NoAvailableProductPriceException;

    double createOrder(String customerId, OrderItem ... orderItems) throws ContractViolationException, CustomerDoesNotExistException,
            ProductInOrderIsNotUniqueException, NoAvailableProductPriceException, ProductDoesNotExistException;

    Order getOrder (String billingNumber) throws ContractViolationException, OrderDoesNotExistException;

    List<Order> getAllCustomersOrders(String customerId) throws ContractViolationException, CustomerDoesNotExistException;

    void addOrderItems(String billingNumber, OrderItem ... orderItems) throws ContractViolationException, OrderDoesNotExistException,
            ProductInOrderIsNotUniqueException, ProductDoesNotExistException;

    void deleteOrderItems(String billingNumber, List<String> productIds) throws ContractViolationException, OrderDoesNotExistException;

    void deleteOrder(String billingNumber) throws ContractViolationException, OrderDoesNotExistException;

}
