package com.auth_util.auth.api.security;

import com.auth_util.auth.api.model.User;

public interface UserDao {
    public User findByUsernameOrEmail(String username, String email);
}
