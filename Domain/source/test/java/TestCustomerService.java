import domain.customer.Customer;
import domain.customer.CustomerRepo;
import domain.customer.CustomerServiceImpl;
import domain.customer.exceptions.CustomerAlreadyExistException;
import domain.customer.exceptions.CustomerDoesNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.instanceOf;

public class TestCustomerService {
    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    CustomerServiceImpl customerService;

    //need for mockito's annotations
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCustomer_with_name_must_create_customer_if_name_is_not_null_and_customer_with_this_name_are_not_in_repo() throws CustomerAlreadyExistException {
        when(customerRepo.get("Alex")).thenReturn(null);
        customerService.createCustomer("Alex");
    }

    @Test(expected = CustomerAlreadyExistException.class)
    public void createCustomer_with_name_must_throw_exception_if_name_is_not_null_and_customer_with_this_name_are_in_repo() throws CustomerAlreadyExistException {
        when(customerRepo.get("Alex")).thenReturn(new Customer("Alex"));
        customerService.createCustomer("Alex");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomer_with_name_must_throw_exception_if_name_is_null() throws CustomerAlreadyExistException {
        customerService.createCustomer(null);
    }

    @Test
    public void getCustomer_with_name_must_return_customer_if_name_is_not_null_and_customer_with_this_name_are_in_repo() throws CustomerDoesNotExistException {
        when(customerRepo.get("Alex")).thenReturn(new Customer("Alex"));
        customerService.getCustomer("Alex");
    }

    @Test(expected = CustomerDoesNotExistException.class)
    public void getCustomer_with_name_must_throw_exception_if_customer_with_this_name_are_not_in_repo() throws CustomerDoesNotExistException {
        when(customerRepo.get("Alex")).thenReturn(null);
        customerService.getCustomer("Alex");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomer_with_name_must_throw_exception_if_name_is_null() throws CustomerDoesNotExistException {
        customerService.getCustomer(null);
    }
}
