package persistence.customerRepository;

import domain.customer.Customer;
import persistence.GenericRepo;

import java.util.List;

public interface CustomerRepo extends GenericRepo<Long, String> {
    List<Customer> getAllCustomers ();
}
