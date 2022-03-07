package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.Entity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Common generic CRUD interface for all DAO repositories.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public interface Dao<T extends Entity<ID>, ID> {

    /**
     * Finds POJO from database by id.
     * @param id primary key of POJO
     * @return Optional with object if found, otherwise an empty Optional
     * @since 1.0
     * @see Optional
     * */
    Optional<T> findById(ID id);

    /**
     * Finds all POJO from database.
     * @return List of POJO
     * @since 1.0
     * @see List
     * */
    List<T> findAll();

    /**
     * Saves POJO in database.
     * Depending on presence object in databases, execute creating or updating operations. <br>
     * If objects doesn't belong to the database, insert it into. <br>
     * If objects has specified id and belongs to the database, makes update.
     * @param o POJO to save
     * @return saved POJO
     * @since 1.0
     * */
    default T save(T o){
        if (Objects.isNull(o.getId())){
            return insert(o);
        }
        else {
            return update(o);
        }
    }

    /**
     * Updates POJO in database.
     * @param o POJO to update
     * @return updated POJO
     * @since 1.1
     * */
    T insert(T o);

    /**
     * Inserts POJO to database.
     * @param o POJO to insert
     * @return inserted POJO
     * @since 1.1
     * */
    T update(T o);

    /**
     * Removes POJO from database by id.<br>
     * @param o POJO to delete
     * */
    void delete(T o);
}
