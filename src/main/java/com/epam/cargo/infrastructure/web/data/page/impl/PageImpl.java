package com.epam.cargo.infrastructure.web.data.page.impl;

import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.pageable.impl.PageRequest;

import java.util.List;

/**
 * Base implementation of Page interface.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class PageImpl<T> implements Page<T> {

    private final List<T> content;

    private final Pageable pageable;

    private final int totalPages;

    public PageImpl(List<T> content, Pageable pageable, int total) {
        this.content = content;
        this.pageable = pageable;
        this.totalPages = (int) Math.ceil( (double) total / Math.max(pageable.getPageSize(), 1));
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int getNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getSize() {
        return pageable.getPageSize();
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }
}