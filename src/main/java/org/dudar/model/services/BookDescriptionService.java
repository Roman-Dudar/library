package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.dao.DaoConnection;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.entity.BookDescription;

public class BookDescriptionService {

    private static final Logger LOGGER = LogManager.getLogger(BookDescriptionService.class);

    private static BookDescriptionService instance;

    private DaoFactory daoFactory;

    public static BookDescriptionService getInstance() {
        if (instance == null) {
            instance = new BookDescriptionService(DaoFactory.getDaoFactory());
        }
        return instance;
    }

    private BookDescriptionService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public void createBookDescription(BookDescription bookDescription) {
        LOGGER.info("Create BookDescription: " + bookDescription.getTitle());
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            BookDescriptionDao bookDescriptionDao = daoFactory.createBookDescriptionDao(connection);
            bookDescriptionDao.create(bookDescription);
        }
    }
    
}
