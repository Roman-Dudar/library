package org.dudar.model.dao;

import org.dudar.model.entity.BookDescription;
import org.dudar.model.entity.BookInstance;
import org.dudar.model.entity.BookOrder;

import java.util.List;

public interface BookInstanceDao extends GenericDao<BookInstance> {

    List<BookInstance> getByBookDescription(BookDescription bookDescription);

    void getByBookOrder(BookOrder bookOrder);

}
