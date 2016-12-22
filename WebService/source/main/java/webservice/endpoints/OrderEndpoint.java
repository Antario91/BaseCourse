package webservice.endpoints;

import domain.customer.Customer;
import domain.order.Order;
import domain.order.exceptions.ProductInOrderIsNotUniqueException;
import domain.EntityAlreadyExistException;
import domain.EntityDoesNotExistException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.order.OrderService;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import domain.customer.CustomerRepo;
import domain.order.OrderRepo;
import domain.product.ProductRepo;
import webservice.converters.OrderConverter;

import webservice.endpointrequestresponse.orderrequestresponse.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Endpoint
public class OrderEndpoint {
    final static Logger logger = Logger.getLogger(OrderEndpoint.class);
    private static final String namespaceUri = "http://www.orderRequestResponse.endpointRequestResponse.webService";
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private OrderService orderService;

    public OrderEndpoint(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo, OrderService orderService) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.orderService = orderService;
    }

    @PayloadRoot(localPart = "CreateOrderRequest", namespace = namespaceUri)
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) throws ProductInOrderIsNotUniqueException, EntityDoesNotExistException, EntityAlreadyExistException {
        Order order = OrderConverter.orderDTOForCreationToOrder(request.getOrder(), customerRepo, orderRepo, productRepo);

        orderRepo.add(order);

        CreateOrderResponse response = new CreateOrderResponse();

        response.setOrderPrice( new BigDecimal( orderService.getOrderPrice(order.getBillingNumber()) ).setScale(2, RoundingMode.HALF_UP) );

        return response;
    }

    @PayloadRoot(localPart = "GetOrderRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) throws EntityDoesNotExistException {
        GetOrderResponse response = new GetOrderResponse();
        Order order = (Order) orderRepo.get(request.getOrderBillingNumber());
        if (order == null) {
            throw new OrderDoesNotExistException();
        }
        response.setOrder(
                OrderConverter.orderToOrderDTOForReception(
                        order,
                        customerRepo,
                        orderRepo,
                        productRepo,
                        orderService
                )
        );

        return response;
    }

    @PayloadRoot(localPart = "GetAllCustomersOrdersRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllCustomersOrdersResponse getAllCustomersOrders (@RequestPayload GetAllCustomersOrdersRequest request) throws EntityDoesNotExistException {
        Customer customer = (Customer) customerRepo.get(request.getCustomersName());
        if (customer == null){
            throw new CustomerDoesNotExistException();
        }
//        Вмсето getAllCustomersOrders(customer) использовать getOrdersByCustomerId(String customerId) из OrderRepo
        List<Order> customersOrders = orderRepo.getAllCustomersOrders(customer);

        GetAllCustomersOrdersResponse response = new GetAllCustomersOrdersResponse();

        for (Order order : customersOrders){
            response.getOrders()
                    .add( OrderConverter.orderToOrderDTOForReception(order, customerRepo, orderRepo, productRepo, orderService) );
        }

        return response;
    }

    @PayloadRoot(localPart = "UpdateOrderRequest", namespace = namespaceUri)
    public void updateOrder(@RequestPayload UpdateOrderRequest request) throws ProductInOrderIsNotUniqueException, EntityDoesNotExistException {
        orderRepo.update(
                OrderConverter.updatedOrderDTOtoOrder(request.getUpdatedOrder(), orderRepo, productRepo)
        );
    }

    @PayloadRoot(localPart = "DeleteOrderRequest", namespace = namespaceUri)
    public void deleteOrder(@RequestPayload DeleteOrderRequest request) throws EntityDoesNotExistException {
        orderRepo.delete(request.getOrderBillingNumber());
    }
}
