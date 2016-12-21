package domain.customer;

import domain.Entity;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) throws NullCustomerNameException {
        CustomerService.validateIncomingDataInConstructor(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}