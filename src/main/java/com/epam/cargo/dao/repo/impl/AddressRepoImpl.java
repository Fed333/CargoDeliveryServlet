package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.repo.AddressRepo;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.AddressRepoImpl.AddressColumns.*;
import static com.epam.cargo.infrastructure.annotation.Singleton.Type.LAZY;

/**
 * Implementation of AddressRepo interface. <br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean. <br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = LAZY)
public class AddressRepoImpl implements AddressRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM addresses WHERE id = ?";
    private static final String SELECT_BY_HOUSE_NUMBER_AND_CITY_AND_STREET = "SELECT * FROM addresses WHERE house_number = ? AND city_id = ? AND street = ?";
    private static final String SELECT_ALL = "SELECT * FROM addresses";
    private static final String INSERT_INTO = "INSERT INTO addresses (city_id, street, house_number) VALUES (?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE addresses SET city_id = ?, street = ?, house_number = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM addresses WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    @Inject
    private CityRepo cityRepo;

    private AddressDaoPersist persist;

    @SuppressWarnings("unused")
    public AddressRepoImpl() {
    }

    public AddressRepoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @PostConstruct
    private void init(){
        persist = new AddressDaoPersist(pool);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
    }

    @Override
    public List<Address> findAll() {
        return persist.findAll(SELECT_ALL);
    }

    @Override
    public Address insert(Address o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public Address update(Address o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(Address o) {
        persist.delete(DELETE_BY_ID, o);
    }

    enum AddressColumns {

        ID("id"), CITY_ID("city_id"), STREET("street"), HOUSE_NUMBER("house_number");

        private final String column;

        AddressColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

    @Override
    public Address findByHouseNumberAndCityAndStreet(String houseNumber, City city, String street) {
        return persist.findBy(
                SELECT_BY_HOUSE_NUMBER_AND_CITY_AND_STREET,
                ps->{
                    ps.setString(1, houseNumber);
                    ps.setLong(2, city.getId());
                    ps.setString(3, street);
                }
        ).orElse(null);
    }


    private class AddressDaoPersist extends DaoPersist<Address, Long> {

        public AddressDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public Address parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.getColumn());
            Long cityId = result.getLong(CITY_ID.getColumn());
            City city = cityRepo.findById(cityId).orElseThrow(()->new NoSuchElementException("No found city with id " + cityId));
            String street = result.getString(STREET.getColumn());
            String houseNumber = result.getString(HOUSE_NUMBER.getColumn());
            return new Address(id, city, street, houseNumber);
        }

        @Override
        public void transferObjectToInsert(Address o, PreparedStatement statement) throws SQLException {
            statement.setLong(1, o.getCity().getId());
            statement.setString(2, o.getStreet());
            statement.setString(3, o.getHouseNumber());
        }

        @Override
        public void transferObjectToUpdate(Address o, PreparedStatement statement) throws SQLException {
            statement.setLong(1, o.getCity().getId());
            statement.setString(2, o.getStreet());
            statement.setString(3, o.getHouseNumber());
            statement.setLong(4, o.getId());
        }
    }
}