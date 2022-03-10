package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object for assembling filter for DirectionDelivery objects on directions page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DirectionDeliveryFilterRequest {

    private String senderCityName;
    private String receiverCityName;

    public String getSenderCityName() {
        return senderCityName;
    }

    public void setSenderCityName(String senderCityName) {
        this.senderCityName = senderCityName;
    }

    public String getReceiverCityName() {
        return receiverCityName;
    }

    public void setReceiverCityName(String receiverCityName) {
        this.receiverCityName = receiverCityName;
    }
}
