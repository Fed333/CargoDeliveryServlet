package com.epam.cargo.dto;

import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object to assemble baggage on making delivery application page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveredBaggageRequest {

    private Double weight;

    private Double volume;

    private BaggageType type;

    private String description;

    @SuppressWarnings("unused")
    public DeliveredBaggageRequest() {
    }

    public DeliveredBaggageRequest(Double weight, Double volume, BaggageType type, String description) {
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public BaggageType getType() {
        return type;
    }

    public void setType(BaggageType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}