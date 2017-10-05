package org.dudar.model.dao;

import java.sql.Connection;

/**
 * Interface that have to be implemented by custom Connection wrapper
 *
 * @author Dmitry
 *
 */
public interface DaoConnection extends AutoCloseable {

    void begin();

    void commit();

    void rollback();

    void close();
}