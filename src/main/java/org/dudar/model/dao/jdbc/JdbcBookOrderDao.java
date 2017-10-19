package org.dudar.model.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.exception.DatabaseException;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.dao.BookOrderDao;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.entity.BookOrder;
import org.dudar.model.entity.User;
import org.dudar.model.entity.enums.Availability;
import org.dudar.model.entity.enums.Status;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JdbcBookOrderDao implements BookOrderDao{

    private static final Logger LOGGER = LogManager.getLogger(JdbcBookOrderDao.class);

    Connection connection;

    public JdbcBookOrderDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void create(BookOrder bookOrder) {
        String createQuery = "INSERT INTO book_order(book_instance_id, user_id, creation_date) VALUES (?, ?, ?)";
        try (PreparedStatement query = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            query.setLong(1, bookOrder.getBookInstance().getId());
            query.setLong(2, bookOrder.getUser().getId());
            query.setDate(3, bookOrder.getCreationDate());
            query.executeUpdate();
            ResultSet keys = query.getGeneratedKeys();
            if (keys.next()) {
                bookOrder.setId(keys.getLong(1));
            }
        } catch (SQLException e){
            LOGGER.error("Create SQL exception", e);
            throw new DatabaseException(e);
        }
    }

    public void update(BookOrder bookOrder) {
        String updateQuery = "UPDATE book_order SET creation_date = ?, pickup_date = ?, "
                           + "return_date = ?, actual_return_date = ? WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(updateQuery)){
            query.setDate(1, bookOrder.getCreationDate());
            query.setDate(2, bookOrder.getPickupDate());
            query.setDate(3, bookOrder.getReturnDate());
            query.setDate(4, bookOrder.getActualReturnDate());
            query.setLong(5, bookOrder.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Update SQL exception", e);
            throw new DatabaseException(e);
        }
    }

    public void delete(BookOrder bookOrder) {
        String deleteQuery = "DELETE FROM book_order WHERE id = ?";
        try(PreparedStatement query = connection.prepareStatement(deleteQuery)){
            query.setLong(1, bookOrder.getId());
            query.executeUpdate();
        } catch(SQLException e) {
            LOGGER.error("Delete SQL exception", e);
            throw new DatabaseException(e);
        }
    }

    public Optional<BookOrder> getById(Long orderId) {
        String getByIdQuery = "SELECT * FROM book_order JOIN user on book_order.user_id = user.id JOIN book_instance "
                            + "ON book_order.book_instance_id = book_instance.id JOIN book_description ON "
                            + "book_instance.book_description_id = book_description.id WHERE book_order.id = ?";
        Optional<BookOrder> bookOrder = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByIdQuery)) {
            query.setLong(1, orderId);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                bookOrder = Optional.of(parseResultSetWithAgregation(resultSet));
            }
        } catch(SQLException e) {
            LOGGER.error("Get order by id: " + orderId, e);
            throw new DatabaseException(e);
        }
        return bookOrder;
    }

    public List<BookOrder> getUnreturnedOrdersOfUser(Long userId){
        String getUnreturnedOrdersOfUserQuery = "SELECT book_order.id, book_instance_id, user_id, "
            + "creation_date, pickup_date, return_date, actual_return_date FROM book_order "
            + "JOIN user ON user.id = book_order.user_id WHERE user_id = ? AND actual_return_date is NULL;";

        List<BookOrder> orders = new LinkedList<>();
        try (PreparedStatement query = connection.prepareStatement(getUnreturnedOrdersOfUserQuery)){
            query.setLong(1, userId);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()){
                orders.add(parseResultSet(resultSet));
            }
        } catch(SQLException e) {
            LOGGER.error("Get unreturned orders of user exception: " + userId, e);
            throw new DatabaseException(e);
        }
        return orders;
    }

    public List<BookOrder> getByBookDescription(Long bookDescriptionId) {
        String getByBookDescriptionQuery = "SELECT book_order.id, book_instance_id, user_id, "
                + "creation_date, pickup_date, return_date, actual_return_date, FROM book_order "
                + "JOIN book_instance ON book_order.book_instance_id = book_instance.id "
                + "JOIN book_description ON book_instance.book_description_id = book_description.id "
                + "WHERE book_description.id = ?";

        List<BookOrder> orders = new LinkedList<>();
        try (PreparedStatement query = connection.prepareStatement(getByBookDescriptionQuery)){
            query.setLong(1, bookDescriptionId);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()){
                orders.add(parseResultSet(resultSet));
            }
        } catch(SQLException e) {
            LOGGER.error("Get orders by book description: " + bookDescriptionId, e);
            throw new DatabaseException(e);
        }
        return orders;
    }

    public List<BookOrder> getAllByUser(Long userId) {
        String getUnreturnedOrdersOfUserQuery = "SELECT book_order.id, book_instance_id, user_id, "
                + "creation_date, pickup_date, return_date, actual_return_date FROM book_order "
                + "JOIN user ON user.id = book_order.user_id WHERE user_id = ?";

        List<BookOrder> orders = new LinkedList<>();
        try (PreparedStatement query = connection.prepareStatement(getUnreturnedOrdersOfUserQuery)){
            query.setLong(1, userId);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()){
                orders.add(parseResultSet(resultSet));
            }
        } catch(SQLException e) {
            LOGGER.error("Get all orders of user exception: " + userId, e);
            throw new DatabaseException(e);
        }
        return orders;
    }

    static BookOrder parseResultSet(ResultSet resultSet) throws SQLException {
        return new BookOrder.Builder().setId(resultSet.getLong("id"))
            .setBookInstance(new BookInstance(resultSet.getLong("book_instance_id")))
            .setUser(new User(resultSet.getLong("user_id")))
            .setCreationDate(resultSet.getDate("creation_date"))
            .setPickupDate(resultSet.getDate("pickup_date"))
            .setReturnDate(resultSet.getDate("return_date"))
            .setActualReturnDate(resultSet.getDate("actual_return_date"))
            .build();
    }

    static BookOrder parseResultSetWithAgregation(ResultSet resultSet) throws SQLException {
        BookOrder bookOrder = new BookOrder.Builder().setId(resultSet.getLong("id"))
                .setBookInstance(JdbcBookInstanceDao.parseResultSet(resultSet))
                .setUser(JdbcUserDao.parseResultSet(resultSet))
                .setCreationDate(resultSet.getDate("creation_date"))
                .setPickupDate(resultSet.getDate("pickup_date"))
                .setReturnDate(resultSet.getDate("return_date"))
                .setActualReturnDate(resultSet.getDate("actual_return_date"))
                .build();
        bookOrder.getBookInstance().setBookDescription(JdbcBookDescriptionDao.parseResultSet(resultSet));
        return bookOrder;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcBookOrderDao Connection can't be closed", e);
        }
    }
}
