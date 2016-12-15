package webservice.validators;

import webservice.dtos.customer.CustomerDTO;
import webservice.dtos.customer.UpdatedCustomerDTO;
import webservice.exceptions.NullCustomerNameException;

/**
 * Created by olgo on 14-Dec-16.
 */
public class CustomerValidator {
    public static boolean checkCustomerDTO (CustomerDTO customerDTO) {
        if ( customerDTO.getName() == null || customerDTO.getName().isEmpty() ){
            throw new NullCustomerNameException();
        }
        return true;
    }

    public static boolean checkUpdatedCustomerDTO (UpdatedCustomerDTO updatedCustomerDTO) {
        if (updatedCustomerDTO.getName() == null ||
                updatedCustomerDTO.getName().isEmpty() ||
                updatedCustomerDTO.getNewName() == null ||
                updatedCustomerDTO.getNewName().isEmpty()){
            throw new NullCustomerNameException();
        }
        return true;
    }
}
