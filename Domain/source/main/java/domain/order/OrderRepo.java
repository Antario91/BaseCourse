package domain.order;

import domain.GenericRepo;
import domain.NullIdException;

import java.util.List;

public interface OrderRepo extends GenericRepo<String> {
    List<Order> getOrdersByCustomerId(String customerId) throws NullIdException;
}