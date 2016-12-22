package domain.customer;

import domain.customer.exceptions.NullCustomerNameException;

/**
 * Created by olgo on 20-Dec-16.
 */

public class CustomerService {
    public static void validateCustomersConstructorsParams(String name) throws NullCustomerNameException {
        if (name == null || name.isEmpty()){
            throw new NullCustomerNameException();
        }
    }
}
