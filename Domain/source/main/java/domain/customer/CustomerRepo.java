package domain.customer;

import domain.GenericRepo;

import java.util.List;

public interface CustomerRepo extends GenericRepo<String> {
    //TODO add PAGINATION
    List<Customer> getAllCustomers();
}
