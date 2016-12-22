package domain.order;

import domain.customer.Customer;
import domain.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<Long> {
//    Instead method below use getOrdersByCustomerId(String customerId)
//    List<Order> getAllCustomersOrders (Customer customer);

    List<Order> getOrdersByCustomerId(String customerId);
}