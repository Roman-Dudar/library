package org.dudar.model.dao;

import org.dudar.model.entity.Author;

import java.util.List;

public interface AuthorDao extends GenericDao<Author>{

    List<Author> getBySurnameBeginning(String surnamePart);

    List<Author> getByBookDescriptionId(Long bookId);
}
