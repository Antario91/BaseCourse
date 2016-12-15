package domain.customer;

import domain.Entity;

public class Customer extends Entity<Long, String> {
    private String name;

    private Customer () {}

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBusinessKey() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if ( getId().equals(customer.getId()) ) return false;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        int result = 31 + getId().hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
