package org.dudar.model.dao;

import org.dudar.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> getByPhoneNumber(String phoneNumber);

}
