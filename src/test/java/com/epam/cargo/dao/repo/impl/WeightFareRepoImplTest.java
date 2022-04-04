package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.parse.impl.WeightFareParser;
import com.epam.cargo.entity.WeightFare;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.junit.jupiter.api.*;
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

class WeightFareRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/weight_fares/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/weight_fares/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/weight_fares/test-delete.sql";

    private Connection connection;

    private WeightFareRepoImpl weightFareRepo;

    private final WeightFare testFare = new WeightFare( 3L, 10, 29,  60d);
    private final WeightFare testMaxFare = new WeightFare( 5L,  100, 100, 150d);

    public static Stream<Arguments> testWeightCase() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(15),
                Arguments.of(20),
                Arguments.of(29)
        );
    }

    @BeforeEach
    void setUp() throws SQLException {
        ApplicationContext context = TestApplication.run();
        weightFareRepo = context.getObject(WeightFareRepoImpl.class);
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
        Optional<WeightFare> optional = weightFareRepo.findById(testFare.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(testFare, optional.get());
    }

    @ParameterizedTest
    @MethodSource("testWeightCase")
    void findFareByWeight(Integer weight) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        WeightFare fare = weightFareRepo.findFareByWeight(weight);
        Assertions.assertNotNull(fare);
        Assertions.assertEquals(testFare, fare);
    }

    @Test
    void findMaxFare() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        WeightFare fare = weightFareRepo.findMaxFare();
        Assertions.assertEquals(testMaxFare, fare);
    }

    @Test
    void findAll() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        List<WeightFare> all = weightFareRepo.findAll();
        Assertions.assertFalse(all.isEmpty());
        Assertions.assertTrue(all.containsAll(Arrays.asList(testFare, testMaxFare)));
    }

    @Test
    void insert() throws SQLException, FileNotFoundException {
        WeightFare fare = new WeightFare(null, 101, 200, 250d);
        weightFareRepo.insert(fare);
        Assertions.assertNotNull(fare.getId());
        Assertions.assertTrue(fetchAll().contains(fare));
    }

    @Test
    @Timeout(100)
    void update() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        testFare.setPrice(testFare.getPrice()+19.99);
        weightFareRepo.update(testFare);
        Assertions.assertTrue(fetchAll().contains(testFare));
    }

    @Test
    void delete() throws SQLException, FileNotFoundException {
        weightFareRepo.delete(testFare);
        Assertions.assertFalse(fetchAll().contains(testFare));
    }

    private List<WeightFare> fetchAll() throws SQLException, FileNotFoundException {
        return fetchWeightFare(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    private static List<WeightFare> fetchWeightFare(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new WeightFareParser(), ArrayList::new);
    }

}