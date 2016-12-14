package persistence;

import domain.Entity;

import org.hibernate.FlushMode;
import persistence.exceptions.EntityAlreadyExistException;
import persistence.exceptions.EntityDoesNotExistException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;


/**
 *
 * @param <T> type of Entity's id
 * @param <V> type of Entity's business key
 */

public class GenericRepoImpl<T extends Serializable, V> implements GenericRepo<T, V> {
    private Class<Entity<T, V>> entityClass;
    private String businessKeyPropertyName;
    private EntityAlreadyExistException entityAlreadyExistException;
    private EntityDoesNotExistException entityDoesNotExistException;
    private SessionFactory sessionFactory;

    public GenericRepoImpl(Class<Entity<T, V>> entityClass, String businessKeyPropertyName, EntityAlreadyExistException entityAlreadyExistException,
                           EntityDoesNotExistException entityDoesNotExistException, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.businessKeyPropertyName = businessKeyPropertyName;
        this.entityAlreadyExistException = entityAlreadyExistException;
        this.entityDoesNotExistException = entityDoesNotExistException;
        this.sessionFactory = sessionFactory;
    }

    public void add (Entity<T, V> entity) {
        try {
//            if (entity.getId() instanceof  Number)

//            if (getByBusinessKey(businessKeyPropertyName, entity.getBusinessKey())
//                    .getId()
//                    .equals(comparisonCriterion)){

            getByBusinessKey(entity.getBusinessKey());
            throw entityAlreadyExistException;

        } catch (EntityDoesNotExistException ex){
            sessionFactory.getCurrentSession()
                    .save(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public Entity<T, V> getByBusinessKey(V businessKeyValue) {
        Entity<T, V> entity = (Entity<T, V>) sessionFactory.getCurrentSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq(businessKeyPropertyName, businessKeyValue))
                .uniqueResult();

        if (entity == null) {
            throw entityDoesNotExistException;
        }
        return entity;
    }


    @SuppressWarnings("unchecked")
    public Entity<T, V> getById(T id) {
        return (Entity<T, V>) getSessionFactory().getCurrentSession()
                .get(entityClass, id);
    }

    public void update (Entity<T, V> entity) {
//        try{
//            Entity<T, V> checkableEntity = getByBusinessKey(entity.getBusinessKey());
//
//            if ( !entity.getId().equals(checkableEntity.getId()) ){
//                throw entityAlreadyExistException;
//            } else {
//                sessionFactory.getCurrentSession().merge(entity);
//            }
//        } catch (EntityDoesNotExistException ex) {
//            sessionFactory.getCurrentSession().merge(entity);
//        }
        getSessionFactory().getCurrentSession().merge(entity);

    }

    public void deleteByBusinessKey (V businessKeyValue) {
        sessionFactory.getCurrentSession()
                .delete(getByBusinessKey(businessKeyValue));
    }

    public SessionFactory getSessionFactory () {
        return sessionFactory;
    }

    public Class<Entity<T, V>> getEntityClass () {
        return entityClass;
    }
}
