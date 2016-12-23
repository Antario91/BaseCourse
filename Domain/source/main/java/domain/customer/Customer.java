package domain.customer;

import domain.ParamIsNullException;
import domain.Entity;

public class Customer extends Entity {
    private final String name;

    private Customer () {
        name = null;
    }

    public Customer(String name) throws ParamIsNullException {
        validateConstructorsParams(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateConstructorsParams(String name) throws ParamIsNullException {
        if (name == null || name.isEmpty()) {
            throw new ParamIsNullException("name");
        }
    }
}