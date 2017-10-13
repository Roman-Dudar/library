package org.dudar.model.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.dudar.dto.ChangePasswordDto;
import org.dudar.dto.LoginDto;
import org.dudar.model.dao.DaoConnection;
import org.dudar.model.dao.DaoFactory;
import org.dudar.model.dao.UserDao;
import org.dudar.model.entity.User;
import org.dudar.utils.PasswordHasher;

import java.util.Optional;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private static UserService instance = new UserService(DaoFactory.getDaoFactory());

    public static UserService getInstance(){
        return instance;
    }

    private DaoFactory daoFactory;

    private UserService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void createUser(User user){
        LOGGER.info("Create user: " + user.getName() + " " + user.getSurname());
        user.setPassword(PasswordHasher.getInstance().hashPassword(user.getPassword()));
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        }
    }

    public boolean changePassword(ChangePasswordDto changePasswordDto) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            boolean changingPasswordResult = false;
            PasswordHasher hasher = PasswordHasher.getInstance();
            connection.begin();
            UserDao userDao = daoFactory.createUserDao(connection);
            User user = userDao.getById(changePasswordDto.getUserId()).get();

            if (hasher.verifyPassword(user.getPassword(), changePasswordDto.getOldPassword())) {
                user.setPassword(hasher.hashPassword(user.getPassword()));
                userDao.update(user);
                connection.commit();
                changingPasswordResult = true;
            }
            return changingPasswordResult;
        }
    }

    public Optional<User> getUserById(Long readerId) {
        LOGGER.info("Get user by id");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getById(readerId);
        }
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        LOGGER.info("Get user by id");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getByPhoneNumber(phoneNumber);
        }
    }

    public User signInUser(LoginDto loginDto){
        LOGGER.info("User sign in try: " + loginDto.getPhoneNumber());
        Optional<User> userOptional;
        try (UserDao userDao = daoFactory.createUserDao()) {
            userOptional = userDao.getByPhoneNumber(loginDto.getPhoneNumber());
        }
        boolean verified = PasswordHasher.getInstance().
                verifyPassword(userOptional.get().getPassword(), loginDto.getPassword());
        if (userOptional.isPresent() && verified) {
            return userOptional.get();
        }
        return null;
    }
}