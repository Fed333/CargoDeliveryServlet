package com.epam.cargo.dto;

import com.epam.cargo.entity.Order;
import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object for assembling filter for DirectionDelivery objects on directions page.<br>
 * @author Roman Kovalchuk
 * @version 1.1
 * */
@DTO
public class SortRequest {

    /**
     * Property by we sort.
     * */
    private String property;

    /**
     * Direction order of sorting.
     * */
    private Order order;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
