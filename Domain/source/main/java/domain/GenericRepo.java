package domain;

/**
 *
 * @param <V> type of Entity's business key and search criterion
 *
 */

//TODO Consider searchByCriteria()
public interface GenericRepo <V> {
    void add (Entity<V> entity) throws EntityAlreadyExistException;
    Entity get (V businessKeyValue);
    Entity getById (long id);
    void update (Entity<V> entity);
    void delete (V businessKeyValue) throws EntityDoesNotExistException;
}