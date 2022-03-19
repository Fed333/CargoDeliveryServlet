package com.epam.cargo.infrastructure.web.data.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable class. Embedded mean of storing sorting data.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public final class Sort {

    private final List<Order> orders;

    private Sort(List<Order> orders){
        this.orders = orders;
    }

    /**
     * Makes Sort object from orders.<br>
     * @param orders source Order data
     * @since 1.0
     * */
    public static Sort by(Order... orders){
        Objects.requireNonNull(orders, "Orders cannot be null");
        return new Sort(List.of(orders));
    }

    /**
     * Joins two Sort objects into a new one.<br>
     * @param sort Sort object which will go next
     * @since 1.0
     * */
    public Sort and(Sort sort){
        Objects.requireNonNull(sort, "Sort cannot be null");
        List<Order> orders = new ArrayList<>(this.orders);
        orders.addAll(sort.orders);
        return new Sort(orders);
    }

    /**
     * Gives sorting orders.<br>
     * @return an unmodifiable collection of Order objects
     * @since 1.0
     * */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
