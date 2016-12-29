package domain;

/**
 *
 * @param <V> type of Entity's id
 *
 */

//TODO Consider searchByCriteria()
public interface GenericRepo <V> {
    void add (Entity entity);
    Entity get (V id);
    void update (Entity entity);
    void delete (Entity entity);
}
//throws IllegalArgumentException