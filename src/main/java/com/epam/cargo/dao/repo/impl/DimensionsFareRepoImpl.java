package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.DimensionsFareRepo;
import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.DimensionsFareRepoImpl.DimensionsFareColumns.*;

/**
 * Implementation of {@link DimensionsFareRepo} interface.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DimensionsFareRepoImpl implements DimensionsFareRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM dimensions_fares WHERE id = ?";
    private static final String SELECT_BY_VOLUME = "SELECT * FROM dimensions_fares WHERE ? BETWEEN dimensions_from AND dimensions_to";
    private static final String SELECT_MAX_FARE = "SELECT * FROM dimensions_fares WHERE price = (SELECT MAX(price) FROM dimensions_fares)";
    private static final String SELECT_ALL = "SELECT * FROM dimensions_fares";
    private static final String INSERT_INTO = "INSERT INTO dimensions_fares (dimensions_from, dimensions_to, price) VALUES(?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE dimensions_fares SET dimensions_from = ?, dimensions_to = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM dimensions_fares WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    private DimensionsFareDaoPersist persist;

    @SuppressWarnings("unused")
    public DimensionsFareRepoImpl() {
    }

    public DimensionsFareRepoImpl(ConnectionPool pool) {
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DimensionsFareDaoPersist(pool);
    }

    @Override
    public Optional<DimensionsFare> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public DimensionsFare findFareByVolume(Integer volume) {
        return persist.findBy(SELECT_BY_VOLUME, ps -> ps.setInt(1, volume)).orElse(null);
    }

    @Override
    public DimensionsFare findMaxFare() {
        return persist.findBy(SELECT_MAX_FARE, ps -> {}).orElse(null);
    }

    @Override
    public List<DimensionsFare> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public DimensionsFare insert(DimensionsFare o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DimensionsFare update(DimensionsFare o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DimensionsFare o) {
        persist.delete(DELETE_BY_ID, o);
    }

    private static class DimensionsFareDaoPersist extends DaoPersist<DimensionsFare, Long> {

        public DimensionsFareDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DimensionsFare parseObjectFrom(ResultSet result) throws SQLException {
            return new DimensionsFare(
                    result.getLong(ID.getColumn()),
                    result.getInt(DIMENSIONS_FROM.getColumn()),
                    result.getInt(DIMENSIONS_TO.getColumn()),
                    result.getDouble(PRICE.getColumn())
            );
        }

        @Override
        public void transferObjectToInsert(DimensionsFare o, PreparedStatement statement) throws SQLException {
            setDimensionsFareData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(DimensionsFare o, PreparedStatement statement) throws SQLException {
            setDimensionsFareData(o, statement);
            statement.setLong(4, o.getId());
        }

        private void setDimensionsFareData(DimensionsFare o, PreparedStatement statement) throws SQLException {
            statement.setInt(1, o.getDimensionsFrom());
            statement.setInt(2, o.getDimensionsTo());
            statement.setDouble(3, o.getPrice());
        }

    }

    enum DimensionsFareColumns {
        ID("id"),
        DIMENSIONS_FROM("dimensions_from"),
        DIMENSIONS_TO("dimensions_to"),
        PRICE("price");

        private final String column;

        DimensionsFareColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

}
