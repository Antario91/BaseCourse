package domain.customer;

import domain.Entity;
import domain.customer.exceptions.*;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) throws NullCustomerNameException {
        CustomerService.validateCustomersConstructorsParams(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}