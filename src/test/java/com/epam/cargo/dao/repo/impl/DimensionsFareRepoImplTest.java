package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.parse.impl.DimensionsFareParser;
import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;

class DimensionsFareRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/dimensions_fares/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/dimensions_fares/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/dimensions_fares/test-delete.sql";

    private Connection connection;

    private DimensionsFareRepoImpl dimensionsFareRepo;

    private DimensionsFare testFare = new DimensionsFare(2L,5000,19999,20d);
    private DimensionsFare testMaxFare = new DimensionsFare(5L,1000000,1000000,80d);

    public static Stream<Arguments> testVolumeCase() {
        return Stream.of(
                Arguments.of(5000),
                Arguments.of(7500),
                Arguments.of(10000),
                Arguments.of(19000),
                Arguments.of(19999)
        );
    }

    @BeforeEach
    void setUp() throws SQLException, FileNotFoundException {
        ApplicationContext context = TestApplication.run();
        dimensionsFareRepo = context.getObject(DimensionsFareRepoImpl.class);
        connection = context.getObject(ConnectionPool.class).getConnection();
        connection.setAutoCommit(true);
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
    }

    @AfterEach
    void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @Test
    void findById() throws SQLException, FileNotFoundException {
        Optional<DimensionsFare> optional = dimensionsFareRepo.findById(testFare.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(testFare, optional.get());
    }

    @ParameterizedTest
    @MethodSource("testVolumeCase")
    void findFareByVolume(Integer volume) throws SQLException, FileNotFoundException {
        DimensionsFare fare = dimensionsFareRepo.findFareByVolume(volume);
        Assertions.assertNotNull(fare);
        Assertions.assertEquals(testFare, fare);
    }

    @Test
    void findMaxFare() throws SQLException, FileNotFoundException {

        DimensionsFare fare = dimensionsFareRepo.findMaxFare();
        Assertions.assertNotNull(fare);
        Assertions.assertEquals(testMaxFare, fare);
    }

    @Test
    void findAll() {

        List<DimensionsFare> all = dimensionsFareRepo.findAll();
        Assertions.assertFalse(all.isEmpty());
        Assertions.assertTrue(all.containsAll(Arrays.asList(testFare, testMaxFare)));
    }

    @Test
    void insert() throws SQLException, FileNotFoundException {
        DimensionsFare fare = new DimensionsFare(null, 1000001, 2000000, 120d);
        dimensionsFareRepo.insert(fare);
        Assertions.assertNotNull(fare.getId());
        Assertions.assertTrue(fetchAll().contains(fare));
    }

    @Test
    void update() throws SQLException, FileNotFoundException {

        testFare.setPrice(testFare.getPrice()+9.99);
        dimensionsFareRepo.update(testFare);
        Assertions.assertTrue(fetchAll().contains(testFare));

    }

    @Test
    void delete() throws SQLException, FileNotFoundException {
        dimensionsFareRepo.delete(testFare);
        Assertions.assertFalse(fetchAll().contains(testFare));
    }

    private List<DimensionsFare> fetchAll() throws SQLException, FileNotFoundException {
        return fetchDimensionsFare(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    private static List<DimensionsFare> fetchDimensionsFare(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new DimensionsFareParser(), ArrayList::new);
    }

}