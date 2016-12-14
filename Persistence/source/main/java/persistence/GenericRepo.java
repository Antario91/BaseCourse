package persistence;

import domain.Entity;

import java.io.Serializable;

/**
 *
 * @param <T> type of Entity's id
 * @param <V> type of Entity's business key and search criterion
 *
 *
 */
public interface GenericRepo <T extends Serializable, V> {
    void add (Entity<T, V> entity);
    Entity<T, V> getByBusinessKey (V businessKeyValue);
    Entity<T, V> getById (T id);
    void update (Entity<T, V> entity);
    void deleteByBusinessKey (V businessKeyValue);
}
