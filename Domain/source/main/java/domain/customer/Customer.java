package domain.customer;

import domain.ContractViolationException;
import domain.Entity;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) throws ContractViolationException {
        validateConstructorsParams(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateConstructorsParams(String name) throws ContractViolationException {
        if (name == null || name.isEmpty()) {
            throw new ContractViolationException("Parameter \"name\" is NULL");
        }
    }
}