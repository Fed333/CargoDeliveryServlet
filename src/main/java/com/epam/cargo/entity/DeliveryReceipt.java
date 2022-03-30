package com.epam.cargo.entity;

import java.time.LocalDate;

/**
 * POJO class which represents receipt of delivery.
 * Used in approving delivery applications with further paying for service.
 * Implements Entity, Receipt interfaces.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class DeliveryReceipt implements Entity<Long>, Receipt {

    private Long id;

    private DeliveryApplication application;

    private User customer;

    private User manager;

    private Double totalPrice;

    private LocalDate formationDate;

    private Boolean paid;

    public DeliveryReceipt() {
    }

    public DeliveryReceipt(Long id, DeliveryApplication application, User customer, User manager, Double totalPrice, LocalDate formationDate, Boolean paid) {
        this.id = id;
        this.application = application;
        this.customer = customer;
        this.manager = manager;
        this.totalPrice = totalPrice;
        this.formationDate = formationDate;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryApplication getApplication() {
        return application;
    }

    public void setApplication(DeliveryApplication application) {
        this.application = application;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public LocalDate getFormationDate() {
        return formationDate;
    }

    public void setFormationDate(LocalDate formationDate) {
        this.formationDate = formationDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
