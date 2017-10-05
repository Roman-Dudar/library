package org.dudar.model.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.model.dao.DaoConnection;

/**
 * Class that represents Connection wrapper for providing correct transaction
 * execution
 *
 * @author Dmitry
 *
 */
public class JdbcDaoConnection implements DaoConnection {

    private static final Logger LOGGER = LogManager.getLogger(JdbcDaoConnection.class);

    private Connection connection;

    /** check if exists an active (uncommitted) transaction */
    private boolean inTransaction = false;

    public JdbcDaoConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void begin() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
            LOGGER.info("Transaction has began");
        } catch (SQLException e) {
            LOGGER.error("JdbcDaoConnection begin error", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
            LOGGER.info("Transaction is commited");
        } catch (SQLException e) {
            LOGGER.error("JdbcDaoConnection commit error", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
            LOGGER.info("Transaction is rollbacked");
        } catch (SQLException e) {
            LOGGER.error("JdbcDaoConnection rollback error", e);
        }
    }

    public void close() {
        if (inTransaction) {
            rollback();
        }
        try {
            connection.close();
            LOGGER.info("Transaction is closed");
        } catch (SQLException e) {
            LOGGER.error("JdbcDaoConnection close error", e);
        }
    }
}