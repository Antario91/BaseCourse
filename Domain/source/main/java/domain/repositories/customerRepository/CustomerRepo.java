package domain.repositories.customerRepository;

import domain.customer.Customer;
import domain.repositories.GenericRepo;

import java.util.List;

public interface CustomerRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Customer> getAllCustomers ();
}
