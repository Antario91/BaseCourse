package domain.customer;

/**
 * Created by olgo on 20-Dec-16.
 */
public class CustomerService {
    public static void validateIncomingDataInConstructor (String name) throws NullCustomerNameException {
        if (name == null || name.isEmpty()){
            throw new NullCustomerNameException();
        }
    }
}
