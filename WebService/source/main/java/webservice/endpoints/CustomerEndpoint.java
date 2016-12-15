package webservice.endpoints;

import domain.customer.Customer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.customerRepository.CustomerRepo;
import persistence.exceptions.customerExceptions.CustomerAlreadyExistException;
import webservice.converters.CustomerConverter;
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

//        CustomerDTO customerDTO = request.getCustomer();
//
//        Customer customer = new Customer(customerDTO.getName());

        customerRepo.add(CustomerConverter.customerDTOtoCustomer(request.getCustomer()));
    }

    @PayloadRoot(localPart = "GetCustomerRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
//        Customer customer = (Customer) customerRepo.getByBusinessKey(request.getCustomerName());
//
//
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setName(customer.getName());

        CustomerDTO customerDTO = CustomerConverter.customerToCustomerDTO( (Customer) customerRepo.getByBusinessKey(request.getCustomerName()) );

        GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerDTO);
        return response;
    }

    @PayloadRoot(localPart = "UpdateCustomerRequest", namespace = namespaceUri)
    public void updateCustomer(@RequestPayload UpdateCustomerRequest request) {
//        UpdatedCustomerDTO updatedCustomerDTO = request.getUpdatedCustomer();
//
//        Customer customer = (Customer) customerRepo.getByBusinessKey(updatedCustomerDTO.getName());
//
//        customer.setName(updatedCustomerDTO.getNewName());
//
//        try {
//            customerRepo.update(customer);
//        } catch (Exception ex){
//            throw new CustomerAlreadyExistException();
//        }
        customerRepo.update( CustomerConverter.updatedCustomerDTOtoCustomer( request.getUpdatedCustomer(), customerRepo) );
    }

    @PayloadRoot(localPart = "DeleteCustomerRequest", namespace = namespaceUri)
    public void deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
        customerRepo.deleteByBusinessKey(request.getCustomerName());
    }
}
