package com.auth_util.auth.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class User {

    private Integer user_db_id;

    private String email_id;

    private String user_id;

    private String password;

    private String first_name;
    private String last_name;
    private Date created_on;
    private String created_by;

    private Date updated_on;
    private String updated_by;

    private Integer failed_attempts;

    private Collection<GrantedAuthority> authorities;

    private Set<String> userGroups;


    private EmailVarification emailVarification;
    private Date lastPasswordResetDate;

    @JsonIgnore
    public EmailVarification getEmailVarification() {
        return emailVarification;
    }
}
