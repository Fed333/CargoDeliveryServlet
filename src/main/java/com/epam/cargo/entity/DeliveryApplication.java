package com.epam.cargo.entity;

import java.time.LocalDate;

/**
 * POJO class which represents delivery application.
 * Used for providing user form of making delivery requests.
 * Implements Entity interface.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class DeliveryApplication implements Entity<Long> {

    private Long id;

    private User customer;

    private Long userId;
    
    private Address senderAddress;

    private Address receiverAddress;

    private DeliveredBaggage deliveredBaggage;

    private LocalDate sendingDate;

    private LocalDate receivingDate;

    private State state;

    private Double price;

    public enum State{
        SUBMITTED, CONFIRMED, COMPLETED, CANCELED, REJECTED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(Address senderAddress) {
        this.senderAddress = senderAddress;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(Address receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public DeliveredBaggage getDeliveredBaggage() {
        return deliveredBaggage;
    }

    public void setDeliveredBaggage(DeliveredBaggage deliveredBaggage) {
        this.deliveredBaggage = deliveredBaggage;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
