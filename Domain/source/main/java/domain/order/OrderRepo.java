package domain.order;

import domain.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<String> {
    List<Order> getOrders(String orderCustomerId);
}