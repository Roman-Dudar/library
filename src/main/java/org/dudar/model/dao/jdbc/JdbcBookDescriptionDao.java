package org.dudar.model.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.entity.BookDescription;

import java.sql.*;
import java.util.Optional;

public class JdbcBookDescriptionDao implements BookDescriptionDao {
    //todo-Dmitry create JdbcBookDescriptionDao

    private static final Logger LOGGER = LogManager.getLogger(JdbcBookOrderDao.class);

    private Connection connection;

    public JdbcBookDescriptionDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void create(BookDescription bookDescription) {
        String createQuery = "INSERT INTO book_description(isbn, title, publisher, genre, availability) "
                           + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement query = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, bookDescription.getIsbn());
            query.setString(2, bookDescription.getTitle());
            query.setString(3, bookDescription.getPublisher());
            query.setString(4, bookDescription.getGenre());
            query.setString(5, bookDescription.getAvailability().name().toLowerCase());
            query.executeUpdate();
            ResultSet keys = query.getGeneratedKeys();
            if (keys.next()) {
                bookDescription.setId(keys.getLong(1));
            }
        } catch (SQLException e){
            LOGGER.error("JdbcBookDescriptionDao create SQL exception", e);
        }
    }

    public void update(BookDescription bookDescription) {
        String updateQuery = "UPDATE book_description SET isbn = ?, title = ?, publisher = ?, genre = ?, "
                           + "availability = ? WHERE  id = ?";
        try (PreparedStatement query = connection.prepareStatement(updateQuery)) {
            query.setString(1, bookDescription.getIsbn());
            query.setString(2, bookDescription.getTitle());
            query.setString(3, bookDescription.getPublisher());
            query.setString(4, bookDescription.getGenre());
            query.setString(5, bookDescription.getAvailability().name().toLowerCase());
            query.setLong(6, bookDescription.getId());
            query.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("JdbcBookDescriptionDao update SQL exception", e);
        }
    }

    public void delete(BookDescription bookDescription) {
        String deleteQuery = "DELETE FROM book_description WHERE id = ?";

        try (PreparedStatement query = connection.prepareStatement(deleteQuery)) {
            query.setLong(1, bookDescription.getId());
            query.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("JdbcBookDescription delete SQL exception", e);
        }
    }

    public Optional<BookDescription> getById(Long key) {
        throw new UnsupportedOperationException();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookDescriptionDao Connection can't be closed", e);
        }
    }
}
