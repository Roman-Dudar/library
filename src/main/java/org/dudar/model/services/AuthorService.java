package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.AuthorDao;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.entity.Author;
import org.dudar.model.entity.BookDescription;

import java.util.List;

public class AuthorService {

    private static final Logger LOGGER = LogManager.getLogger(AuthorService.class);

    private static AuthorService instance;

    private DaoFactory daoFactory;

    public static AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService(DaoFactory.getDaoFactory());
        }
        return instance;
    }

    private AuthorService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void createAuthor(Author author) {
        LOGGER.info("Create author: " + author.getSurname() + " " + author.getName());
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            authorDao.create(author);
        }
    }

    //todo-Dmitry can u delete this?
    public List<Author> getAuthorBySurnameBeginning(String surnameBeginning) {
        LOGGER.info("Get authors by surname beginning: " + surnameBeginning);
        List<Author> authors;
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            authors = authorDao.getBySurnameBeginning(surnameBeginning);
        }
        return authors;
    }

    public List<Author> getAuthorsByBookDescriptionId(Long bookId) {
        LOGGER.info("Get authors by book description ID: " + bookId);
        List<Author> authors;
        try (AuthorDao authorDao = daoFactory.createAuthorDao()){
            authors = authorDao.getByBookDescriptionId(bookId);
        }
        return authors;
    }

    public List<Author> getByBookDescription(BookDescription bookDescription) {
        return getAuthorsByBookDescriptionId(bookDescription.getId());
    }
}
