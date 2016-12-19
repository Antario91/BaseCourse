package domain.repositories.orderRepository;

import domain.customer.Customer;
import domain.order.Order;
import domain.product.Product;
import domain.repositories.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<Long> {
    List<Order> getAllCustomersOrders (Customer customer);
    long getNextOrdersBillingNumber ();
}