package domain.customer;

import domain.ContractViolationException;
import domain.customer.exceptions.*;
import domain.order.OrderService;
import domain.order.exceptions.OrderDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 20-Dec-16.
 */

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo, OrderService orderService) throws ContractViolationException {
        if (customerRepo == null) {
            throw new ContractViolationException("Parameter \"customerRepo\" is NULL");
        }
        this.customerRepo = customerRepo;
    }

    //todo check if validation before get???
    public void createCustomer(String name) throws ContractViolationException, CustomerAlreadyExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        if (customer != null) {
            throw new CustomerAlreadyExistException();
        }
        customerRepo.add(new Customer(name));
    }

    public Customer getCustomer(String name) throws ContractViolationException, CustomerDoesNotExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        return customer;
    }

    public List<Customer> getAllCustomers () {
        return customerRepo.getAllCustomers();
    }

    public void deleteCustomer (String name) throws ContractViolationException, CustomerDoesNotExistException, OrderDoesNotExistException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        customerRepo.delete(customer);
    }

    private void validateParamName(String name) throws ContractViolationException {
        if (name == null || name.isEmpty()) {
            throw new ContractViolationException("Parameter \"name\" is NULL");
        }
    }

    private void validateCustomersExistence(Customer customer) throws CustomerDoesNotExistException {
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
    }
}
