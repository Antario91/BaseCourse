package webservice.converters;

import domain.customer.Customer;

import domain.order.Order;
import domain.order.OrderItem;
import domain.order.ProductInOrderIsNotUniqueException;

import domain.product.Product;

import domain.repositories.EntityDoesNotExistException;
import domain.repositories.customerRepository.CustomerDoesNotExistException;
import domain.repositories.customerRepository.CustomerRepo;
import domain.repositories.orderRepository.OrderDoesNotExistException;
import domain.repositories.productRepository.ProductDoesNotExistException;
import domain.service.OrderService;
import domain.repositories.orderRepository.OrderRepo;
import domain.repositories.productRepository.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.order.*;
import webservice.dtos.product.ProductDTOInOrder;
import webservice.validators.OrderValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgo on 14-Dec-16.
 */
public class OrderConverter {
    public static Order orderDTOForCreationToOrder (OrderDTOForCreation orderDTOForCreation, CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) throws ProductInOrderIsNotUniqueException, EntityDoesNotExistException {
        if (orderDTOForCreation != null && customerRepo != null && productRepo != null) {
            OrderValidator.checkOrderDTOForCreation(orderDTOForCreation);

            Customer customer = (Customer) customerRepo.get(orderDTOForCreation.getOrdersCustomersName());
            if (customer == null) {
                throw new CustomerDoesNotExistException();
            }
            long customerId = customer.getId();

            List<OrderItem> orderItems = new ArrayList<OrderItem>();
            Product product;
            for (OrderItemDTOForCreation orderItemDTO : orderDTOForCreation.getOrderItems()) {
                product = (Product) productRepo.get(orderItemDTO.getProductName());
                if (product == null) {
                    throw new ProductDoesNotExistException();
                }
                orderItems.add(
                        new OrderItem(orderItemDTO.getQuantity().doubleValue(),
                                product.getId())
                );
            }

            Order order = new Order(customerId, orderRepo.getNextOrdersBillingNumber());

            order.setOrderItems(orderItems);

            return order;
        }

        return null;
    }

    public static OrderDTOForReception orderToOrderDTOForReception (Order order,
                                                                    CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo, OrderService orderService) throws EntityDoesNotExistException {
        if (order != null && customerRepo != null && orderRepo != null && productRepo != null) {
            OrderDTOForReception orderDTOForReception = new OrderDTOForReception();

            orderDTOForReception.setBillingNumber(order.getBillingNumber());
            orderDTOForReception.setPlacingDate( DateProducer.produce(order.getPlacingDate()) );
            orderDTOForReception.setOrderPrice( new BigDecimal(
                    orderService.getOrderPrice(order.getBillingNumber())
                    ).setScale(2, RoundingMode.HALF_UP)
            );
            orderDTOForReception.setOrdersCustomersName(
                    ( (Customer) customerRepo.getById(order.getCustomerId()) ).getBusinessKey()
            );

            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = (Product) productRepo.getById(orderItem.getProductId());

                ProductDTOInOrder productDTOInOrder = new ProductDTOInOrder();
                productDTOInOrder.setProductName(product.getName());
                productDTOInOrder.setProductUnits(product.getUnits());
                productDTOInOrder.setProductPrice( new BigDecimal(
                        product.getProductPrice(order.getPlacingDate())
                ).setScale(2, RoundingMode.HALF_UP)
                );

                OrderItemDTOForReception orderItemDTOForReception = new OrderItemDTOForReception();
                orderItemDTOForReception.setQuantity( new BigDecimal(
                        orderItem.getQuantity()
                ).setScale(2, RoundingMode.HALF_UP)
                );
                orderItemDTOForReception.setProduct(productDTOInOrder);

                orderDTOForReception.getOrderItems().add(orderItemDTOForReception);
            }
            return orderDTOForReception;
        }

        return null;
    }

    public static Order updatedOrderDTOtoOrder (UpdatedOrderDTO updatedOrderDTO, OrderRepo orderRepo, ProductRepo productRepo) throws ProductInOrderIsNotUniqueException, EntityDoesNotExistException {
        if (updatedOrderDTO != null && orderRepo != null && productRepo != null) {
            OrderValidator.checkUpdatedOrderDTO(updatedOrderDTO);

            Order order = (Order) orderRepo.get(updatedOrderDTO.getBillingNumberOfUpdatedOrder());
            if (order == null) {
                throw new OrderDoesNotExistException();
            }

            List<OrderItem> orderItems = new ArrayList<OrderItem>();

            for (OrderItemDTOForCreation orderItemDTOForCreation : updatedOrderDTO.getNewOrderItems()) {
                Product product = (Product) productRepo.get(orderItemDTOForCreation.getProductName());
                if (product == null) {
                    throw new ProductDoesNotExistException();
                }

                orderItems.add(
                        new OrderItem(orderItemDTOForCreation.getQuantity().doubleValue(), product.getId())
                );
            }

            order.setOrderItems(orderItems);

            return order;
        }

        return null;
    }
}
