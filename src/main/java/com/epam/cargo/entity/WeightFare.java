package com.epam.cargo.entity;

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
}
