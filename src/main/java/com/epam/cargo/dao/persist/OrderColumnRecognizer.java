package com.epam.cargo.dao.persist;

import org.fed333.servletboot.web.data.pageable.Pageable;
import org.fed333.servletboot.web.data.sort.Order;
import org.fed333.servletboot.web.data.sort.Sort;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * Recognizes columns of mapped object with {@link Pageable}.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class OrderColumnRecognizer {

    /**
     * Map with Entity's sorted field name and corresponding column in database.
     * */
    private final Map<String, String> orderedColumns;

    /**
     * Creates object from source data for {@link Map}
     * with sorted field name as a key and column name in database as a value.
     * <pre> {@code
     *     OrderColumnRecognizer.of(
     *          "key1", "value1",
     *          "key2", "value2",
     *          "key3", "value3"
     *     );
     * } </pre>
     * @param a array with key-value pairs for {@link Map} inside
     * @return customized {@link OrderColumnRecognizer} object
     * */
    public static OrderColumnRecognizer of(String...a){
        if (isOdd(a)){
            throw new RuntimeException("Number of varargs must be an even number!");
        }
        Map<String, String> orderedColumns = new HashMap<>();
        for (int i = 0; i < a.length - 1; i+=2) {
            orderedColumns.put(a[i], a[i+1]);
        }
        return new OrderColumnRecognizer(orderedColumns);
    }

    /**
     * Creates object with given field name to column name Map.
     * @param orderedColumns map with sorted field name as a key and column name in database as a value
     * */
    public OrderColumnRecognizer(Map<String, String> orderedColumns) {
        this.orderedColumns = orderedColumns;
    }

    /**
     * Recognize sort orders from {@link Sort}.<br>
     * @param sort source of sorting orders
     * @return String with order columns separated with ','
     * @since 1.0
     * */
    @NotNull
    public String recognizeOrders(Sort sort){
        StringJoiner joiner = new StringJoiner(",");

        for (Order order : sort.getOrders()) {
            String orderedColumn = recognizeOrder(order);
            if (!orderedColumn.isBlank()) {
                joiner.add(orderedColumn);
            }
        }

        return joiner.toString();
    }

    /**
     * Recognize sort orders from {@link Order}.<br>
     * @param order source of sorting order
     * @return String with order if it was recognized, otherwise returns ""
     * @since 1.0
     * */
    @NotNull
    public String recognizeOrder(Order order){
        String orderedColumn = orderedColumns.get(order.getProperty());
        if (Objects.nonNull(orderedColumn)) {
            return orderedColumn + " " + (order.isAscending() ? "ASC" : "DESC");
        }
        return "";
    }

    private static boolean isOdd(String[] a) {
        return (a.length & 1) != 0;
    }

}
