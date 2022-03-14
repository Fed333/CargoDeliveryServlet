package com.epam.cargo.entity;

/**
 * Interface of all entity classes.
 * Contains information about primary key - ID type.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Entity<ID>{

    ID getId();

    void setId(ID id);
}