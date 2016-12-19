package domain.repositories;

import domain.Entity;

/**
 *
 * @param <V> type of Entity's business key and search criterion
 *
 */

//TODO Consider searchByCriteria()
public interface GenericRepo <V> {
    void add (Entity entity);
    Entity get (V businessKeyValue);
    Entity getById (long id);
    void update (Entity entity);
    void delete (V businessKeyValue);
}