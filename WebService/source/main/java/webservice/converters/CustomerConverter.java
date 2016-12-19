package webservice.converters;

import domain.customer.Customer;
import domain.repositories.customerRepository.CustomerRepo;
import domain.repositories.customerRepository.CustomerAlreadyExistException;
import domain.repositories.customerRepository.CustomerDoesNotExistException;
import webservice.dtos.customer.CustomerDTO;
import webservice.dtos.customer.UpdatedCustomerDTO;
import webservice.validators.CustomerValidator;

/**
 * Created by olgo on 14-Dec-16.
 */
public class CustomerConverter {

    public static Customer customerDTOtoCustomer (CustomerDTO customerDTO){
        if (customerDTO != null) {
            CustomerValidator.checkCustomerDTO(customerDTO);

            return new Customer(customerDTO.getName());
        }
        return null;
    }

    public static CustomerDTO customerToCustomerDTO (Customer customer){
        if (customer != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName(customer.getName());

            return customerDTO;
        }
        return null;
    }

    public static Customer updatedCustomerDTOtoCustomer (UpdatedCustomerDTO updatedCustomerDTO, CustomerRepo customerRepo){
        if (updatedCustomerDTO != null && customerRepo != null) {
            CustomerValidator.checkUpdatedCustomerDTO(updatedCustomerDTO);

            try {
                if (customerRepo.getByBusinessKey(updatedCustomerDTO.getNewName()) instanceof Customer) {
                    throw new CustomerAlreadyExistException();
                }
            } catch (CustomerDoesNotExistException ex) {
            }

            Customer customer = new Customer( updatedCustomerDTO.getNewName() );
            return customer;
        }
        return null;
    }
}
