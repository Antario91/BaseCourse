package domain.customer;

import domain.ParamIsNullException;
import domain.customer.exceptions.*;

import java.util.List;

/**
 * Created by olgo on 20-Dec-16.
 */

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) throws ParamIsNullException {
        if (customerRepo == null) {
            throw new ParamIsNullException("customerRepo");
        }
        this.customerRepo = customerRepo;
    }

    //todo check if validation before get???
    public void createCustomer(String name) throws ParamIsNullException, CustomerAlreadyExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        if (customer != null) {
            throw new CustomerAlreadyExistException();
        }
        customerRepo.add(new Customer(name));
    }

    public Customer getCustomer(String name) throws ParamIsNullException, CustomerDoesNotExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        return customer;
    }

    public List<Customer> getAllCustomers () {
        return customerRepo.getAllCustomers();
    }

    public void deleteCustomer (String name) throws ParamIsNullException, CustomerDoesNotExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        customerRepo.delete(customer);
    }

    private void validateParamName(String name) throws ParamIsNullException {
        if (name == null || name.isEmpty()) {
            throw new ParamIsNullException("name");
        }
    }

    private void validateCustomersExistence(Customer customer) throws CustomerDoesNotExistException {
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
    }
}
