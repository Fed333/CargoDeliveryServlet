package com.epam.cargo.entity;

import java.util.Objects;

/**
 * POJO class of weight fare.
 * Used in calculating price of delivery cost.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class WeightFare implements Entity<Long>{

    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    private Integer weightFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    private Integer weightTo;

    private Double price;

    public WeightFare() {
    }

    public WeightFare(Long id, Integer weightFrom, Integer weightTo, Double price) {
        this.id = id;
        this.weightFrom = weightFrom;
        this.weightTo = weightTo;
        this.price = price;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(Integer weightFrom) {
        this.weightFrom = weightFrom;
    }

    public Integer getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(Integer weightTo) {
        this.weightTo = weightTo;
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
        WeightFare that = (WeightFare) o;
        return Objects.equals(id, that.id) && Objects.equals(weightFrom, that.weightFrom) && Objects.equals(weightTo, that.weightTo) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weightFrom, weightTo, price);
    }
}
