package domain.customer;

import domain.Entity;

public class Customer extends Entity<String> {
    private final String name;

    //Для того, чтобы name был final, в конструкторе присваивается null
    private Customer () {
        name = null;
    }

    public Customer(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getBusinessKey() {
        return name;
    }
}