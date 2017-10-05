package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dudar.model.dao.BookOrderDao;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.BookOrder;
import org.dudar.model.entity.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BookOrderService {

    private static final Logger LOGGER = LogManager.getLogger(BookOrderService.class);

    private DaoFactory daoFactory;

    public BookOrderService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void createOrder(BookOrder bookOrder) {
        LOGGER.info("Create book order for " + bookOrder.getBookInstance().getId());
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            bookOrder.setCreationDate(Date.valueOf(LocalDate.now()));
            bookOrderDao.create(bookOrder);
        }
    }

    public void confirmPickUp(BookOrder bookOrder) {
        LOGGER.info("Confirm book pick-up (set pick-up and return date in book order) " + bookOrder.getId());
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            bookOrder.setPickupDate(Date.valueOf(LocalDate.now()));
            bookOrder.setReturnDate(Date.valueOf(LocalDate.now().plusMonths(1)));
            bookOrderDao.update(bookOrder);
        }
    }

    public void confirmReturn(BookOrder bookOrder) {
        LOGGER.info("Confirm book return (set actual return date in book order) " + bookOrder.getId());
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            bookOrder.setActualReturnDate(Date.valueOf(LocalDate.now()));
            bookOrderDao.update(bookOrder);
        }
    }

    public List<BookOrder> getUnreturnedOrdersOfUser(Long userId) {
        LOGGER.info("Get unreturned orders of user " + userId);
        List<BookOrder> resultList;
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            resultList = bookOrderDao.getUnreturnedOrdersOfUser(userId);
            //todo-Dmitry get book instances for resultList of orders;
        }
        return resultList;
    }

    public List<BookOrder> getUnreturnedOrdersOfUser(User user) {
        return getUnreturnedOrdersOfUser(user.getId());
    }

    public List<BookOrder> getAllOrdersOfUser(Long userId) {
        LOGGER.info("Get all orders of user " + userId);
        List<BookOrder> resultList;
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            resultList = bookOrderDao.getAllByUser(userId);
            //todo-Dmitry get book instances for resultList of orders;
        }
        return resultList;
    }

    public List<BookOrder> getAllOrdersOfUser(User user) {
        return getUnreturnedOrdersOfUser(user.getId());
    }

    public List<BookOrder> getByBookDescription(Long bookDescriptionId) {
        LOGGER.info("Get orders by book description " + bookDescriptionId);
        List<BookOrder> resultList;
        try (BookOrderDao bookOrderDao = daoFactory.createBookOrderDao()) {
            resultList = bookOrderDao.getByBookDescription(bookDescriptionId);
            //todo-Dmitry get book instances for resultList of orders;
        }
        return resultList;
    }

    public List<BookOrder> getByBookDescription(BookDescription bookDescription) {
        return getByBookDescription(bookDescription.getId());
    }

}