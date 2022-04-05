package com.epam.cargo.dto;

import com.epam.cargo.entity.City;
import org.fed333.servletboot.annotation.DTO;

/**
 * Data Transfer Object to transfer response of cost calculation.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DeliveryCostCalculatorResponse {
    private City.Distance distance;
    private Double cost;

    public DeliveryCostCalculatorResponse() { }

    public DeliveryCostCalculatorResponse(Double cost, City.Distance distance) {
        this.distance = distance;
        this.cost = cost;
    }

    public static DeliveryCostCalculatorResponse of(City.Distance distance, Double cost){
        DeliveryCostCalculatorResponse response = new DeliveryCostCalculatorResponse();
        response.setDistance(distance);
        response.setCost(cost);
        return response;
    }

    public City.Distance getDistance() {
        return distance;
    }

    public void setDistance(City.Distance distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
