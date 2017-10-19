package org.dudar.model.dao.jdbc;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.exception.DatabaseException;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.enums.Availability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBookDescriptionDao implements BookDescriptionDao {

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
            throw new DatabaseException(e);
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
            throw new DatabaseException(e);
        }
    }

    public void delete(BookDescription bookDescription) {
        String deleteQuery = "DELETE FROM book_description WHERE id = ?";

        try (PreparedStatement query = connection.prepareStatement(deleteQuery)) {
            query.setLong(1, bookDescription.getId());
            query.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("JdbcBookDescription delete SQL exception", e);
            throw new DatabaseException(e);
        }
    }

    public Optional<BookDescription> getById(Long bookDescriptionId) {
        String getByIdQuery = "SELECT * FROM book_description WHERE id = ?";
        Optional<BookDescription> bookDescription = null;
        try (PreparedStatement query = connection.prepareStatement(getByIdQuery)){
            query.setLong(1, bookDescriptionId);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                bookDescription = Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Get book description by id " + bookDescriptionId, e);
            throw new DatabaseException(e);
        }
        return bookDescription;
    }

    public List<BookDescription> getBookDescription(int limit, int offset) {
        //SQL_CALC_FOUND_ROWS
        String getBookDescriptionsPaginationQuery = "SELECT * FROM book_description LIMIT ? OFFSET ?";
        List<BookDescription> bookDescriptions = new ArrayList<>(limit);
        try (PreparedStatement query = connection.prepareStatement(getBookDescriptionsPaginationQuery)) {
            query.setInt(1, limit);
            query.setInt(2, offset);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                bookDescriptions.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Get book description pagination: limit = " + limit + ", offset = " + offset, e);
            throw new DatabaseException(e);
        }
        return bookDescriptions;
    }

    protected static BookDescription parseResultSet(ResultSet resultSet) throws SQLException {
        return new BookDescription.Builder().setId(resultSet.getLong("book_description.id"))
                .setIsbn(resultSet.getString("isbn"))
                .setTitle(resultSet.getString("title"))
                .setAvailability(Availability.valueOf(resultSet.getString("availability").toUpperCase()))
                .setPublisher(resultSet.getString("publisher"))
                .build();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookDescriptionDao Connection can't be closed", e);
        }
    }
}
