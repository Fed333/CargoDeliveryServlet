package com.epam.cargo.dto;

import org.fed333.servletboot.annotation.DTO;

/**
 * Data Transfer Object to assemble data of User.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class UserRequest {

    private String name;

    private String surname;

    private String login;

    private String password;

    private String duplicatePassword;

    private String phone;
    private String email;

    private AddressRequest address;

    public UserRequest() {
    }

    public UserRequest(String name, String surname, String login, String password, String duplicatePassword, String phone, String email, AddressRequest address) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.duplicatePassword = duplicatePassword;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

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

    public String getDuplicatePassword() {
        return duplicatePassword;
    }

    public void setDuplicatePassword(String duplicatePassword) {
        this.duplicatePassword = duplicatePassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}
