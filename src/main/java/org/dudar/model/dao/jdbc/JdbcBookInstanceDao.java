package org.dudar.model.dao.jdbc;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.exception.DatabaseException;
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
            LOGGER.error("Create SQL exception", e);
            throw new DatabaseException(e);
        }
    }

    public void update(BookInstance bookInstance) {
        String updateQuery = "UPDATE book_instance SET book_description_id = ?, status=? WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(updateQuery)) {
            query.setLong(1, bookInstance.getBookDescription().getId());
            query.setString(2, bookInstance.getStatus().name().toLowerCase());
            query.setLong(3, bookInstance.getId());
            query.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Update SQL exception: " + bookInstance.getId(), e);
            throw new DatabaseException(e);
        }
    }

    public void delete(BookInstance bookInstance) {
        String deleteQuery = "DELETE FROM book_instance WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(deleteQuery)) {
            query.setLong(1, bookInstance.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Delete SQL exception: " + bookInstance.getId(), e);
            throw new DatabaseException(e);
        }
    }

    public Optional<BookInstance> getById(Long id) {
        String getByIdQuery = "SELECT id, book_description_id, status FROM book_instance WHERE id = ?";
        Optional<BookInstance> bookInstance = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByIdQuery)) {
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                bookInstance = Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Get by ID SQL exception: " + id, e);
            throw new DatabaseException(e);
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
                instances.add(parseResultSet(resultSet, bookDescription));
            }
        } catch (SQLException e) {
            LOGGER.error("Get by book description SQL exception: " + bookDescription.getId(), e);
            throw new DatabaseException(e);
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
            LOGGER.error("Get by book order SQL exception: " + bookOrder.getId(), e);
            throw new DatabaseException(e);
        }
    }

    public Optional<BookInstance> getAvailableByBookDescriptionId(Long bookDescriptionId){
        String getAvailableQuery = "SELECT book_instance.id, book_instance.status, book_description.id, isbn, title, "
                                 + "publisher, genre, availability, author.id, name, patronymic, surname "
                                 + "FROM book_instance JOIN book_description ON "
                                 + "book_instance.book_description_id = book_description.id "
                                 + "JOIN m2m_book_author ON book_description.id = m2m_book_author.book_description_id "
                                 + "JOIN author ON m2m_book_author.author_id = author.id WHERE "
                                 + "book_instance.book_description_id = ? AND status = 'available' "
                                 + "ORDER BY book_instance.id";
        Optional<BookInstance> bookInstance = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getAvailableQuery)){
            query.setLong(1, bookDescriptionId);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                bookInstance = Optional.of(parseResultSetWithAgregation(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Get available book instance by book description ID SQL exception: " + bookDescriptionId, e);
            throw new DatabaseException(e);
        }
        return bookInstance;
    }

    static BookInstance parseResultSet(ResultSet resultSet) throws SQLException {
        return new BookInstance.Builder().setId(resultSet.getLong("id"))
            .setBookDescription(new BookDescription(resultSet.getLong("book_description_id")))
            .setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()))
            .build();
    }

    static BookInstance parseResultSet(ResultSet resultSet, BookDescription bookDescription) throws SQLException {
        return new BookInstance.Builder().setId(resultSet.getLong("id"))
            .setBookDescription(bookDescription)
            .setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()))
            .build();
    }

    static BookInstance parseResultSetWithAgregation(ResultSet resultSet) throws SQLException {
        BookInstance bookInstance = new BookInstance.Builder().setId(resultSet.getLong("book_instance.id"))
                .setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()))
                .setBookDescription(JdbcBookDescriptionDao.parseResultSet(resultSet)).build();
        do {
            if (!bookInstance.getId().equals(resultSet.getLong("book_instance.id"))) {break;}
            bookInstance.getBookDescription().addAuthor(JdbcAuthorDao.parseResultSet(resultSet));
        } while (resultSet.next());
        return bookInstance;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookInstanceDao Connection can't be closed", e);
        }
    }
}