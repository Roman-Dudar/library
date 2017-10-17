package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.dao.BookInstanceDao;
import org.dudar.model.dao.DaoConnection;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.dao.jdbc.JdbcBookInstanceDao;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.entity.enums.Status;

import java.util.List;
import java.util.Optional;

public class BookInstanceService {

    private static final Logger LOGGER = LogManager.getLogger(BookInstanceService.class);

    private static BookInstanceService instance;

    private DaoFactory daoFactory;

    public static BookInstanceService getInstance() {
        if (instance == null) {
            instance = new BookInstanceService(DaoFactory.getDaoFactory());
        }
        return instance;
    }

    private BookInstanceService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void createBookInstance(BookInstance bookInstance) {
        LOGGER.info("Create book instance for " + bookInstance.getBookDescription().getId());
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            bookInstanceDao.create(bookInstance);
        }
    }

    public void setStatus(BookInstance bookInstance, Status status) {
        LOGGER.info("Set book instance #" + bookInstance.getId() + " status: " + status.name().toLowerCase());
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            bookInstance.setStatus(status);
            bookInstanceDao.update(bookInstance);
        }
    }

    public void deleteBookInstance(BookInstance bookInstance) {
        LOGGER.info("Delete book instance #" + bookInstance.getId());
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            bookInstanceDao.delete(bookInstance);
        }
    }

    public List<BookInstance> getByBookDescription(BookDescription bookDescription) {
        LOGGER.info("Get book instances by book description #" + bookDescription.getId());
        List<BookInstance> instances;
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            instances = bookInstanceDao.getByBookDescription(bookDescription);
        }
        return instances;
    }

    public List<BookInstance> getByBookDescriptionId(Long bookDescriptionId) {
        LOGGER.info("Get book instances by book description id: " + bookDescriptionId);
        List<BookInstance> instances;
        BookDescription bookDescription = daoFactory.createBookDescriptionDao().getById(bookDescriptionId).get();
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            instances = bookInstanceDao.getByBookDescription(bookDescription);
        }
        return instances;
    }


    public Optional<BookInstance> getAvailableByBookDescriptionId(Long bookDescriptionId) {
        LOGGER.info("Get available book instance by book description id: " + bookDescriptionId);
        Optional<BookInstance> bookInstance;
        try (BookInstanceDao bookInstanceDao = daoFactory.createBookInstanceDao()) {
            bookInstance = bookInstanceDao.getAvailableByBookDescriptionId(bookDescriptionId);
        }
        return bookInstance;
    }

}
