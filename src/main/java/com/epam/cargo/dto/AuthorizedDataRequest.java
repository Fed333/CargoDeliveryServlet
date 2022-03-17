package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object to assemble User's authorized data.<br>
 * Consists of login and password.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class AuthorizedDataRequest {

    /**
     * User's login.<br>
     * @since 1.0
     * */
    private String login;

    /**
     * User's password.<br>
     * Not hash, the dto transfers the input password from web form.<br>
     * @since 1.0
     * */
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
