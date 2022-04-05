package com.epam.cargo.dto;

import org.fed333.servletboot.annotation.DTO;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Data Transfer Object to assemble data for Dimensions object.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class DimensionsRequest {

    private Double length;

    private Double width;

    private Double height;

    /**
     * calculate the volume according to given dimensions fields: length, width, height
     * @return the volume in cm^3
     * @throws IllegalArgumentException if field is present but is less equal than zero
     * @throws NullPointerException if field wasn't passed and is null
     * */
    public Double getVolume(){
        return  requireValidField(this::getLength, "length") *
                requireValidField(this::getWidth, "width") *
                requireValidField(this::getHeight, "height");
    }

    private Double requireValidField(Supplier<Double> field, String fieldName){
        Double value = field.get();
        Objects.requireNonNull(value, "field " + fieldName + " cannot be null");
        if (value <= 0){
            throw new IllegalArgumentException("field " + fieldName + " cannot be negative or zero");
        }
        return value;
    }

    public static DimensionsRequest of(Double length, Double width, Double height){
        DimensionsRequest request = new DimensionsRequest();
        request.setLength(length);
        request.setWidth(width);
        request.setHeight(height);
        return request;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
