package org.dudar.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import org.dudar.model.dao.AuthorDao;
import org.dudar.model.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JdbcAuthorDao implements AuthorDao {

    private static final Logger LOGGER = LogManager.getLogger(JdbcAuthorDao.class);

    private Connection connection;

    public JdbcAuthorDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void create(Author author) {
        String createQuery = "INSERT INTO author (name, patronymic, surname) VALUES (?, ?, ?)";
        try (PreparedStatement query = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, author.getName());
            query.setString(2, author.getPatronymic());
            query.setString(3, author.getSurname());
            query.executeUpdate();

            ResultSet keys = query.getGeneratedKeys();
            if (keys.next()) {
                author.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("JDBCAuthorDAO create SQL exception", e);
        }
    }

    public void update(Author author) {
        String updateQuery = "UPDATE author SET name=?, patronymic=?, surname=? WHERE id=?";
        try (PreparedStatement query = connection.prepareStatement(updateQuery)) {
            query.setString(1, author.getName());
            query.setString(2, author.getPatronymic());
            query.setString(3, author.getSurname());
            query.setLong(4, author.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("JDBCAuthorDAO update SQL exception: " + author.getId(), e);
        }
    }

    public void delete(Author author) {
        String deleteAuthorQuery = "DELETE FROM author WHERE id=?";
        try (PreparedStatement query = connection.prepareStatement(deleteAuthorQuery)) {
            query.setLong(1, author.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("JdbcAuthorDao delete SQL exception: " + author.getId(), e);
        }
    }

    public Optional<Author> getById(Long id) {
        String getByIdQuery = "SELECT id, name, patronymic, surname FROM author WHERE id=?";
        Optional<Author> author = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(getByIdQuery)) {
            query.setLong(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                author = Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcAuthorDao get by ID SQL exception: " + id, e);
        }
        return author;
    }

    public List<Author> getBySurnameBeginning(String surnamePart) {
        String getBySurnamePartQuery = "SELECT id, name, patronymic, surname FROM author "
                                     + "WHERE surname like ? ORDER BY name";
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement query = connection.prepareStatement(getBySurnamePartQuery)) {
            query.setString(1, surnamePart + "%");
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                authors.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcAuthorDao get by surname beginning SQL exception: " + surnamePart, e);
        }
        return authors;
    }

    public List<Author> getByBookDescriptionId(Long bookId) {
        String getByBookDescriptionIdQuery = "SELECT id, name, patronymic, surname FROM author "
            + "JOIN m2m_book_author ON m2m_book_author.author_id = author.id "
            + "WHERE m2m_book_author.book_description_id = ?";
        List<Author> authors = new LinkedList<>();
        try (PreparedStatement query = connection.prepareStatement(getByBookDescriptionIdQuery)) {
            query.setLong(1, bookId);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                authors.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("JdbcAuthorDao get by book description id SQL exception", e);
        }
        return authors;
    }

    private Author parseResultSet(ResultSet resultSet) throws SQLException {
        return new Author.Builder().setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setPatronymic(resultSet.getString("patronymic"))
                .setSurname(resultSet.getString("surname"))
                .build();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("JdbcAuthorDao Connection can't be closed", e);
        }
    }
}
