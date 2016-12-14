package persistence.orderRepository;

import domain.order.Order;
import domain.product.Product;
import persistence.GenericRepo;

import java.util.List;

public interface OrderRepo extends GenericRepo<Long, Long> {
    List<Product> getAllOrdersProducts (Order order);
}
