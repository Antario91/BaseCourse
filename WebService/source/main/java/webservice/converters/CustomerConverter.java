package webservice.converters;

import domain.customer.Customer;
import domain.customer.CustomerRepo;
import domain.customer.exceptions.CustomerAlreadyExistException;
import webservice.dtos.customer.CustomerDTO;
import webservice.dtos.customer.UpdatedCustomerDTO;
import webservice.validators.CustomerValidator;

/**
 * Created by olgo on 14-Dec-16.
 */
public class CustomerConverter {

    public static Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
        if (customerDTO != null) {
            CustomerValidator.checkCustomerDTO(customerDTO);

            return new Customer(customerDTO.getName());
        }
        return null;
    }

    public static CustomerDTO customerToCustomerDTO(Customer customer) {
        if (customer != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName(customer.getName());

            return customerDTO;
        }
        return null;
    }

    public static Customer updatedCustomerDTOtoCustomer(UpdatedCustomerDTO updatedCustomerDTO, CustomerRepo customerRepo) throws CustomerAlreadyExistException {
        if (updatedCustomerDTO != null && customerRepo != null) {
            CustomerValidator.checkUpdatedCustomerDTO(updatedCustomerDTO);

            Customer checkableCustomer = (Customer) customerRepo.get(updatedCustomerDTO.getNewName());
            if (checkableCustomer != null) {
                throw new CustomerAlreadyExistException();
            }

            return new Customer(updatedCustomerDTO.getNewName());
        }
        return null;
    }
}