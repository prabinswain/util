package com.auth_util.auth.api.security.mapper;

import com.auth_util.auth.api.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
        User user = new User();

        user.setEmail_id(row.getString("EMAIL_ID"));
        user.setUser_id(row.getString("USER_ID"));
        user.setFirst_name(row.getString("FIRST_NAME"));
        user.setLast_name(row.getString("PASSWORD"));
        user.setFailed_attempts(row.getInt("FAILED_ATTEMPTS"));
        user.setCreated_on(row.getDate("CREATED_ON"));
        user.setCreated_by(row.getString("CREATED_BY"));
        user.setUpdated_on(row.getDate("UPDATED_ON"));
        user.setUpdated_by(row.getString("UPDATED_BY"));

        return user;

    }
}
