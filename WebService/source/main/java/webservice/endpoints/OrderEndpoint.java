package webservice.endpoints;

import domain.customer.Customer;
import domain.exceptions.ProductInOrderIsNotUniqueException;
import domain.order.Order;
import domain.order.OrderItem;
import domain.product.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.customerRepository.CustomerRepo;
import persistence.orderRepository.OrderRepo;
import persistence.productRepository.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.converters.OrderConverter;
import webservice.dtos.order.*;

import webservice.dtos.product.ProductDTOInOrder;
import webservice.endpointrequestresponse.orderrequestresponse.*;

import java.util.ArrayList;
import java.util.List;


@Endpoint
public class OrderEndpoint {
    final static Logger logger = Logger.getLogger(OrderEndpoint.class);
    private static final String namespaceUri = "http://www.orderRequestResponse.endpointRequestResponse.webService";
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    public OrderEndpoint(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @PayloadRoot(localPart = "CreateOrderRequest", namespace = namespaceUri)
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
//        OrderDTOForCreation orderDTOForCreation = request.getOrder();
//
//        long customerId = customerRepo.getByBusinessKey(orderDTOForCreation.getOrdersCustomersName())
//                .getId();
//
//        List<OrderItem> orderItems = new ArrayList<OrderItem>();
//        for (OrderItemDTOForCreation orderItemDTO : orderDTOForCreation.getOrderItems()) {
//            orderItems.add(
//                    new OrderItem(orderItemDTO.getQuantity(),
//                            productRepo.getByBusinessKey(orderItemDTO.getProductName()).getId())
//            );
//        }
//
//        Order order = new Order(customerId);
//
//        order.setOrderItems(orderItems);

        Order order = OrderConverter.orderDTOForCreationToOrder(request.getOrder(), customerRepo, productRepo);

        orderRepo.add(order);

        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderPrice( order.getOrderPrice(orderRepo.getAllOrdersProducts(order)) );
        return response;
    }

    @PayloadRoot(localPart = "GetOrderRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) {
//        Order order = (Order) orderRepo.getByBusinessKey( request.getOrderBillingNumber() );
//
//        OrderDTOForReception orderDTOForReception = new OrderDTOForReception();
//        orderDTOForReception.setBillingNumber(order.getBillingNumber());
//        orderDTOForReception.setPlacingDate( DateProducer.produce(order.getPlacingDate()) );
//        orderDTOForReception.setOrdersCustomersName(
//                customerRepo.getById(order.getCustomerId())
//                .getBusinessKey()
//        );
//        orderDTOForReception.setOrderPrice( order.getOrderPrice(orderRepo.getAllOrdersProducts(order)) );
//
//        for (OrderItem orderItem : order.getOrderItems()) {
//            Product product = (Product) productRepo.getById( orderItem.getProductId() );
//
//            ProductDTOInOrder productDTOInOrder = new ProductDTOInOrder();
//            productDTOInOrder.setProductName( product.getName() );
//            productDTOInOrder.setProductUnits( product.getUnits() );
//            productDTOInOrder.setProductPrice( product.getProductPrice(order.getPlacingDate()) );
//
//            OrderItemDTOForReception orderItemDTOForReception = new OrderItemDTOForReception();
//            orderItemDTOForReception.setQuantity(orderItem.getQuantity());
//            orderItemDTOForReception.setProduct( productDTOInOrder );
//
//            orderDTOForReception.getOrderItems().add(orderItemDTOForReception);
//        }

        GetOrderResponse response = new GetOrderResponse();
        response.setOrder(
                OrderConverter.orderToOrderDTOForReception(
                        (Order) orderRepo.getByBusinessKey( request.getOrderBillingNumber() ),
                        customerRepo,
                        orderRepo,
                        productRepo
                )
        );


        return response;
    }

    @PayloadRoot(localPart = "GetAllCustomersOrdersRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllCustomersOrdersResponse getAllCustomersOrders (@RequestPayload GetAllCustomersOrdersRequest request){
        List<Order> customersOrders = orderRepo.getAllCustomersOrders(
                (Customer) customerRepo.getByBusinessKey( request.getCustomersName() )
        );

        GetAllCustomersOrdersResponse response = new GetAllCustomersOrdersResponse();

        for (Order order : customersOrders){
            response.getOrders()
                    .add( OrderConverter.orderToOrderDTOForReception(order, customerRepo, orderRepo, productRepo) );
        }

        return response;
    }

    @PayloadRoot(localPart = "UpdateOrderRequest", namespace = namespaceUri)
    public void updateOrder(@RequestPayload UpdateOrderRequest request) {
//        UpdatedOrderDTO updatedOrderDTO = request.getUpdatedOrder();
//
//        Order order = (Order) orderRepo.getByBusinessKey(updatedOrderDTO.getBillingNumberOfUpdatedOrder());
//
//        List<OrderItem> orderItems = new ArrayList<OrderItem>();
//
//        for (OrderItemDTOForCreation orderItemDTOForCreation : updatedOrderDTO.getNewOrderItems()){
//            orderItems.add(
//                    new OrderItem(orderItemDTOForCreation.getQuantity(),
//                            productRepo.getByBusinessKey(orderItemDTOForCreation.getProductName()).getId())
//            );
//        }
//
//        order.setOrderItems(orderItems);

        orderRepo.update(
                OrderConverter.updatedOrderDTOtoOrder(request.getUpdatedOrder(), orderRepo, productRepo)
        );
    }

    @PayloadRoot(localPart = "DeleteOrderRequest", namespace = namespaceUri)
    public void deleteOrder(@RequestPayload DeleteOrderRequest request) {
        orderRepo.deleteByBusinessKey(request.getOrderBillingNumber());
    }


}
