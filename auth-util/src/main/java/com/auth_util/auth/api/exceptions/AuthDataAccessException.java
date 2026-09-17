package com.auth_util.auth.api.exceptions;

import org.springframework.dao.DataAccessException;

public class AuthDataAccessException extends RuntimeException {
    public AuthDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
