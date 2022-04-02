package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.TestConnectionManager;
import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dao.utils.parse.impl.AddressParser;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;

class AddressRepoImplTest {

    private static final String FILL_ADDRESSES_SQL_SCRIPT_PATH = "src/test/resources/db.sql/addresses/test-insert.sql";
    private static final String DELETE_ADDRESSES_SQL_SCRIPT_PATH = "src/test/resources/db.sql/addresses/test-delete.sql";
    private static final String SELECT_ADDRESSES_SQL_SCRIPT_PATH = "src/test/resources/db.sql/addresses/select-all.sql";

    private AddressRepoImpl addressRepo;
    private CityRepoImpl cityRepo;

    private Connection connection;

    public static Stream<Arguments> testAddressCase() {
        return Stream.of(
          Arguments.of(TestDataUtils.getTestAddress1())
        );
    }

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        ApplicationContext context = TestApplication.run();
        addressRepo = context.getObject(AddressRepoImpl.class);
        cityRepo = context.getObject(CityRepoImpl.class);
        ConnectionPool pool = context.getObject(ConnectionPool.class);
        connection = new TestConnectionManager().getConnection();
        pool.getConnection().setAutoCommit(true);
    }

    @AfterEach
    public void tearDown() throws IOException, SQLException {
        executeSQLScript(connection, DELETE_ADDRESSES_SQL_SCRIPT_PATH);
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    public void findById(Address address) throws SQLException, IOException {
        executeSQLScript(connection, FILL_ADDRESSES_SQL_SCRIPT_PATH);
        Long id = address.getId();
        Assertions.assertEquals(address, addressRepo.findById(id).orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    void findAll(Address address) throws IOException, SQLException {
        executeSQLScript(connection, FILL_ADDRESSES_SQL_SCRIPT_PATH);
        List<Address> addresses = addressRepo.findAll();
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertTrue(addresses.contains(address));
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    void insert(Address address) throws SQLException, FileNotFoundException {
        cityRepo.insert(address.getCity());
        addressRepo.insert(address);
        Long id = address.getId();
        Assertions.assertTrue(Objects.nonNull(id));
        List<Address> all = fetchAll();
        Assertions.assertTrue(all.contains(address));
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    void update(Address address) throws IOException, SQLException {
        executeSQLScript(new TestConnectionManager().getConnection(), FILL_ADDRESSES_SQL_SCRIPT_PATH);
        Address old = new Address(
                address.getId(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber()
        );
        address.setStreet("testStreet2");
        address.setHouseNumber("houseNumber2");
        addressRepo.update(address);
        List<Address> all = fetchAll();
        Assertions.assertFalse(all.contains(old));
        Assertions.assertTrue(all.contains(address));
    }

    @NotNull
    private List<Address> fetchAll() throws SQLException, FileNotFoundException {
        return fetchAddresses(executeSelectSQLScript(connection, SELECT_ADDRESSES_SQL_SCRIPT_PATH));
    }

    private static List<Address> fetchAddresses(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new AddressParser(), ArrayList::new);
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    void delete(Address address) throws IOException, SQLException {
        executeSQLScript(new TestConnectionManager().getConnection(), FILL_ADDRESSES_SQL_SCRIPT_PATH);
        addressRepo.delete(address);
        List<Address> all = fetchAll();
        Assertions.assertFalse(all.contains(address));
    }

    @ParameterizedTest
    @MethodSource("testAddressCase")
    void findByHouseNumberAndCityAndStreet(Address address) throws IOException, SQLException {
        executeSQLScript(new TestConnectionManager().getConnection(), FILL_ADDRESSES_SQL_SCRIPT_PATH);

        String houseNumber = address.getHouseNumber();
        City city = address.getCity();
        String street = address.getStreet();
        Assertions.assertEquals(address, addressRepo.findByHouseNumberAndCityAndStreet(houseNumber, city, street));
    }
}