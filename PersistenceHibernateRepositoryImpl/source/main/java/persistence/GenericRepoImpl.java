package persistence;

import domain.Entity;

import domain.repositories.EntityAlreadyExistException;
import domain.repositories.EntityDoesNotExistException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import domain.repositories.GenericRepo;

/**
 * @param <V> type of Entity's business key
 */

public class GenericRepoImpl<V> implements GenericRepo<V> {
    private Class<Entity<V>> entityClass;
    private String businessKeyPropertyName;
    private EntityAlreadyExistException entityAlreadyExistException;
    private EntityDoesNotExistException entityDoesNotExistException;
    private SessionFactory sessionFactory;

    public GenericRepoImpl(Class<Entity<V>> entityClass, String businessKeyPropertyName, EntityAlreadyExistException entityAlreadyExistException,
                           EntityDoesNotExistException entityDoesNotExistException, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.businessKeyPropertyName = businessKeyPropertyName;
        this.entityAlreadyExistException = entityAlreadyExistException;
        this.entityDoesNotExistException = entityDoesNotExistException;
        this.sessionFactory = sessionFactory;
    }

    public void add(Entity<V> entity) throws EntityAlreadyExistException {
        if (get(entity.getBusinessKey()) != null) {
            throw entityAlreadyExistException;
        }

        sessionFactory.getCurrentSession()
                .save(entity);
    }

    @SuppressWarnings("unchecked")
    //TODO добавить где был вызов этого метода EntityDoesNotExistException вместе с проверкой на NULL
    public Entity get(V businessKeyValue) {
        return (Entity) sessionFactory.getCurrentSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq(businessKeyPropertyName, businessKeyValue))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public Entity getById(long id) {
        return (Entity) getSessionFactory().getCurrentSession()
                .get(entityClass, id);
    }

    public void update(Entity entity) {
        getSessionFactory().getCurrentSession().merge(entity);
    }

    @SuppressWarnings("unchecked")
    public void delete(V businessKeyValue) throws EntityDoesNotExistException {
        Entity<V> entity = (Entity<V>) get(businessKeyValue);
        if (entity == null){
            throw entityDoesNotExistException;
        }

        sessionFactory.getCurrentSession()
                .delete(entity);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
