package com.epam.cargo.infrastructure.web.data.pageable.impl;

import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.sort.Sort;

/**
 * Base implementation of Pageable interface.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public final class PageRequest implements Pageable {

    private int page;

    private int size;

    private Sort sort;

    public PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public Pageable withPage(int page) {
        return new PageRequest(0, size, sort);
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return page * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }
}
