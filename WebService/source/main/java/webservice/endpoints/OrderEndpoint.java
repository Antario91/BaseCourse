package webservice.endpoints;

import domain.order.*;
import domain.order.exceptions.ProductInOrderIsAlreadyOrderedException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import domain.product.Product;
import domain.product.ProductService;
import domain.product.exceptions.NotAvailableProductPriceException;
import domain.product.exceptions.ProductDoesNotExistException;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import utils.XMLGregorianCalendarProducer.DateProducer;

import webservice.dtos.order.OrderDTOForReception;
import webservice.dtos.order.OrderItemDTOForCreation;
import webservice.dtos.order.OrderItemDTOForReception;
import webservice.dtos.product.ProductDTOInOrder;
import webservice.endpointrequestresponse.orderrequestresponse.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Endpoint
public class OrderEndpoint {
    final static Logger logger = Logger.getLogger(OrderEndpoint.class);
    private static final String namespaceUri = "http://www.orderRequestResponse.endpointRequestResponse.webService";
    private OrderService orderService;
    private ProductService productService;

    public OrderEndpoint(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @PayloadRoot(localPart = "CreateOrderRequest", namespace = namespaceUri)
    public void createOrder(@RequestPayload CreateOrderRequest request) throws CustomerDoesNotExistException,
            ProductInOrderIsAlreadyOrderedException, ProductDoesNotExistException, NotAvailableProductPriceException {
        String customersName = request.getOrder().getOrdersCustomersName();
        List<OrderItemDTOForCreation> orderItemDTOForCreations = request.getOrder().getOrderItems();

        OrderItem[] orderItems = new OrderItem[orderItemDTOForCreations.size()];

        orderService.createOrder(customersName,
                convertOrderItems(orderItemDTOForCreations).toArray(orderItems));
    }


    @PayloadRoot(localPart = "GetOrderRequest", namespace = namespaceUri)
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) throws OrderDoesNotExistException, NotAvailableProductPriceException {
        Order order = orderService.getOrder(request.getOrderBillingNumber());

        GetOrderResponse response = new GetOrderResponse();

        response.setOrder(convertOrder(order));

        return response;
    }

    @PayloadRoot(localPart = "GetAllCustomersOrdersRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllCustomersOrdersResponse getAllCustomersOrders (@RequestPayload GetAllCustomersOrdersRequest request)
            throws CustomerDoesNotExistException, NotAvailableProductPriceException {
        List<Order> customersOrders = orderService.getAllCustomerOrders(request.getCustomersName());

        GetAllCustomersOrdersResponse response = new GetAllCustomersOrdersResponse();

        for (Order order : customersOrders){
            response.getOrders().add( convertOrder(order) );
        }

        return response;
    }

    @PayloadRoot(localPart = "AddOrderItemsRequest", namespace = namespaceUri)
    public void AddOrderItems(@RequestPayload AddOrderItemsRequest request) throws ProductInOrderIsAlreadyOrderedException,
            ProductDoesNotExistException, OrderDoesNotExistException, NotAvailableProductPriceException {
        OrderItem[] items = new OrderItem[request.getOrderItemDTOForCreation().size()];
        orderService.addOrderItems(request.getOrdersBillingNumber(), convertOrderItems(request.getOrderItemDTOForCreation()).toArray(items));
    }

    @PayloadRoot(localPart = "DeleteOrderItemsRequest", namespace = namespaceUri)
    @ResponsePayload
    public void DeleteOrderItems(@RequestPayload DeleteOrderItemsRequest request) throws OrderDoesNotExistException,
            NotAvailableProductPriceException {
        orderService.deleteOrderItems(request.getOrdersBillingNumber(), request.getProductIds());
    }

    @PayloadRoot(localPart = "DeleteOrderRequest", namespace = namespaceUri)
    public void deleteOrder(@RequestPayload DeleteOrderRequest request) throws OrderDoesNotExistException {
        orderService.deleteOrder(request.getOrderBillingNumber());
    }

    private List<OrderItem> convertOrderItems(List<OrderItemDTOForCreation> orderItemDTOForCreations) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (OrderItemDTOForCreation tempItem : orderItemDTOForCreations) {
            if (tempItem.getQuantity() == null) {
                throw new IllegalArgumentException("Parameter \"quantity\" is illegal");
            }
            orderItems.add(
                    new OrderItem(tempItem.getQuantity().doubleValue(), tempItem.getProductName())
            );
        }
        return orderItems;
    }

    private OrderDTOForReception convertOrder(Order order) throws NotAvailableProductPriceException {
        OrderDTOForReception orderDTOForReception = new OrderDTOForReception();
        orderDTOForReception.setOrdersCustomersName(order.getCustomerId());
        orderDTOForReception.setPlacingDate(DateProducer.produce(order.getPlacingDate()));
        orderDTOForReception.setBillingNumber(order.getBillingNumber());
        orderDTOForReception.setOrderPrice(
                new BigDecimal( orderService.getOrderPrice(order.getBillingNumber()) ).setScale(2, RoundingMode.HALF_UP)
        );

        List<Product> ordersProducts = productService.getProducts(order.getOrdersProductsIds());

        Map<String, Product> products = new HashMap<String, Product>();
        for (Product tempProduct : ordersProducts) {
            products.put(tempProduct.getName(), tempProduct);
        }

        for (OrderItem item : order.getOrderItems()) {
            ProductDTOInOrder productDTOInOrder = new ProductDTOInOrder();
            OrderItemDTOForReception orderItemDTOForReception = new OrderItemDTOForReception();
            Product product = products.get(item.getProductId());

            productDTOInOrder.setProductName(product.getName());
            productDTOInOrder.setProductUnits(product.getUnits());
            productDTOInOrder.setProductPrice(
                    new BigDecimal( product.getProductPrice(order.getPlacingDate()) ).setScale(2, RoundingMode.HALF_UP));

            orderItemDTOForReception.setQuantity( new BigDecimal(item.getQuantity()).setScale(2, RoundingMode.HALF_UP) );
            orderItemDTOForReception.setProduct(productDTOInOrder);

            orderDTOForReception.getOrderItems().add(orderItemDTOForReception);
        }

        return orderDTOForReception;
    }
}
