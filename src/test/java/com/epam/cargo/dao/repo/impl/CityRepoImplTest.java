package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.repo.CityRepo;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.ScriptExecutorUtils;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dao.utils.parse.impl.CityParser;
import com.epam.cargo.entity.City;
import org.fed333.servletboot.context.ApplicationContext;
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
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;


class CityRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/cities/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/cities/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/cities/test-delete.sql";

    private CityRepo cityRepo;

    private Connection connection;

    public static Stream<Arguments> testCityCase() {
        return Stream.of(
            Arguments.of(TestDataUtils.getTestCity1())
        );
    }

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        ApplicationContext context = TestApplication.run();
        cityRepo = context.getObject(CityRepoImpl.class);
        ConnectionPool pool = context.getObject(ConnectionPool.class);
        connection = pool.getConnection();
        connection.setAutoCommit(true);
    }

    @AfterEach
    public void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @ParameterizedTest
    @MethodSource("testCityCase")
    void findById(City city) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        Optional<City> optionalCity = cityRepo.findById(city.getId());
        Assertions.assertTrue(optionalCity.isPresent());
        Assertions.assertEquals(city, optionalCity.orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("testCityCase")
    void findByZipcode(City city) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        Optional<City> optionalCity = cityRepo.findByZipcode(city.getZipcode());
        Assertions.assertTrue(optionalCity.isPresent());
        Assertions.assertEquals(city, optionalCity.orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("testCityCase")
    void save(City city) throws SQLException, FileNotFoundException {
        city.setId(null);
        cityRepo.save(city);
        Assertions.assertTrue(Objects.nonNull(city.getId()));
        Assertions.assertTrue(fetchAll().contains(city));
        city.setZipcode(city.getZipcode()+"111");
        cityRepo.save(city);
        Assertions.assertTrue(fetchAll().contains(city));
    }

    @ParameterizedTest
    @MethodSource("testCityCase")
    void delete(City city) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        cityRepo.delete(city);
        Assertions.assertFalse(fetchAll().contains(city));
    }

    private List<City> fetchAll() throws SQLException, FileNotFoundException {
        return fetchCities(ScriptExecutorUtils.executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    public static List<City> fetchCities(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new CityParser(), ArrayList::new);
    }
}