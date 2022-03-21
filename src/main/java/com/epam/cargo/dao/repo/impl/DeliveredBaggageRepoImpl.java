package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.DeliveredBaggageRepo;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.DeliveredBaggageRepoImpl.DeliveredBaggageColumns.*;

/**
 * Implementation of {@link DeliveredBaggageRepo} interface.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DeliveredBaggageRepoImpl implements DeliveredBaggageRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM delivered_baggage WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM delivered_baggage";
    private static final String INSERT_INTO = "INSERT INTO delivered_baggage (weight, volume, type, description) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE delivered_baggage SET weight = ?, volume = ?, type = ?, description = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM delivered_baggage WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    private DeliveredBaggageDaoPersist persist;

    @SuppressWarnings("unused")
    public DeliveredBaggageRepoImpl() {
    }

    public DeliveredBaggageRepoImpl(ConnectionPool pool) {
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DeliveredBaggageDaoPersist(pool);
    }

    @Override
    public Optional<DeliveredBaggage> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public List<DeliveredBaggage> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public DeliveredBaggage insert(DeliveredBaggage o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DeliveredBaggage update(DeliveredBaggage o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DeliveredBaggage o) {
        persist.delete(DELETE_BY_ID, o);
    }

    enum DeliveredBaggageColumns {

        ID("id"),
        WEIGHT("weight"),
        VOLUME("volume"),
        TYPE("type"),
        DESCRIPTION("description");

        private final String column;

        DeliveredBaggageColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

    private static class DeliveredBaggageDaoPersist extends DaoPersist<DeliveredBaggage, Long> {

        public DeliveredBaggageDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DeliveredBaggage parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.getColumn());
            Double weight = result.getDouble(WEIGHT.getColumn());
            Double volume = result.getDouble(VOLUME.getColumn());
            int typeOrdinal = result.getInt(TYPE.getColumn());
            BaggageType type = BaggageType.values()[typeOrdinal];
            String description = result.getString(DESCRIPTION.getColumn());

            return new DeliveredBaggage(
                    id,
                    weight,
                    volume,
                    type,
                    description
            );
        }

        @Override
        public void transferObjectToInsert(DeliveredBaggage o, PreparedStatement statement) throws SQLException {
            setDeliveredBaggageData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(DeliveredBaggage o, PreparedStatement statement) throws SQLException {
            setDeliveredBaggageData(o, statement);
            statement.setLong(5, o.getId());
        }

        private void setDeliveredBaggageData(DeliveredBaggage o, PreparedStatement statement) throws SQLException {
            statement.setDouble(1, o.getWeight());
            statement.setDouble(2, o.getVolume());
            statement.setInt(3, o.getType().ordinal());
            statement.setString(4, o.getDescription());
        }

    }

}