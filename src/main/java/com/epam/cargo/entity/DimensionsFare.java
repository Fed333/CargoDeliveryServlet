package com.epam.cargo.entity;

import java.util.Objects;

/**
 * POJO class of dimensions fare.
 * Used in calculating price of delivery cost.
 * Implements Entity interface.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public class DimensionsFare implements Entity<Long>{

    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    private Integer dimensionsFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    private Integer dimensionsTo;

    private Double price;

    public DimensionsFare() {
    }

    public DimensionsFare(Long id, Integer dimensionsFrom, Integer dimensionsTo, Double price) {
        this.id = id;
        this.dimensionsFrom = dimensionsFrom;
        this.dimensionsTo = dimensionsTo;
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

    public Integer getDimensionsFrom() {
        return dimensionsFrom;
    }

    public void setDimensionsFrom(Integer dimensionsFrom) {
        this.dimensionsFrom = dimensionsFrom;
    }

    public Integer getDimensionsTo() {
        return dimensionsTo;
    }

    public void setDimensionsTo(Integer dimensionsTo) {
        this.dimensionsTo = dimensionsTo;
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
        DimensionsFare that = (DimensionsFare) o;
        return Objects.equals(id, that.id) && Objects.equals(dimensionsFrom, that.dimensionsFrom) && Objects.equals(dimensionsTo, that.dimensionsTo) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dimensionsFrom, dimensionsTo, price);
    }
}
