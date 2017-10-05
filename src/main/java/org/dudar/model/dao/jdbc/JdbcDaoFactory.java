package org.dudar.model.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.exception.DatabaseException;
import org.dudar.model.dao.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory{

    private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);

    private DataSource dataSource;

    public JdbcDaoFactory() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/library");

        } catch (Exception e) {
            LOGGER.error("Can't load pool connection from Initial Context", e);
            throw new DatabaseException(e);
        }
    }

    public DaoConnection getConnection() {
        try {
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB connection to the data source", e);
            throw new DatabaseException(e);
        }
    }

    public UserDao createUserDao() {
        try {
            return new JdbcUserDao(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB Connection for JdbcUserDao creation", e);
            throw new DatabaseException(e);
        }
    }

    public UserDao createUserDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcUserDao(sqlConnection);
    }

    public BookDescriptionDao createBookDescriptionDao() {
        try {
            return new JdbcBookDescriptionDao(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB Connection for JdbcBookDao creation", e);
            throw new DatabaseException(e);
        }
    }

    public BookDescriptionDao createBookDescriptionDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcBookDescriptionDao(sqlConnection);
    }

    public AuthorDao createAuthorDao() {
        try {
            return new JdbcAuthorDao(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB Connection for JdbcAuthorDao creation", e);
            throw new DatabaseException(e);
        }
    }

    public AuthorDao createAuthorDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcAuthorDao(sqlConnection);
    }

    public BookInstanceDao createBookInstanceDao() {
        try {
            return new JdbcBookInstanceDao(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB Connection for JdbcBookInstancesDao creation", e);
            throw new DatabaseException(e);
        }
    }

    public BookInstanceDao createBookInstanceDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcBookInstanceDao(sqlConnection);
    }

    public BookOrderDao createBookOrderDao() {
        try {
            return new JdbcBookOrderDao(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Can't get DB Connection for JdbcBookOrderDao creation", e);
            throw new DatabaseException(e);
        }
    }

    public BookOrderDao createBookOrderDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcBookOrderDao(sqlConnection);
    }
}
