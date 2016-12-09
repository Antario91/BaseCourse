package webservice.endpoints;

import domain.customer.Customer;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.customerRepository.CustomerRepo;
import webservice.dtos.customer.CustomerDTO;
import webservice.dtos.customer.UpdatedCustomerDTO;
import webservice.endpointrequestresponse.customerrequestresponse.*;


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
        CustomerDTO customerDTO = request.getCustomer();

        Customer customer = new Customer(customerDTO.getName());

        customerRepo.add(customer);
    }

    @PayloadRoot(localPart = "GetCustomerRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
        Customer customer = (Customer) customerRepo.getByBusinessKey(request.getCustomerName());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerDTO);
        return response;
    }

    @PayloadRoot(localPart = "UpdateCustomerRequest", namespace = namespaceUri)
    public void updateCustomer(@RequestPayload UpdateCustomerRequest request) {
        UpdatedCustomerDTO updatedCustomerDTO = request.getUpdatedCustomer();

        Customer customer = (Customer) customerRepo.getByBusinessKey(updatedCustomerDTO.getName());

        customer.setName(updatedCustomerDTO.getNewName());

        customerRepo.update(customer);
    }

    @PayloadRoot(localPart = "DeleteCustomerRequest", namespace = namespaceUri)
    public void deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
        customerRepo.deleteByBusinessKey(request.getCustomerName());
    }
}
