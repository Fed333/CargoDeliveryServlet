package com.epam.cargo.dto;

import com.epam.cargo.infrastructure.annotation.DTO;

/**
 * Data Transfer Object for assembling PageRequest information with pagination.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@DTO
public class PageRequest {

    /**
     * Order number of page, starting with 0.
     * */
    private Integer page = 0;

    /**
     * Number of records within one page.
     * */
    private Integer size = 9;

    private SortRequest sort;

    public PageRequest() {
    }

    public PageRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public SortRequest getSort() {
        return sort;
    }

    public void setSort(SortRequest sort) {
        this.sort = sort;
    }
}
