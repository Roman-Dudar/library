package org.dudar.model.dao.jdbc;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.dao.BookInstanceDao;
import org.dudar.model.entity.BookOrder;
import org.dudar.model.entity.enums.Status;

public class JdbcBookInstanceDao implements BookInstanceDao {

    private static final Logger LOGGER = LogManager.getLogger(JdbcBookInstanceDao.class);

    private Connection connection;

    public JdbcBookInstanceDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void create(BookInstance bookInstance) {
        String createQuery = "INSERT INTO book_instance (id, book_description_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement query = connection.prepareStatement(createQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            query.setLong(1, bookInstance.getId());
            query.setLong(2, bookInstance.getBookDescription().getId());
            query.setString(3, bookInstance.getStatus().name());
            query.executeUpdate();

            ResultSet keys = query.getGeneratedKeys();
            if (keys.next()) {
                bookInstance.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao create SQL exception", e);
        }
    }

    public void update(BookInstance bookInstance) {
        String updateQuery = "UPDATE book_instance SET book_description_id = ?, status=? WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(updateQuery)) {
            query.setLong(1, bookInstance.getBookDescription().getId());
            query.setString(2, bookInstance.getStatus().name());
            query.setLong(3, bookInstance.getId());
            query.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao update SQL exception: " + bookInstance.getId(), e);
        }
    }

    public void delete(BookInstance bookInstance) {
        String deleteQuery = "DELETE FROM book_instance WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(deleteQuery)) {
            query.setLong(1, bookInstance.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao delete SQL exception: " + bookInstance.getId(), e);
        }
    }

    public Optional<BookInstance> getById(Long id) {
        String getByIdQuery = "SELECT id, book_description_id, status FROM book_instance WHERE id = ?";
        Optional<BookInstance> bookInstance = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByIdQuery)) {
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                bookInstance = Optional.of(parseResultSer(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao get by ID SQL exception: " + id, e);
        }
        return bookInstance;
    }

    public List<BookInstance> getByBookDescription(BookDescription bookDescription) {
        String getByBookDescriptionQuery = "SELECT id, status FROM book_instance WHERE id = ?";
        List<BookInstance> instances = new LinkedList<>();
        try (PreparedStatement query = connection.prepareStatement(getByBookDescriptionQuery)) {
            query.setLong(1, bookDescription.getId());
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                instances.add(parseResultSer(resultSet, bookDescription));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao getByBookDescription SQL exception: " + bookDescription.getId(), e);
        }
        return instances;
    }

    public void getByBookOrder(BookOrder bookOrder){
        String getByBookOrderQuery = "SELECT id, book_description_id, status FROM book_instance JOIN book_order ON "
                                   + "book_instance.id = book_order.book_instance_id WHERE book_order.id = ?";
        try (PreparedStatement query = connection.prepareStatement(getByBookOrderQuery)) {
            query.setLong(1, bookOrder.getId());
            ResultSet resultSet = query.executeQuery();
            bookOrder.getBookInstance().setStatus(Status.valueOf
                    (resultSet.getString("status").toUpperCase()));
            bookOrder.getBookInstance().setBookDescription(
                    new BookDescription(resultSet.getLong("book_description_id")));
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao get by book order SQL exception: " + bookOrder.getId(), e);
        }
    }

    private BookInstance parseResultSer(ResultSet resultSet) throws SQLException {
        return new BookInstance.Builder().setId(resultSet.getLong("id"))
            .setBookDescription(new BookDescription(resultSet.getLong("book_description_id")))
            .setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()))
            .build();
    }

    private BookInstance parseResultSer(ResultSet resultSet, BookDescription bookDescription) throws SQLException {
        return new BookInstance.Builder().setId(resultSet.getLong("id"))
            .setBookDescription(bookDescription)
            .setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()))
            .build();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao Connection can't be closed", e);
        }
    }
}