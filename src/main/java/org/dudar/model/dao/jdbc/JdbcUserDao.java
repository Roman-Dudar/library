package org.dudar.model.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.model.dao.UserDao;
import org.dudar.model.entity.User;
import org.dudar.model.entity.enums.Role;

import java.sql.*;
import java.util.Optional;

public class JdbcUserDao implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

    private Connection connection;

    public JdbcUserDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void create(User user) {
        String createQuery = "INSERT INTO USER(name, surname, phone_number, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement query = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, user.getName());
            query.setString(2, user.getSurname());
            query.setString(3, user.getPhoneNumber());
            query.setString(4, user.getPassword());
            query.setString(5, user.getRole().name().toLowerCase());
            query.executeUpdate();

            ResultSet keys = query.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao create SQL exception", e);
        }
    }

    public void update(User user) {
        String updateUserQuery = "UPDATE user SET name = ?, surname = ?, phone_number = ?, "
                           + "password = ?, role = ? WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(updateUserQuery)) {
            query.setString(1, user.getName());
            query.setString(2, user.getSurname());
            query.setString(3, user.getPhoneNumber());
            query.setString(4, user.getPassword());
            query.setString(5, user.getRole().name().toLowerCase());
            query.setLong(6, user.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao update SQL exception: " + user.getId(), e);
        }
    }

    public void delete(User user) {
        String DELETE_USER = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement query = connection.prepareStatement(DELETE_USER)) {
            query.setLong(1, user.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao delete SQL exception: " + user.getId(), e);
        }
    }

    public Optional<User> getById(Long id){
        String getByPhoneNumber  = "SELECT * FROM user WHERE id = ?";
        Optional<User> user = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByPhoneNumber)) {
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            user = Optional.of(parseResultSet(resultSet));
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao get by phone number SQL exception: " + id, e);
        }
        return user;
    }

    public Optional<User> getByPhoneNumber(String phoneNumber) {
        String getByPhoneNumber  = "SELECT * FROM user WHERE phone_number = ?";
        Optional<User> user = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByPhoneNumber)) {
            query.setString(1, phoneNumber);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao get by phone number SQL exception: " + phoneNumber, e);
        }
        return user;
    }

    private User parseResultSet(ResultSet resultSet) throws SQLException {
        return new User.Builder().setId(resultSet.getLong("id"))
                                     .setName(resultSet.getString("name"))
                                     .setSurname(resultSet.getString("surname"))
                                     .setPhoneNumber(resultSet.getString("phone_number"))
                                     .setPassword(resultSet.getString("password"))
                                     .setRole(Role.valueOf(resultSet.getString("role").toUpperCase()))
                                     .build();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcUserDao Connection can't be closed", e);
        }
    }
}
