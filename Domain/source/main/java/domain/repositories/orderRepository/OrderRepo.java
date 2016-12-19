package domain.repositories.orderRepository;

import domain.order.Order;
import domain.product.Product;
import domain.repositories.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<Long> {
    List<Order> getAllCustomersOrders (String customersName);
    long getNextOrdersBillingNumber ();
}