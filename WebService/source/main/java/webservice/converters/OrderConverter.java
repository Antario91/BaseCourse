package webservice.converters;

import domain.order.Order;
import domain.order.OrderItem;
import domain.product.Product;
import persistence.customerRepository.CustomerRepo;
import persistence.orderRepository.OrderRepo;
import persistence.productRepository.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.order.*;
import webservice.dtos.product.ProductDTOInOrder;
import webservice.validators.OrderValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgo on 14-Dec-16.
 */
public class OrderConverter {
    public static Order orderDTOForCreationToOrder (OrderDTOForCreation orderDTOForCreation, CustomerRepo customerRepo, ProductRepo productRepo) {
        if (orderDTOForCreation != null && customerRepo != null && productRepo != null) {
            OrderValidator.checkOrderDTOForCreation(orderDTOForCreation);

            long customerId = customerRepo.getByBusinessKey(orderDTOForCreation.getOrdersCustomersName())
                    .getId();

            List<OrderItem> orderItems = new ArrayList<OrderItem>();
            for (OrderItemDTOForCreation orderItemDTO : orderDTOForCreation.getOrderItems()) {
                orderItems.add(
                        new OrderItem(orderItemDTO.getQuantity(),
                                productRepo.getByBusinessKey(orderItemDTO.getProductName()).getId())
                );
            }

            Order order = new Order(customerId);

            order.setOrderItems(orderItems);

            return order;
        }

        return null;
    }

    public static OrderDTOForReception orderToOrderDTOForReception (Order order,
                                                                    CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) {
        if (order != null && customerRepo != null && orderRepo != null && productRepo != null) {
            OrderDTOForReception orderDTOForReception = new OrderDTOForReception();

            orderDTOForReception.setBillingNumber(order.getBillingNumber());
            orderDTOForReception.setPlacingDate(DateProducer.produce(order.getPlacingDate()));
            orderDTOForReception.setOrderPrice(order.getOrderPrice(orderRepo.getAllOrdersProducts(order)));
            orderDTOForReception.setOrdersCustomersName(
                    customerRepo.getById(order.getCustomerId())
                            .getBusinessKey()
            );

            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = (Product) productRepo.getById(orderItem.getProductId());

                ProductDTOInOrder productDTOInOrder = new ProductDTOInOrder();
                productDTOInOrder.setProductName(product.getName());
                productDTOInOrder.setProductUnits(product.getUnits());
                productDTOInOrder.setProductPrice(product.getProductPrice(order.getPlacingDate()));

                OrderItemDTOForReception orderItemDTOForReception = new OrderItemDTOForReception();
                orderItemDTOForReception.setQuantity(orderItem.getQuantity());
                orderItemDTOForReception.setProduct(productDTOInOrder);

                orderDTOForReception.getOrderItems().add(orderItemDTOForReception);
            }
            return orderDTOForReception;
        }

        return null;
    }

    public static Order updatedOrderDTOtoOrder (UpdatedOrderDTO updatedOrderDTO, OrderRepo orderRepo, ProductRepo productRepo) {
        if (updatedOrderDTO != null && orderRepo != null && productRepo != null) {
            OrderValidator.checkUpdatedOrderDTO(updatedOrderDTO);

            Order order = (Order) orderRepo.getByBusinessKey(updatedOrderDTO.getBillingNumberOfUpdatedOrder());

            List<OrderItem> orderItems = new ArrayList<OrderItem>();

            for (OrderItemDTOForCreation orderItemDTOForCreation : updatedOrderDTO.getNewOrderItems()) {
                orderItems.add(
                        new OrderItem(orderItemDTOForCreation.getQuantity(),
                                productRepo.getByBusinessKey(orderItemDTOForCreation.getProductName()).getId())
                );
            }

            order.setOrderItems(orderItems);

            return order;
        }

        return null;
    }
}
