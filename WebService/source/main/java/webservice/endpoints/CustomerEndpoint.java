package webservice.endpoints;

import domain.customer.Customer;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import domain.repositories.customerRepository.CustomerRepo;
import webservice.converters.CustomerConverter;
import webservice.dtos.customer.CustomerDTO;
import webservice.endpointrequestresponse.customerrequestresponse.*;

import java.util.List;


@Endpoint
public class CustomerEndpoint {
    final static Logger logger = Logger.getLogger(CustomerEndpoint.class);
    private static final String namespaceUri = "http://www.customerRequestResponse.endpointRequestResponse.webService";
    private CustomerRepo customerRepo;

    public CustomerEndpoint(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @PayloadRoot(localPart = "CreateCustomerRequest", namespace = namespaceUri)
    public void createCustomer(@RequestPayload CreateCustomerRequest request) {
        customerRepo.add(CustomerConverter.customerDTOtoCustomer(request.getCustomer()));
    }

    @PayloadRoot(localPart = "GetCustomerRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
        CustomerDTO customerDTO = CustomerConverter.customerToCustomerDTO( (Customer) customerRepo.getByBusinessKey(request.getCustomerName()) );

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerDTO);
        return response;
    }

    //TODO add PAGINATION
    @PayloadRoot(localPart = "GetAllCustomersRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllCustomersResponse getAllCustomers (@RequestPayload GetAllCustomersRequest request) {
        GetAllCustomersResponse response = new GetAllCustomersResponse();

        for ( Customer customer : (List<Customer>) customerRepo.getAllCustomers() ) {
            response.getCustomer().add( CustomerConverter.customerToCustomerDTO(customer) );
        }

        return response;
    }

    @PayloadRoot(localPart = "UpdateCustomerRequest", namespace = namespaceUri)
    public void updateCustomer(@RequestPayload UpdateCustomerRequest request) {
        customerRepo.update( CustomerConverter.updatedCustomerDTOtoCustomer( request.getUpdatedCustomer(), customerRepo) );
    }

    @PayloadRoot(localPart = "DeleteCustomerRequest", namespace = namespaceUri)
    public void deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
        customerRepo.deleteByBusinessKey(request.getCustomerName());
    }
}
