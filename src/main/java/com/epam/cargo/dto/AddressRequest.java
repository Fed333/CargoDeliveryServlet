package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object to assemble address.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class AddressRequest {

    private Long cityId;

    private String streetName;

    private String houseNumber;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}