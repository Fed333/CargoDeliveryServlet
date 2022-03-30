package com.epam.cargo.infrastructure.web.data.page;

import com.epam.cargo.infrastructure.web.data.pageable.Pageable;

import java.util.List;

/**
 * Representation of paginated page.<br>
 * Contains pagination information and corresponding page content.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Page<T> {

    /**
     * Gives number of total pages.<br>
     * @return number of all available pages
     * @since 1.0
     * */
    int getTotalPages();

    /**
     * Gives number of current page.<br>
     * @return page number
     * @since 1.0
     * */
    int getNumber();

    /**
     * Gives size of page.<br>
     * @return number of records in the page
     * @since 1.0
     * */
    int getSize();

    /**
     * Gives records of this page.<br>
     * @return List of objects for the page
     * @since 1.0
     * */
    List<T> getContent();

    /**
     * Gives Pageable from where this Page was made.<br>
     * @return Pageable object
     * @since 1.0
     * */
    Pageable getPageable();

}
