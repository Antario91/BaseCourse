package domain.customer;

import domain.Entity;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"name\" is NULL");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
