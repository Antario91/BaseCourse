package webservice.endpoints;

import domain.customer.Customer;
import domain.order.Order;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.customerRepository.CustomerRepo;
import persistence.orderRepository.OrderRepo;
import persistence.productRepository.ProductRepo;
import webservice.converters.OrderConverter;

import webservice.endpointrequestresponse.orderrequestresponse.*;

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
        Order order = OrderConverter.orderDTOForCreationToOrder(request.getOrder(), customerRepo, orderRepo, productRepo);

        orderRepo.add(order);

        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderPrice( order.getOrderPrice(orderRepo.getAllOrdersProducts(order)) );

        return response;
    }

    @PayloadRoot(localPart = "GetOrderRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) {
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
        orderRepo.update(
                OrderConverter.updatedOrderDTOtoOrder(request.getUpdatedOrder(), orderRepo, productRepo)
        );
    }

    @PayloadRoot(localPart = "DeleteOrderRequest", namespace = namespaceUri)
    public void deleteOrder(@RequestPayload DeleteOrderRequest request) {
        orderRepo.deleteByBusinessKey(request.getOrderBillingNumber());
    }
}
