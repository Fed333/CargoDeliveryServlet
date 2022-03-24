package com.epam.cargo.entity;

import java.util.Objects;

/**
 * POJO class of distance fare.
 * Used in calculating price of delivery cost.
 * Implements Entity interface.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class DistanceFare implements Entity<Long>{

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDistanceFrom() {
        return distanceFrom;
    }

    public void setDistanceFrom(Integer distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    public Integer getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(Integer distanceTo) {
        this.distanceTo = distanceTo;
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
        DistanceFare that = (DistanceFare) o;
        return Objects.equals(id, that.id) && Objects.equals(distanceFrom, that.distanceFrom) && Objects.equals(distanceTo, that.distanceTo) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distanceFrom, distanceTo, price);
    }
}