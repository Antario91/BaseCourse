package domain.service;

import domain.order.Order;
import domain.order.OrderItem;
import domain.product.Product;
import domain.repositories.EntityDoesNotExistException;
import domain.repositories.orderRepository.OrderDoesNotExistException;
import domain.repositories.orderRepository.OrderRepo;
import domain.repositories.productRepository.ProductRepo;

import java.util.List;

public class OrderService {
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderService (OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public double getOrderPrice(long billingNumber) throws EntityDoesNotExistException {
        double price = 0;

        Order order = (Order) orderRepo.get(billingNumber);

        List<Product> orderProducts = productRepo.getOrdersProducts(order.getOrderItems());

        for (OrderItem orderItem : order.getOrderItems()) {
            for (Product product : orderProducts) {
                if (orderItem.getProductId() == product.getId()) {
                    double quantity = orderItem.getQuantity();
                    double prodPrice = product.getProductPrice(order.getPlacingDate());
                    price += prodPrice * quantity;
                }
            }
        }

        return price;
    }
}
