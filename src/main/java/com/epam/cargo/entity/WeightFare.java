package com.epam.cargo.entity;

/**
 * Class of weight fare
 * Used in calculating price of delivery cost
 * */
public class WeightFare {
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
}
