package domain.customer;

import domain.Entity;

public class Customer extends Entity {
    private final String name;

    //Для того, чтобы name был final, в конструкторе присваивается null
    private Customer () {
        name = null;
    }

    public Customer(String name) {
        if (name == null){
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}