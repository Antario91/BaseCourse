package persistence;

import domain.DBUser;
import handlingException.UserAlreadyExistException;
import handlingException.UserDoesNotExistException;

/**
 * Created by olgo on 15-Nov-16.
 */
public interface DBUserREPO {
    void createDBUser(DBUser user) throws UserAlreadyExistException;
    DBUser getDBUser(String username) throws UserDoesNotExistException;
    void updateDBUser(DBUser user) throws UserAlreadyExistException, UserDoesNotExistException;
    void deleteDBUser(String username) throws UserDoesNotExistException;
}
