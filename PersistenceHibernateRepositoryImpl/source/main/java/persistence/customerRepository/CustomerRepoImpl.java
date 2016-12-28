package persistence.customerRepository;

import domain.customer.Customer;
import domain.customer.CustomerRepo;
import persistence.GenericRepoImpl;

import org.hibernate.SessionFactory;

import java.util.List;

public class CustomerRepoImpl extends GenericRepoImpl<String, Customer> implements CustomerRepo {
    public CustomerRepoImpl(Class<Customer> entityClass,
                            String businessKeyPropertyName,
                            SessionFactory sessionFactory) {
        super(entityClass, businessKeyPropertyName, sessionFactory);
    }

    //TODO add PAGINATION
    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> getAllCustomers() {
       List<Customer> customers = (List<Customer>) getSessionFactory().getCurrentSession()
               .createQuery("FROM Customer")
               .list();
        return customers;
    }
}
