package domain.order;

import domain.customer.Customer;
import domain.order.Order;
import domain.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<Long> {
    List<Order> getAllCustomersOrders (Customer customer);
    long getNextOrdersBillingNumber ();
}