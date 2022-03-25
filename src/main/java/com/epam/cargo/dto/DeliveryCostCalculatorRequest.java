package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object to assemble data for delivering cost calculating.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveryCostCalculatorRequest {

    private Long cityFromId;

    private Long cityToId;

    private DimensionsRequest dimensions;

    private Double weight;

    public static DeliveryCostCalculatorRequest of(Long cityFromId, Long cityToId, DimensionsRequest dimensions, Double weight){
        DeliveryCostCalculatorRequest request = new DeliveryCostCalculatorRequest();
        request.setCityFromId(cityFromId);
        request.setCityToId(cityToId);
        request.setDimensions(dimensions);
        request.setWeight(weight);
        return request;
    }

    public Long getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(Long cityFromId) {
        this.cityFromId = cityFromId;
    }

    public Long getCityToId() {
        return cityToId;
    }

    public void setCityToId(Long cityToId) {
        this.cityToId = cityToId;
    }

    public DimensionsRequest getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsRequest dimensions) {
        this.dimensions = dimensions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}