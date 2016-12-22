package domain;

/**
 *
 * @param <V> type of Entity's id
 *
 */

//TODO Consider searchByCriteria()
public interface GenericRepo <V> {
    void add (Entity entity) throws EntityAlreadyExistException;
    Entity get (V id);
    void update (Entity entity);
    void delete (V id) throws EntityDoesNotExistException;
}