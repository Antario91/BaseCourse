package persistence;

import domain.Entity;

/**
 *
 * @param <T> type of Entity's id
 * @param <V> type of Entity's business key and search criterion
 *
 *
 */
public interface GenericRepo <T, V> {
    void add (Entity<T, V> entity);
    Entity<T, V> getByBusinessKey (V businessKeyValue);
    void update (Entity<T, V> entity);
    void deleteByBusinessKey (V businessKeyValue);
}
