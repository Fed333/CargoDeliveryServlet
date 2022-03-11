package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object for assembling filter for DirectionDelivery objects on directions page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class SortRequest {

    private String sort;
    private Order order;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Ordering direction of sorting.<br>
     * @author Roman Kovalchuk
     * @since 1.0
     * */
    public enum Order{
        ASC, DESC
    }
}
