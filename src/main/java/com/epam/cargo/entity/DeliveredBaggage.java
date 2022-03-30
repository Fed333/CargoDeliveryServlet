package com.epam.cargo.entity;

import java.util.Objects;

/**
 * Represents delivered baggage.
 * Used as a part of delivery application.
 * Implements Entity interface.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class DeliveredBaggage implements Entity<Long>{

    private Long id;

    private Double weight;

    private Double volume;

    private BaggageType type;

    private String description;

    public DeliveredBaggage() {
    }

    public DeliveredBaggage(Long id) {
        this.id = id;
    }

    public DeliveredBaggage(Long id, Double weight, Double volume, BaggageType type, String description) {
        this.id = id;
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveredBaggage that = (DeliveredBaggage) o;
        return Objects.equals(id, that.id) && Objects.equals(weight, that.weight) && Objects.equals(volume, that.volume) && type == that.type && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, volume, type, description);
    }
}
