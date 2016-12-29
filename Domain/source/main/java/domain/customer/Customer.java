package domain.customer;

import domain.ContractViolationException;
import domain.Entity;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) {
        checkConstructorParameterForNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void checkConstructorParameterForNull(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
    }
}
