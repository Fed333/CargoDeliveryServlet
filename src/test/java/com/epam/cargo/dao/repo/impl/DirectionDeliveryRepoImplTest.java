package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.parse.impl.DirectionDeliveryParser;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.fed333.servletboot.context.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;


class DirectionDeliveryRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/directions/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/directions/test-insert.sql";
    private static final String INSERT_BEFORE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/directions/insert-before.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/directions/test-delete.sql";

    private DirectionDeliveryRepoImpl directionDeliveryRepo;
    private CityRepoImpl cityRepo;

    private Connection connection;

    public static Stream<Arguments> testDirectionCase() {
        return Stream.of(
                Arguments.of(new DirectionDelivery(65L, new City(1L, "Vinnytsia", "21012"), new City(2L, "Uman", "20301"), 160.1d))
        );
    }

    @BeforeEach
    public void setUp() throws SQLException {
        ApplicationContext context = TestApplication.run();
        directionDeliveryRepo = context.getObject(DirectionDeliveryRepoImpl.class);
        cityRepo = context.getObject(CityRepoImpl.class);
        connection = context.getObject(ConnectionPool.class).getConnection();
        connection.setAutoCommit(true);
    }

    @AfterEach
    public void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void findById(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        Optional<DirectionDelivery> found = directionDeliveryRepo.findById(directionDelivery.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(directionDelivery, found.orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void findAll(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        List<DirectionDelivery> list = directionDeliveryRepo.findAll();
        Assertions.assertTrue(list.size() > 0);
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void findBySenderCityAndReceiverCity(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);

        DirectionDelivery found = directionDeliveryRepo.findBySenderCityAndReceiverCity(directionDelivery.getSenderCity(), directionDelivery.getReceiverCity());
        Assertions.assertTrue(Objects.nonNull(found));
        Assertions.assertEquals(directionDelivery, found);
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void insert(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, INSERT_BEFORE_SQL_SCRIPT_PATH);
        directionDelivery.setId(null);
        directionDeliveryRepo.insert(directionDelivery);
        Assertions.assertTrue(Objects.nonNull(directionDelivery.getId()));
        Assertions.assertTrue(fetchAll().contains(directionDelivery));
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void update(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        directionDelivery.setDistance(directionDelivery.getDistance() + 10);
        directionDeliveryRepo.update(directionDelivery);
        Assertions.assertTrue(fetchAll().contains(directionDelivery));
    }

    @ParameterizedTest
    @MethodSource("testDirectionCase")
    void delete(DirectionDelivery directionDelivery) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        directionDeliveryRepo.delete(directionDelivery);
        Assertions.assertFalse(fetchAll().contains(directionDelivery));
    }

    private List<DirectionDelivery> fetchAll() throws SQLException, FileNotFoundException {
        return RepoTestsUtils.fetchAll(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH), new DirectionDeliveryParser(), ArrayList::new);
    }
}