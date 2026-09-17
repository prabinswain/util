package com.auth_util.auth.api.security.mapper;

import com.auth_util.auth.api.model.UserDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRowMapper implements RowMapper<UserDetail> {
    @Override
    public UserDetail mapRow(ResultSet row, int rowNum) throws SQLException {
        UserDetail user = new UserDetail();

        user.setFirstName(row.getString("first_name"));
        user.setLastName(row.getString("last_name"));
        user.setLastLogin(row.getString("last_login"));
        user.setRole(row.getString("role_name"));
        user.setLanPreference(row.getString("lang_preference"));
        return user;

    }
}
