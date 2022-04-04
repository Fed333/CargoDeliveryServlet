package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.parse.impl.DistanceFareParser;
import com.epam.cargo.entity.DistanceFare;
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

class DistanceFareRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/distance_fares/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/distance_fares/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/distance_fares/test-delete.sql";

    private Connection connection;

    private DistanceFareRepoImpl distanceFareRepo;

    private final DistanceFare testFare = new DistanceFare(2L, 20, 49, 50d);
    private final DistanceFare testMaxFare = new DistanceFare(5L, 1000, 1000, 200d);

    public static Stream<Arguments> testDistanceCase() {
        return Stream.of(
                Arguments.of(20),
                Arguments.of(25),
                Arguments.of(30),
                Arguments.of(49)
        );
    }

    @BeforeEach
    public void setUp() throws SQLException, FileNotFoundException {
        ApplicationContext context = TestApplication.run();
        distanceFareRepo = context.getObject(DistanceFareRepoImpl.class);
        connection = context.getObject(ConnectionPool.class).getConnection();
        connection.setAutoCommit(true);
    }

    @AfterEach
    void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @Test
    void findById() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        Optional<DistanceFare> optional = distanceFareRepo.findById(testFare.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(testFare, optional.get());
    }

    @ParameterizedTest
    @MethodSource("testDistanceCase")
    void findFareByDistance(Integer distance) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        DistanceFare fare = distanceFareRepo.findFareByDistance(distance);
        Assertions.assertNotNull(fare);
        Assertions.assertEquals(testFare, fare);
    }

    @Test
    void findMaxFare() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        DistanceFare fare = distanceFareRepo.findMaxFare();
        Assertions.assertEquals(testMaxFare, fare);
    }

    @Test
    void findAll() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        List<DistanceFare> all = distanceFareRepo.findAll();
        Assertions.assertFalse(all.isEmpty());
        Assertions.assertTrue(all.containsAll(Arrays.asList(testFare, testMaxFare)));
    }

    @Test
    void insert() throws SQLException, FileNotFoundException {
        DistanceFare fare = new DistanceFare(null, 1001, 2000, 300d);
        distanceFareRepo.insert(fare);
        Assertions.assertNotNull(fare.getId());
        Assertions.assertTrue(fetchAll().contains(fare));
    }

    @Test
    void update() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        testFare.setPrice(testFare.getPrice()+10);
        distanceFareRepo.update(testFare);
        Assertions.assertTrue(fetchAll().contains(testFare));
    }

    @Test
    void delete() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        distanceFareRepo.delete(testFare);
        Assertions.assertFalse(fetchAll().contains(testFare));
    }

    private List<DistanceFare> fetchAll() throws SQLException, FileNotFoundException {
        return fetchDistanceFare(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    private static List<DistanceFare> fetchDistanceFare(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new DistanceFareParser(), ArrayList::new);
    }

}