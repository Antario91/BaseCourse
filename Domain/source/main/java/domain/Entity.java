package domain;

/**
 *
 * @param <T> type of Entity's id
 * @param <V> type of Entity's business key
 */

public abstract class Entity<T, V> {
    private T id;

    public T getId() {
        return id;
    }
    public void setId(T id) {
        this.id = id;
    }

    public abstract V getBusinessKey();
}
