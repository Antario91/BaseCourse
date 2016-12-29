package domain.customer;

import domain.customer.exceptions.*;

import java.util.List;

/**
 * Created by olgo on 20-Dec-16.
 */

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        if (customerRepo == null) {
            throw new IllegalArgumentException("Parameter \"customerRepo\" is NULL");
        }
        this.customerRepo = customerRepo;
    }

    //todo check if validation before get???
    public void createCustomer(String name) throws CustomerAlreadyExistException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Customer customer = (Customer) customerRepo.get(name);
        if (customer != null) {
            throw new CustomerAlreadyExistException();
        }

        customerRepo.add(new Customer(name));
    }

    public Customer getCustomer(String name) throws CustomerDoesNotExistException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Customer customer = (Customer) customerRepo.get(name);
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }

        return customer;
    }

    public List<Customer> getAllCustomers () {
        return customerRepo.getAllCustomers();
    }

    public void deleteCustomer (String name) throws CustomerDoesNotExistException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        Customer customer = (Customer) customerRepo.get(name);
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }

        customerRepo.delete(customer);
    }
}
