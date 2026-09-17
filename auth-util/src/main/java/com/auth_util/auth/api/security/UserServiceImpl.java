package com.auth_util.auth.api.security;

import com.auth_util.auth.api.exceptions.AuthDataAccessException;
import com.auth_util.auth.api.exceptions.UserNotFoundException;
import com.auth_util.auth.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserDao userDao;
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        try {
            User user = userDao.findByUsernameOrEmail(username, email);
            if (user == null) {
                throw new UserNotFoundException("User not found : " + username);
            }
            return user;

        } catch (DataAccessException ex) {

            LOGGER.error("Failed to retrieve user from username: {}", username ,ex);
            throw new AuthDataAccessException("Failed to retrieve user", ex);
        }


    }
}
