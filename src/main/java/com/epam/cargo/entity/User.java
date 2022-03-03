package com.epam.cargo.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Represents an end user
 * */
public class User {

    private Long id;

    private String name;

    private String surname;

    private String login;

    private String password;

    private String phone;

    private String email;

    private BigDecimal cash;

    private Address address;

    private Set<Role> roles;

    private List<DeliveryApplication> applications;

    private List<DeliveryReceipt> receipts;

    public User() { }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(Long id, String name, String surname, String login, String password, String phone, String email, BigDecimal cash, Address address, Set<Role> roles, List<DeliveryApplication> applications, List<DeliveryReceipt> receipts) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.cash = cash;
        this.address = address;
        this.roles = roles;
        this.applications = applications;
        this.receipts = receipts;
    }

    public boolean isManager(){
        return getRoles().contains(Role.MANAGER);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<DeliveryApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<DeliveryApplication> applications) {
        this.applications = applications;
    }

    public List<DeliveryReceipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<DeliveryReceipt> receipts) {
        this.receipts = receipts;
    }

}
