package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

import java.time.LocalDate;

/**
 * Data Transfer Object to assemble application data from making delivery application page<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveryApplicationRequest {

    private AddressRequest senderAddress;

    private AddressRequest receiverAddress;

    private DeliveredBaggageRequest deliveredBaggageRequest;

    private LocalDate sendingDate;

    private LocalDate receivingDate;

    public AddressRequest getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(AddressRequest senderAddress) {
        this.senderAddress = senderAddress;
    }

    public AddressRequest getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(AddressRequest receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public DeliveredBaggageRequest getDeliveredBaggageRequest() {
        return deliveredBaggageRequest;
    }

    public void setDeliveredBaggageRequest(DeliveredBaggageRequest deliveredBaggageRequest) {
        this.deliveredBaggageRequest = deliveredBaggageRequest;
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
}