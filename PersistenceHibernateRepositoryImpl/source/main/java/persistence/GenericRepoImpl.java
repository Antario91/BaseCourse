package persistence;

import domain.Entity;

import domain.repositories.GenericRepo;
import domain.repositories.EntityAlreadyExistException;
import domain.repositories.EntityDoesNotExistException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @param <V> type of Entity's business key
 *
 */

public abstract class GenericRepoImpl<V> implements GenericRepo<V> {
    private Class<Entity> entityClass;
    private String businessKeyPropertyName;
    private EntityAlreadyExistException entityAlreadyExistException;
    private EntityDoesNotExistException entityDoesNotExistException;
    private SessionFactory sessionFactory;

    public GenericRepoImpl(Class<Entity> entityClass, String businessKeyPropertyName, EntityAlreadyExistException entityAlreadyExistException,
                           EntityDoesNotExistException entityDoesNotExistException, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.businessKeyPropertyName = businessKeyPropertyName;
        this.entityAlreadyExistException = entityAlreadyExistException;
        this.entityDoesNotExistException = entityDoesNotExistException;
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    //TODO EntityExists() instead exception
    //TODO добавить где был вызов этого метода EntityDoesNotExistException вместе с проверкой на NULL
    public Entity get(V businessKeyValue) {
        Entity entity = (Entity) sessionFactory.getCurrentSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq(businessKeyPropertyName, businessKeyValue))
                .uniqueResult();

        return entity;
    }


    @SuppressWarnings("unchecked")
    public Entity getById(long id) {
        return (Entity) getSessionFactory().getCurrentSession()
                .get(entityClass, id);
    }

    public void update (Entity entity) {
        getSessionFactory().getCurrentSession().merge(entity);
    }

    public void delete (V businessKeyValue) {
        sessionFactory.getCurrentSession()
                .delete(get(businessKeyValue));
    }

    public SessionFactory getSessionFactory () {
        return sessionFactory;
    }
}
