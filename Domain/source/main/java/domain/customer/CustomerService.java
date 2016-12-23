package domain.customer;

import domain.ParamIsNullException;
import domain.customer.exceptions.CustomerAlreadyExistException;
import domain.customer.exceptions.CustomerDoesNotExistException;

import java.util.List;

/**
 * Created by olgo on 23-Dec-16.
 */
public interface CustomerService {
    void createCustomer(String name) throws ParamIsNullException, CustomerAlreadyExistException;
    Customer getCustomer(String name) throws ParamIsNullException, CustomerDoesNotExistException;
    List<Customer> getAllCustomers ();
    void deleteCustomer (String name) throws ParamIsNullException, CustomerDoesNotExistException;
}
