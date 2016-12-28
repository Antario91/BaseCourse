package domain.customer;

import domain.ContractViolationException;
import domain.customer.exceptions.CustomerAlreadyExistException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import domain.order.exceptions.OrderDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface CustomerService {
    void createCustomer(String name) throws ContractViolationException, CustomerAlreadyExistException;
    Customer getCustomer(String name) throws ContractViolationException, CustomerDoesNotExistException;
    List<Customer> getAllCustomers ();
    void deleteCustomer (String name) throws ContractViolationException, CustomerDoesNotExistException, OrderDoesNotExistException;
}
