package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.dao.repo.DirectionDeliveryRepo;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of DirectionDeliveryRepo interface. <br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean. <br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DirectionDeliveryRepoImpl implements DirectionDeliveryRepo {

    private static final String SELECT_ALL = "SELECT * FROM directions";
    private static final String SELECT_BY_ID = "SELECT * FROM directions WHERE id = ?";
    private static final String SELECT_BY_SENDER_AND_RECEIVER_CITY = "SELECT * FROM directions WHERE sender_city_id = ? AND receiver_city_id = ?";
    private static final String INSERT_INTO = "INSERT INTO directions (distance, sender_city_id, receiver_city_id) VALUES (?,?,?)";
    private static final String UPDATE_BY_ID = "UPDATE directions SET distance = ?, sender_city_id = ?, receiver_city_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM directions WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    @Inject
    private CityRepo cityRepo;

    private DirectionDeliveryDaoPersist persist;

    public DirectionDeliveryRepoImpl() {
    }

    public DirectionDeliveryRepoImpl(ConnectionPool connectionPool) {
        this.pool = connectionPool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DirectionDeliveryDaoPersist(pool);
    }

    @Override
    public Optional<DirectionDelivery> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps->{ps.setLong(1, id);});
    }

    @Override
    public List<DirectionDelivery> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity) {
        return persist.findBy(SELECT_BY_SENDER_AND_RECEIVER_CITY, ps->{ps.setLong(1, senderCity.getId()); ps.setLong(2, receiverCity.getId());}).orElse(null);
    }

    @Override
    public DirectionDelivery insert(DirectionDelivery o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DirectionDelivery update(DirectionDelivery o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DirectionDelivery o) {
        persist.delete(DELETE_BY_ID, o);
    }

    enum DirectionsColumns {
        ID("id"), DISTANCE("distance"), SENDER_CITY_ID("sender_city_id"), RECEIVER_CITY_ID("receiver_city_id");

        private final String column;

        DirectionsColumns(String column) {
            this.column = column;
        }

        public String column() {
            return column;
        }
    }

    private class DirectionDeliveryDaoPersist extends DaoPersist<DirectionDelivery, Long> {

        public DirectionDeliveryDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DirectionDelivery parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(DirectionsColumns.ID.column());
            Double distance = result.getDouble(DirectionsColumns.DISTANCE.column());
            Long senderCityId = result.getLong(DirectionsColumns.SENDER_CITY_ID.column());
            Long receiverCityId = result.getLong(DirectionsColumns.RECEIVER_CITY_ID.column());
            City senderCity = cityRepo.findById(senderCityId).orElseThrow(()->new RuntimeException("Required city with id " + senderCityId + " wasn't found!"));
            City receiverCity = cityRepo.findById(receiverCityId).orElseThrow(()->new RuntimeException("Required city with id " + senderCityId + " wasn't found!"));
            return new DirectionDelivery(id, senderCity, receiverCity, distance);
        }

        @Override
        public void transferObjectToInsert(DirectionDelivery o, PreparedStatement statement) throws SQLException {
            statement.setDouble(1, o.getDistance());
            statement.setLong(2, o.getSenderCity().getId());
            statement.setLong(3, o.getReceiverCity().getId());
        }

        @Override
        public void transferObjectToUpdate(DirectionDelivery o, PreparedStatement statement) throws SQLException {
            statement.setDouble(1, o.getDistance());
            statement.setLong(2, o.getSenderCity().getId());
            statement.setLong(3, o.getReceiverCity().getId());
            statement.setLong(4, o.getId());
        }
    }
}
