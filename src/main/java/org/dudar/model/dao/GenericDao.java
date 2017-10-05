package org.dudar.model.dao;

import java.util.Optional;

/**
 *
 * @author dmitry
 *
 * @param <T> type of the object being processed by DAO
 */
public interface GenericDao<T> extends AutoCloseable{

    /** Creates a new record that corresponds to the object */
    void create(T object);

    /** Saves the state of the object in the database */
    void update(T object);

    /** Deletes record from database */
    void delete(T object);

    /** Returns an object of the corresponding record with the primary key */
    Optional<T> getById(Long key);

    void close();
}