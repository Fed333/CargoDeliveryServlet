package com.epam.cargo.entity;

/**
 * Represents delivered baggage
 * Used as a part of delivery application
 * */
public class DeliveredBaggage {

    private Long id;

    private Double weight;

    private Double volume;

    private BaggageType type;

    private String description;

    public DeliveredBaggage() {
    }

    public DeliveredBaggage(Long id, Double weight, Double volume, BaggageType type, String description) {
        this.id = id;
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }
}
