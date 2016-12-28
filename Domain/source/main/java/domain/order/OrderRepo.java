package domain.order;

import domain.GenericRepo;
import domain.NullIdException;

import java.util.List;

public interface OrderRepo extends GenericRepo<String> {
//    Instead method below use getOrdersByCustomerId(String customerId)
//    List<Order> getAllCustomersOrders (Customer customer);

    List<Order> getOrdersByCustomerId(String customerId) throws NullIdException;
    List<Order> getOrdersByProductId(String productId);
}