package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.WeightFareRepo;
import com.epam.cargo.entity.WeightFare;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.WeightFareRepoImpl.WeightFareColumns.*;

/**
 * Implementation of {@link WeightFareRepo} interface.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class WeightFareRepoImpl implements WeightFareRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM weight_fares WHERE id = ?";
    private static final String SELECT_BY_WEIGHT = "SELECT * FROM weight_fares WHERE ? BETWEEN weight_from AND weight_to";
    private static final String SELECT_MAX_FARE = "SELECT * FROM weight_fares WHERE price = (SELECT MAX(price) FROM weight_fares)";
    private static final String SELECT_ALL = "SELECT * FROM weight_fares";
    private static final String INSERT_INTO = "INSERT INTO weight_fares (weight_from, weight_to, price) VALUES(?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE weight_fares SET weight_from = ?, weight_to = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM weight_fares WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    private WeightFareDaoPersist persist;

    @SuppressWarnings("unused")
    public WeightFareRepoImpl() {
    }

    public WeightFareRepoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @PostConstruct
    private void init(){
        persist = new WeightFareDaoPersist(pool);
    }

    @Override
    public Optional<WeightFare> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public WeightFare findFareByWeight(Integer weight) {
        return persist.findBy(SELECT_BY_WEIGHT, ps -> ps.setLong(1, weight)).orElse(null);
    }

    @Override
    public WeightFare findMaxFare() {
        return persist.findBy(SELECT_MAX_FARE, ps -> {}).orElse(null);
    }

    @Override
    public List<WeightFare> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public WeightFare insert(WeightFare o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public WeightFare update(WeightFare o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(WeightFare o) {
        persist.delete(DELETE_BY_ID, o);
    }

    private static class WeightFareDaoPersist extends DaoPersist<WeightFare, Long> {

        public WeightFareDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public WeightFare parseObjectFrom(ResultSet result) throws SQLException {
            return new WeightFare(
                    result.getLong(ID.getColumn()),
                    result.getInt(WEIGHT_FROM.getColumn()),
                    result.getInt(WEIGHT_TO.getColumn()),
                    result.getDouble(PRICE.getColumn())
            );
        }

        @Override
        public void transferObjectToInsert(WeightFare o, PreparedStatement statement) throws SQLException {
            setWeightFareData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(WeightFare o, PreparedStatement statement) throws SQLException {
            setWeightFareData(o, statement);
            statement.setLong(4, o.getId());
        }

        private void setWeightFareData(WeightFare o, PreparedStatement statement) throws SQLException {
            statement.setInt(1, o.getWeightFrom());
            statement.setInt(2, o.getWeightTo());
            statement.setDouble(3, o.getPrice());
        }

    }

    enum WeightFareColumns {
        ID("id"),
        WEIGHT_FROM("weight_from"),
        WEIGHT_TO("weight_to"),
        PRICE("price");

        private final String column;

        WeightFareColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }
}
