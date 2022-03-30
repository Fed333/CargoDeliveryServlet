package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object to assemble data for DeliveryReceipt on making delivery receipt page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveryReceiptRequest {

    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
