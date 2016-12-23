package domain.order;

import domain.ParamIsNullException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.order.exceptions.ProductInOrderIsNotUniqueException;
import domain.product.exceptions.NoAvailableProductPriceException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface OrderService {
    double getOrderPrice(String billingNumber) throws ParamIsNullException, NoAvailableProductPriceException;

    double createOrder(String customerId, OrderItem ... orderItems) throws ParamIsNullException, CustomerDoesNotExistException,
            ProductInOrderIsNotUniqueException, NoAvailableProductPriceException;

    Order getOrder (String billingNumber) throws ParamIsNullException, OrderDoesNotExistException;

    List<Order> getAllCustomersOrders(String customerId) throws ParamIsNullException;

    void addOrderItems(String billingNumber, OrderItem ... orderItems) throws ParamIsNullException, OrderDoesNotExistException,
            ProductInOrderIsNotUniqueException;

    void deleteOrderItems(String billingNumber, OrderItem ... orderItems) throws ParamIsNullException, OrderDoesNotExistException;

    void deleteOrder(String billingNumber) throws ParamIsNullException, OrderDoesNotExistException;

}
