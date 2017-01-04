package webservice.endpoints;

import domain.customer.Customer;
import domain.customer.CustomerService;
import domain.customer.exceptions.CustomerAlreadyExistException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import webservice.dtos.customer.CustomerDTO;
import webservice.endpointrequestresponse.customerrequestresponse.*;


@Endpoint
public class CustomerEndpoint {
    final static Logger logger = Logger.getLogger(CustomerEndpoint.class);
    private static final String namespaceUri = "http://www.customerRequestResponse.endpointRequestResponse.webService";
    private CustomerService customerService;

    public CustomerEndpoint(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PayloadRoot(localPart = "CreateCustomerRequest", namespace = namespaceUri)
    public void createCustomer(@RequestPayload CreateCustomerRequest request) throws CustomerAlreadyExistException {
        customerService.createCustomer(request.getCustomer().getName());
    }

    @PayloadRoot(localPart = "GetCustomerRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) throws CustomerDoesNotExistException {
        Customer customer = customerService.getCustomer(request.getCustomerName());

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(convertCustomer(customer));
        return response;
    }

    //TODO add PAGINATION
    @PayloadRoot(localPart = "GetAllCustomersRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllCustomersResponse getAllCustomers (@RequestPayload GetAllCustomersRequest request) {
        GetAllCustomersResponse response = new GetAllCustomersResponse();

        for ( Customer customer : customerService.getAllCustomers() ) {
            response.getCustomer().add( convertCustomer(customer) );
        }

        return response;
    }

    @PayloadRoot(localPart = "DeleteCustomerRequest", namespace = namespaceUri)
    public void deleteCustomer(@RequestPayload DeleteCustomerRequest request) throws CustomerDoesNotExistException, OrderDoesNotExistException {
        customerService.deleteCustomer(request.getCustomerName());
    }

    private CustomerDTO convertCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        return customerDTO;
    }
}
