package persistence;

import domain.Entity;

import domain.NullEntityException;
import domain.NullIdException;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import domain.GenericRepo;

/**
 * @param <V> type of Entity's business key
 * @param <T> type of Entity
 */

public class GenericRepoImpl<V, T> implements GenericRepo<V> {
    private Class<T> entityClass;
    private String businessKeyPropertyName;
    private SessionFactory sessionFactory;

    public GenericRepoImpl(Class<T> entityClass, String businessKeyPropertyName, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.businessKeyPropertyName = businessKeyPropertyName;
        this.sessionFactory = sessionFactory;
    }

    public void add (Entity entity) throws NullEntityException {
        if (entity == null){
            throw new NullEntityException();
        }
        sessionFactory.getCurrentSession().save(entity);
    }

    @SuppressWarnings("unchecked")
    public Entity get (V id) throws NullIdException {
        if (id == null) {
            throw new NullIdException();
        }
        return (Entity) sessionFactory.getCurrentSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq(businessKeyPropertyName, id))
                .uniqueResult();
    }

    public void update (Entity entity) throws NullEntityException {
        if (entity == null) {
            throw new NullEntityException();
        }
        sessionFactory.getCurrentSession().merge(entity);
    }

    @SuppressWarnings("unchecked")
    public void delete (Entity entity) throws NullEntityException {
        if (entity == null) {
            throw new NullEntityException();
        }
        sessionFactory.getCurrentSession().delete(entity);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
