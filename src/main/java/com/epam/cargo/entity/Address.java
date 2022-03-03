package com.epam.cargo.entity;

import java.util.Objects;

/**
 * Class for representing address
 * Used as directly user's address or address of delivering
 * */
public class Address {

    private Long id;

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

    private City city;

    private String street;

    private String houseNumber;

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
}