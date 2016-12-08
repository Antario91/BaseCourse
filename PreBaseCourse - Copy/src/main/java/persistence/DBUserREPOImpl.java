package persistence;

import handlingException.UserAlreadyExistException;
import handlingException.UserDoesNotExistException;
import org.hibernate.criterion.Restrictions;
import domain.DBUser;
import org.hibernate.SessionFactory;


/**
 * Created by olgo on 15-Nov-16.
 */
public class DBUserREPOImpl implements DBUserREPO {

    SessionFactory sessionFactory;

    public void createDBUser(DBUser user) throws UserAlreadyExistException {
        try {
            if (getDBUser(user.getUsername()).getUserId() != 0) {
                throw new UserAlreadyExistException();
            }
        } catch (UserDoesNotExistException ex) {
            sessionFactory.getCurrentSession()
                    .save(user);
        }
    }

    public DBUser getDBUser(String username) throws UserDoesNotExistException {
        DBUser user = (DBUser) sessionFactory.getCurrentSession()
                .createCriteria(DBUser.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();

        if (user == null) {
            throw new UserDoesNotExistException();
        }

        return user;
    }

    public void updateDBUser(DBUser user) throws UserAlreadyExistException {
        try {
            getDBUser(user.getUsername());
            if (user.getUserId() == getDBUser(user.getUsername()).getUserId()) {
                sessionFactory.getCurrentSession()
                        .update(user);
            } else {
                throw new UserAlreadyExistException();
            }
        } catch (UserDoesNotExistException ex) {
            sessionFactory.getCurrentSession()
                    .update(user);
        }


    }

    public void deleteDBUser(String username) throws UserDoesNotExistException {
        sessionFactory.getCurrentSession()
                .delete(getDBUser(username));
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
