package domain.product;

import domain.order.OrderItem;
import domain.product.Product;
import domain.GenericRepo;

import java.util.List;

public interface ProductRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Product> getAllProducts ();
    List<Product> getOrdersProducts (List<OrderItem> orderItems);
}