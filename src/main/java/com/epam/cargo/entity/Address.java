package com.epam.cargo.entity;

import java.util.Objects;

/**
 * POJO class for representing address.
 * Used as directly user's address or address of delivering.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class Address implements Entity<Long>{

    private Long id;

    private City city;

    private String street;

    private String houseNumber;

    public Address() {
    }

    public Address(City city, String street, String houseNumber) {
        this(null, city, street, houseNumber);
    }

    public Address(Long id, City city, String street, String houseNumber) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, houseNumber);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}