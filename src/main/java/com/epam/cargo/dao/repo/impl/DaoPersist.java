package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.entity.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Abstract class with common logic of CRUD Dao methods.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
public abstract class DaoPersist<T extends Entity<ID>, ID> {
    
    private final ConnectionPool pool;

    public DaoPersist(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * Assemble T object from ResultSet.
     * @param result data to create object with
     * @return T object assembled from ResultSet
     * @since 1.0
     * @see ResultSet
     * */
    public abstract T parseObjectFrom(ResultSet result) throws SQLException;

    /**
     * Transfers object of T class to PreparedStatement so that insert query was executable.
     * @param o T object to transfer
     * @param statement PreparedStatement object where we transfer to
     * @since 1.0
     * @see PreparedStatement
     * */
    public abstract void transferObjectToInsert(T o, PreparedStatement statement) throws SQLException;

    /**
     * Transfers object of T class to PreparedStatement so that update query was executable.
     * @param o T object to transfer
     * @param statement PreparedStatement object where we transfer to
     * @since 1.0
     * */
    public abstract void transferObjectToUpdate(T o, PreparedStatement statement) throws SQLException;

    /**
     * Finds one object according to the sql query and given arguments for statement
     * @param selectQuery a sql query
     * @param prepared prepared params to set preparedStatement
     * @return Optional with T object
     * @throws IllegalStateException when more than 1 element was found
     * @since 1.0
     * */
    public Optional<T> findBy(String selectQuery, PreparedStatementConsumer prepared) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            prepared.prepare(statement);
            return getObject(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (!Objects.isNull(connection)) {
                pool.releaseConnection(connection);
            }
        }

        return Optional.empty();
    }

    /**
     * Finds all T objects in selected by given sql query.
     * @param selectAllQuery sql query to select all records from database
     * @return List of all T objects from database
     * @since 1.0
     * */
    public List<T> findAll(String selectAllQuery) {
        return findAllBy(selectAllQuery, ps -> {});
    }

    /**
     * Finds all T objects in selected by given sql query.
     * @param selectQuery sql query to select all records from database
     * @return List of all T objects from database
     * @since 1.1
     * */
    public List<T> findAllBy(String selectQuery, PreparedStatementConsumer prepared) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            prepared.prepare(statement);
            return fetchAll(statement);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (!Objects.isNull(connection)){
                pool.releaseConnection(connection);
            }
        }
        return null;
    }

    /**
     * Gives number of records by countQuery.<br>
     * @param countQuery query to find count of records
     * @param prepared actions to prepare PreparedStatement
     * @return count of records
     * */
    public int countBy(String countQuery, PreparedStatementConsumer prepared){
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement(countQuery);
            prepared.prepare(ps);
            ResultSet resultSet = ps.executeQuery();
            int total = 0;
            if (resultSet.next()){
                total = resultSet.getInt(1);
            }
            return total;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get count of records from database.", e);
        } finally {
            if (Objects.nonNull(connection)) {
                pool.releaseConnection(connection);
            }
        }
    }

    /**
     * Inserts object of class T into database. Makes insert according to the given query.<br>
     * @param insertInto sql query to execute inserting
     * @param o object to insert
     * @return inserted object of T class
     * @since 1.0
     * */
    @SuppressWarnings("unchecked")
    public T create(String insertInto, T o) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertInto, Statement.RETURN_GENERATED_KEYS);
            transferObjectToInsert(o, statement);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                Object object = generatedKeys.getObject(1);
                o.setId((ID)object);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(connection)){
                pool.releaseConnection(connection);
            }
        }
        return o;
    }

    /**
     * Updates record of given object in database.
     * @param updateById sql query to execute update of object
     * @param o object to update
     * @return updated object
     * @since 1.0
     * */
    public T update(String updateById, T o) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateById);
            transferObjectToUpdate(o, statement);
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(connection)){
                pool.releaseConnection(connection);
            }
        }
        return o;
    }

    /**
     * Removes records from database which belongs to the T object.
     * @param deleteById sql query to execute delete
     * @param o object to be removed from database
     * @throws IllegalArgumentException if parameter o has null id
     * @since 1.0
     * */
    public void delete(String deleteById, T o) {
        if (Objects.isNull(o)){
            return;
        }

        if (Objects.isNull(o.getId())){
            throw new IllegalArgumentException("Cannot delete object with id null!");
        }

        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteById);
            statement.setLong(1, (Long)o.getId());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(connection)){
                pool.releaseConnection(connection);
            }
        }
    }

    private Optional<T> getObject(PreparedStatement statement) throws SQLException {
        List<T> list = fetchAll(statement);
        if (list.isEmpty()){
            return Optional.empty();
        }
        if (list.size() > 1){
            throw new IllegalStateException("More than 1 element was found!");
        }
        return Optional.ofNullable(list.get(0));
    }

    protected List<T> fetchAll(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        List<T> objects = new ArrayList<>();
        while (result.next()){
            objects.add(parseObjectFrom(result));
        }
        return objects;
    }

    /**
     * Interface for special preparing action under PreparedStatement.
     * @author Roman Kovalchuk
     * @version 1.0
     * */
    interface PreparedStatementConsumer {

        void prepare(PreparedStatement preparedStatement) throws SQLException;

        @SuppressWarnings("unused")
        default PreparedStatementConsumer prepareNext(PreparedStatementConsumer after) {
            return o -> {prepare(o); after.prepare(o);};
        }
    }

}