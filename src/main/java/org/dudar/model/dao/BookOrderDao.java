package org.dudar.model.dao;

import org.dudar.model.entity.BookOrder;
import org.dudar.model.entity.User;

import java.util.List;

public interface BookOrderDao extends GenericDao<BookOrder> {

    List<BookOrder> getUnreturnedOrdersOfUser(Long userId);

    List<BookOrder> getByBookDescription(Long bookDescriptionId);

    List<BookOrder> getAllByUser(Long userId);
}
