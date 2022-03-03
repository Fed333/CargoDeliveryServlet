package com.epam.cargo.entity;

/**
 * Class of distance fare
 * Used in calculating price of delivery cost
 * */
public class DistanceFare {

    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    private Integer distanceFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    private Integer distanceTo;

    private Double price;

    public DistanceFare() {

    }

    public DistanceFare(Long id, Integer distanceFrom, Integer distanceTo, Double price) {
        this.id = id;
        this.distanceFrom = distanceFrom;
        this.distanceTo = distanceTo;
        this.price = price;
    }
}