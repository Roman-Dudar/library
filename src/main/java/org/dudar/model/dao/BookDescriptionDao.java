package org.dudar.model.dao;

import org.dudar.model.entity.BookDescription;

import java.util.List;

public interface BookDescriptionDao extends GenericDao<BookDescription> {

    List<BookDescription> getBookDescription(int limit, int offset);

}
