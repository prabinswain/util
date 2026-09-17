package com.auth_util.auth.api.security;

import com.auth_util.auth.api.model.User;
import com.auth_util.auth.api.security.mapper.UserRowMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    private static final String GET_USER_GROUP =
            """
                    SELECT USER_GROUP FROM prabin.role_group rg JOIN prabin.user_role ur ON rg.role_name = ur.role_name JOIN prabin.[user] u ON u.user_id= ur.user_id where u.user_id =?
                    """;
    private JdbcTemplate jdbcTemplate;


    @Override
    public User findByUsernameOrEmail(String user_id, String email_id) throws DataAccessException {

        long entryTime = System.currentTimeMillis();
        LOGGER.debug("Finding user by username={} or email={}", user_id, email_id);

        String userQuery = """
                   SELECT EMAIL_ID, USER_ID, PASSWORD, FIRST_NAME, LAST_NAME, CREATED_ON,
                   CREATED_BY, UPDATED_ON, UPDATED_BY, FAILED_ATTEMPTS FROM [USER] WHERE 
                   (USER_ID = ? OR EMAIL_ID = ?) AND STATUS = 'A';
                """;

        RowMapper<User> rows = new UserRowMapper();
        User user = (User) jdbcTemplate.query(userQuery, rows, user_id, email_id);


        String roleQuery = """
                SELECT ROLE_NAME FROM [USER_ROLE] WHERE USER_ID = ?;
                """;

        List<String> roles = jdbcTemplate.query(roleQuery,
                (resultSet, rowNum) -> resultSet.getString("ROLE_NAME"),
                user.getUser_id());

        List<GrantedAuthority> authorities = roles.stream()
                .map(role ->
                        (GrantedAuthority) new SimpleGrantedAuthority(role))
                .toList();
        user.setAuthorities(authorities);
        user.setUserGroups(getUserGroups(user.getUser_id()));

        long execuionTime = System.currentTimeMillis() - entryTime;

        LOGGER.info("findByUsernameOrEmail execuionTime = {} MS", execuionTime);

        return user;


    }

    public Set<String> getUserGroups(String userId) {

        if (userId == null || userId.isBlank()) {
            return Collections.emptySet();
        }

        Set<String> userGroups = null;
        try {
            userGroups = jdbcTemplate.query(GET_USER_GROUP
                    , new Object[]{userId},
                    new ResultSetExtractor<Set<String>>() {
                        @Override
                        public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            Set<String> setRet = new HashSet<String>();
                            while (rs.next()) {
                                setRet.add(rs.getString("user_group"));
                            }
                            return setRet;
                        }
                    }

            );
        } catch (Exception ex) {
            userGroups.add("");
            LOGGER.info("getUserGroup query failour. Check for issue {}", ex.getMessage());
        }
        if (userGroups.isEmpty()){
            userGroups.add("");
        }
        LOGGER.info("getUserGroups {}", userGroups.toArray(new String[0])[0]);

        return userGroups;
    }
}
