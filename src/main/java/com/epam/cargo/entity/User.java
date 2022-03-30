package com.epam.cargo.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an end user.
 * Implements Entity interface.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class User implements Entity<Long> {

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

    public User(Long id){
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname)
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(phone, user.phone)
                && Objects.equals(email, user.email)
                && Objects.equals(cash, user.cash)
                && Objects.equals(address, user.address)
                && Objects.equals(roles, user.roles)
                && Objects.equals(applications, user.applications)
                && Objects.equals(receipts, user.receipts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, phone, email, cash, address, roles, applications, receipts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", cash=" + cash +
                ", address=" + address +
                ", roles=" + roles +
                ", applications=" + applications +
                ", receipts=" + receipts +
                '}';
    }
}
