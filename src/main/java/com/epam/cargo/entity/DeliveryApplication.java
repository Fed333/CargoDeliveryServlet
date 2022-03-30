package com.epam.cargo.entity;

import java.time.LocalDate;
import java.util.Objects;

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

    private Address senderAddress;

    private Address receiverAddress;

    private DeliveredBaggage deliveredBaggage;

    private LocalDate sendingDate;

    private LocalDate receivingDate;

    private State state;

    private Double price;

    public DeliveryApplication() {
    }

    public DeliveryApplication(Long id) {
        this.id = id;
    }

    public DeliveryApplication(Long id, User customer, Address senderAddress, Address receiverAddress, DeliveredBaggage deliveredBaggage, LocalDate sendingDate, LocalDate receivingDate, State state, Double price) {
        this.id = id;
        this.customer = customer;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.deliveredBaggage = deliveredBaggage;
        this.sendingDate = sendingDate;
        this.receivingDate = receivingDate;
        this.state = state;
        this.price = price;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryApplication that = (DeliveryApplication) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(senderAddress, that.senderAddress) && Objects.equals(receiverAddress, that.receiverAddress) && Objects.equals(deliveredBaggage, that.deliveredBaggage) && Objects.equals(sendingDate, that.sendingDate) && Objects.equals(receivingDate, that.receivingDate) && state == that.state && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, senderAddress, receiverAddress, deliveredBaggage, sendingDate, receivingDate, state, price);
    }
}
