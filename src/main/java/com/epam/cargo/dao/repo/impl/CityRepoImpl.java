package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.City;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.CityRepoImpl.CityColumns.*;

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

    @SuppressWarnings("unused")
    public CityRepoImpl() {}

    public CityRepoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Optional<City> findById(Long id) {
        return findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public Optional<City> findByZipcode(String zipcode) {
        return findBy(SELECT_BY_ZIPCODE, ps -> ps.setString(1, zipcode));
    }

    @Override
    public List<City> findAll() {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
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

    private City update(City o) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);
            statement.setString(1, o.getName());
            statement.setString(2, o.getZipcode());
            statement.setLong(3, o.getId());
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

    private City insert(City o) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, o.getName());
            statement.setString(2, o.getZipcode());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                o.setId(generatedKeys.getLong(1));
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
        if (Objects.isNull(o)){
            return;
        }

        if (Objects.isNull(o.getId())){
            throw new IllegalArgumentException("Cannot delete city with id null!");
        }

        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, o.getId());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(connection)){
                pool.releaseConnection(connection);
            }
        }
    }

    /**
     * Finds one object according to the sql query and given arguments for statement
     * @param selectQuery a sql query
     * @param prepared prepared params to set preparedStatement
     * @return Optional with City object
     * @throws IllegalStateException when more than 1 element was found
     * */
    public Optional<City> findBy(String selectQuery, PreparedStatementConsumer prepared) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            prepared.prepare(statement);
            return getCity(statement);

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

    private Optional<City> getCity(PreparedStatement statement) throws SQLException {
        List<City> list = fetchAll(statement);
        if (list.isEmpty()){
            return Optional.empty();
        }
        if (list.size() > 1){
            throw new IllegalStateException("More than 1 element was found!");
        }
        return Optional.ofNullable(list.get(0));
    }

    private List<City> fetchAll(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        List<City> cities = new ArrayList<>();
        while (result.next()){
            cities.add(parseCity(result));
        }
        return cities;
    }

    private City parseCity(ResultSet result) throws SQLException {
        Long id = result.getLong(ID.column());
        String name = result.getString(NAME.column());
        String zipcode = result.getString(ZIPCODE.column());
        return new City(id, name, zipcode);
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

    interface PreparedStatementConsumer {

        void prepare(PreparedStatement preparedStatement) throws SQLException;

        default PreparedStatementConsumer prepareNext(PreparedStatementConsumer after) {
            return o -> {prepare(o); after.prepare(o);};
        }
    }
}
