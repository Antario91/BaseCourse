package domain;

/**
 *
 * @param <V> type of Entity's id
 *
 */

//TODO Consider searchByCriteria()
public interface GenericRepo <V> {
    void add (Entity entity) throws NullEntityException;
    Entity get (V id) throws NullIdException;
    void update (Entity entity) throws NullEntityException;
    void delete (Entity entity) throws NullEntityException;
}