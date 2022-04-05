package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.City;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.CityRepoImpl.CityColumns.*;

/**
 * Implementation of CityRepo interface. <br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean. <br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
@Singleton(type = Singleton.Type.LAZY)
public class CityRepoImpl implements CityRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM cities WHERE id = ?";
    private static final String SELECT_BY_ZIPCODE = "SELECT * FROM cities WHERE zipcode = ?";
    private static final String SELECT_ALL = "SELECT * FROM cities";
    private static final String INSERT_INTO = "INSERT INTO cities (name, zipcode) VALUES (?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE cities SET name = ?, zipcode = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM cities WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    private CityDaoPersist cityDaoPersist;

    @SuppressWarnings("unused")
    public CityRepoImpl() {}

    public CityRepoImpl(ConnectionPool pool) {
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        cityDaoPersist = new CityDaoPersist(pool);
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityDaoPersist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public Optional<City> findByZipcode(String zipcode) {
        return cityDaoPersist.findBy(SELECT_BY_ZIPCODE, ps -> ps.setString(1, zipcode));
    }

    @Override
    public List<City> findAll() {
        return cityDaoPersist.findAll(SELECT_ALL);
    }

    @Override
    public City save(City o) {
        requireValidCity(o);

        if (Objects.isNull(o.getId())){
            return insert(o);
        }
        else {
            return update(o);
        }
    }

    @Override
    public City update(City o) {
        return cityDaoPersist.update(UPDATE_BY_ID, o);
    }

    @Override
    public City insert(City o) {
        return cityDaoPersist.create(INSERT_INTO, o);
    }

    private void requireValidCity(City o) {
        if (Objects.isNull(o)){
            throw new IllegalArgumentException("City cannot be null");
        }
        if (Objects.isNull(o.getName()) || o.getName().isBlank()){
            throw new IllegalArgumentException("Name of city cannot be null or blank");
        }
        if (Objects.isNull(o.getZipcode()) || o.getZipcode().isBlank()){
            throw new IllegalArgumentException("Zipcode of city cannot be null or blank");
        }
    }

    @Override
    public void delete(City o) {
        cityDaoPersist.delete(DELETE_BY_ID, o);
    }


    enum CityColumns {
        ID("id"), NAME("name"), ZIPCODE("zipcode");

        private final String column;

        CityColumns(String column) {
            this.column = column;
        }

        public String column() {
            return column;
        }
    }

    /**
     * Implementation of DaoPersist for CityRepoImpl class.<br>
     * Implements all abstract methods of DaoPersist interface.
     * @author Roman Kovalchuk
     * @version 1.0
     * */
    private static class CityDaoPersist extends DaoPersist<City, Long> {

        public CityDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public City parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.column());
            String name = result.getString(NAME.column());
            String zipcode = result.getString(ZIPCODE.column());
            return new City(id, name, zipcode);
        }

        @Override
        public void transferObjectToInsert(City o, PreparedStatement statement) throws SQLException {
            statement.setString(1, o.getName());
            statement.setString(2, o.getZipcode());
        }

        @Override
        public void transferObjectToUpdate(City o, PreparedStatement statement) throws SQLException {
            statement.setString(1, o.getName());
            statement.setString(2, o.getZipcode());
            statement.setLong(3, o.getId());
        }
    }
}
