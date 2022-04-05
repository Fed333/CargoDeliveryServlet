package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.DistanceFareRepo;
import com.epam.cargo.entity.DistanceFare;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.DistanceFareRepoImpl.DistanceFareColumns.*;

/**
 * Implementation of {@link DistanceFareRepo} interface.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DistanceFareRepoImpl implements DistanceFareRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM distance_fares WHERE id = ?";
    private static final String SELECT_BY_DISTANCE = "SELECT * FROM distance_fares WHERE ? BETWEEN distance_from AND distance_to";
    private static final String SELECT_MAX_FARE = "SELECT * FROM distance_fares WHERE price = (SELECT MAX(price) FROM distance_fares)";
    private static final String SELECT_ALL = "SELECT * FROM distance_fares";
    private static final String INSERT_INTO = "INSERT INTO distance_fares (distance_from, distance_to, price) VALUES(?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE distance_fares SET distance_from = ?, distance_to = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM distance_fares WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    private DistanceFareDaoPersist persist;

    @SuppressWarnings("unused")
    public DistanceFareRepoImpl() {
    }

    public DistanceFareRepoImpl(ConnectionPool pool) {
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DistanceFareDaoPersist(pool);
    }

    @Override
    public Optional<DistanceFare> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public DistanceFare findFareByDistance(Integer distance) {
        return persist.findBy(SELECT_BY_DISTANCE, ps -> ps.setInt(1, distance)).orElse(null);
    }

    @Override
    public DistanceFare findMaxFare() {
        return persist.findBy(SELECT_MAX_FARE, ps -> {}).orElse(null);
    }

    @Override
    public List<DistanceFare> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public DistanceFare insert(DistanceFare o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DistanceFare update(DistanceFare o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DistanceFare o) {
        persist.delete(DELETE_BY_ID, o);
    }

    private static class DistanceFareDaoPersist extends DaoPersist<DistanceFare, Long> {

        public DistanceFareDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DistanceFare parseObjectFrom(ResultSet result) throws SQLException {
            return new DistanceFare(
                    result.getLong(ID.getColumn()),
                    result.getInt(DISTANCE_FROM.getColumn()),
                    result.getInt(DISTANCE_TO.getColumn()),
                    result.getDouble(PRICE.getColumn())
            );
        }

        @Override
        public void transferObjectToInsert(DistanceFare o, PreparedStatement statement) throws SQLException {
            setDistanceFareData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(DistanceFare o, PreparedStatement statement) throws SQLException {
            setDistanceFareData(o, statement);
            statement.setLong(4, o.getId());
        }

        private void setDistanceFareData(DistanceFare o, PreparedStatement statement) throws SQLException {
            statement.setInt(1, o.getDistanceFrom());
            statement.setInt(2, o.getDistanceTo());
            statement.setDouble(3, o.getPrice());
        }

    }

    enum DistanceFareColumns {
        ID("id"),
        DISTANCE_FROM("distance_from"),
        DISTANCE_TO("distance_to"),
        PRICE("price");

        private final String column;

        DistanceFareColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }
}
