package com.epam.cargo.dto;

import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveryApplication;
import org.fed333.servletboot.annotation.DTO;

import java.time.LocalDate;

/**
 * Data Transfer Object to assemble filter for DeliveryApplication objects on delivery applications review page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveryApplicationsReviewFilterRequest {

    private DeliveryApplication.State applicationState;

    private Long cityFromId;
    private Long cityToId;

    private LocalDate sendingDate;

    private LocalDate receivingDate;

    private BaggageType baggageType;

    public DeliveryApplication.State getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(DeliveryApplication.State applicationState) {
        this.applicationState = applicationState;
    }

    public Long getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(Long cityFromId) {
        this.cityFromId = cityFromId;
    }

    public Long getCityToId() {
        return cityToId;
    }

    public void setCityToId(Long cityToId) {
        this.cityToId = cityToId;
    }

    public LocalDate getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(LocalDate sendingDate) {
        this.sendingDate = sendingDate;
    }

    public LocalDate getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(LocalDate receivingDate) {
        this.receivingDate = receivingDate;
    }

    public BaggageType getBaggageType() {
        return baggageType;
    }

    public void setBaggageType(BaggageType baggageType) {
        this.baggageType = baggageType;
    }
}
