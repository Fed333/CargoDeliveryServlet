package com.epam.cargo.dao.persist;

import org.fed333.servletboot.web.data.pageable.Pageable;
import org.fed333.servletboot.web.data.sort.Sort;

/**
 * Simple builder of pageable sql queries.<br>
 * Works only with table columns itself. Doesn't work with relations mapping.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class PageableQueryBuilder {

    /**
     * Object for recognizing column names from sort properties.
     * */
    private final OrderColumnRecognizer orderColumnRecognizer;

    public PageableQueryBuilder(OrderColumnRecognizer orderColumnRecognizer) {
        this.orderColumnRecognizer = orderColumnRecognizer;
    }

    /**
     * Builds select sql query with sorting and pagination.<br>
     * @param selectQuery initial select sql query
     * @param pageable source of sorting and pagination data
     * @return concatenation of selectQuery and {@link #buildPageQuery(Pageable)}
     * @since 1.0
     * */
    public String buildPageQuery(String selectQuery, Pageable pageable){
        return selectQuery + " " + buildPageQuery(pageable);
    }

    /**
     * Builds part of sql query with order and limit.<br>
     * @param pageable source of data to obtain page
     * @return string with sql commands of sorting and limiting
     * @since 1.0
     * */
    public String buildPageQuery(Pageable pageable){
        return orderQuery(pageable.getSort()) + " " + limitQuery(pageable.getPageSize(), pageable.getOffset());
    }

    /**
     * Builds limit query with page limit and offset.<br>
     * @param limit number of records in select
     * @param offset skipped number of records
     * @since 1.0
     * @return string with limit sql query
     * */
    private String limitQuery(int limit, int offset){
        return String.format("LIMIT %d OFFSET %d", limit, offset);
    }

    /**
     * Builds order query from {@link Sort}.<br>
     * @param sort source of sorting orders
     * @since 1.0
     * @return string with order sql query if sort contains orders, otherwise ""
     * */
    private String orderQuery(Sort sort){
        if (sort.hasOrders()) {
            String orderedColumns = orderColumnRecognizer.recognizeOrders(sort);
            if (!orderedColumns.isBlank()) {
                return "ORDER BY " + orderedColumns;
            }
        }
        return "";
    }

}
