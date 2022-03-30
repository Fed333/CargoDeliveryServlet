package com.epam.cargo.infrastructure.web.data.pageable;

import com.epam.cargo.infrastructure.web.data.sort.Sort;

/**
 * Contains sort and pagination information about requested page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Pageable {

    /**
     * Gives number of current page.<br>
     * @return page number
     * @since 1.0
     * */
    int getPageNumber();

    /**
     * Creates new Pageable based on existing one, but starting with another page number.<br>
     * @param page number of page to start with
     * @return new Pageable which starts from new page number
     * @since 1.0
     * */
    Pageable withPage(int page);

    /**
     * Gives number of records in page.<br>
     * @return size of page
     * @since 1.0
     * */
    int getPageSize();

    /**
     * Calculates offset with start to current position.<br>
     * @return offset from start to page number
     * @since 1.0
     * */
    int getOffset();

    /**
     * Gives page sorting data.<br>
     * @return Sort object with sorting data
     * @since 1.0
     * */
    Sort getSort();
}
