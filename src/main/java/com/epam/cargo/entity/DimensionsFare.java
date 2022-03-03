package com.epam.cargo.entity;

/**
 * Class of dimensions fare
 * Used in calculating price of delivery cost
 * */
public class DimensionsFare {

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
}
