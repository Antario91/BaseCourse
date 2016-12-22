package domain.customer;

import domain.customer.exceptions.*;

import java.util.List;

/**
 * Created by olgo on 20-Dec-16.
 */

public class CustomerService {
    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) throws NullCustomerRepoException {
        if (customerRepo == null) {
            throw new NullCustomerRepoException();
        }
        this.customerRepo = customerRepo;
    }

    static void validateCustomersConstructorsParams(String name) throws NullCustomerNameException {
        validateParamName(name);
    }

    private static void validateParamName(String name) throws NullCustomerNameException {
        if (name == null || name.isEmpty()) {
            throw new NullCustomerNameException();
        }
    }

    private void validateCustomersExistence(Customer customer) throws CustomerDoesNotExistException {
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
    }

    public void createCustomer(String name) throws CustomerAlreadyExistException, NullCustomerNameException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        if (customer != null) {
            throw new CustomerAlreadyExistException();
        }
        customerRepo.add(new Customer(name));
    }

    public Customer getCustomer(String name) throws CustomerDoesNotExistException, NullCustomerNameException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        return customer;
    }

    public List<Customer> getAllCustomers () {
        return customerRepo.getAllCustomers();
    }

    public void deleteCustomer (String name) throws CustomerDoesNotExistException, NullCustomerNameException {
        validateParamName(name);
        Customer customer = (Customer) customerRepo.get(name);
        validateCustomersExistence(customer);
        customerRepo.delete(customer);
    }
}
