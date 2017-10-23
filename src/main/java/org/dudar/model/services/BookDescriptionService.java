package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.AuthorDao;
import org.dudar.model.dao.BookDescriptionDao;
import org.dudar.model.dao.DaoConnection;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.dao.jdbc.JdbcBookDescriptionDao;
import org.dudar.model.entity.BookDescription;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

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
    
    public void addBookDescription(BookDescription bookDescription) {
        LOGGER.info("Create BookDescription: " + bookDescription.getTitle());
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            BookDescriptionDao bookDescriptionDao = daoFactory.createBookDescriptionDao(connection);
            bookDescriptionDao.create(bookDescription);
            connection.commit();
        }
    }

    public List<BookDescription> getBookDescription(int pageNumber){
        LOGGER.info("Get book descriptions for page #" + pageNumber);
        int limit = DaoFactory.LIMIT;
        int offset = (pageNumber - 1) * limit;
        List<BookDescription> books;

        try (BookDescriptionDao bookDescriptionDao = daoFactory.createBookDescriptionDao()) {
            books = bookDescriptionDao.getBookDescription(limit, offset);
        }

        AuthorService as = AuthorService.getInstance();
        for (BookDescription book: books) {
            book.setAuthors(as.getByBookDescription(book));
        }
        return books;
    }

    public List<BookDescription> getBookDescriptionByTitle(String title) {
        List<BookDescription> books;

        try (BookDescriptionDao bookDescriptionDao = daoFactory.createBookDescriptionDao()) {
            books = bookDescriptionDao.getByTitle(title);
        }

        AuthorService authorService = AuthorService.getInstance();
        for (BookDescription book: books) {
            authorService.getAuthorsByBookDescriptionId(book.getId());
        }
        return books;
    }
    
}
