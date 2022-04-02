package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dao.utils.parse.impl.DeliveredBaggageParser;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;

class DeliveredBaggageRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivered_baggage/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivered_baggage/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivered_baggage/test-delete.sql";

    private DeliveredBaggageRepoImpl repo;

    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        ApplicationContext context = TestApplication.run();
        repo = context.getObject(DeliveredBaggageRepoImpl.class);
        ConnectionPool pool = context.getObject(ConnectionPool.class);
        connection = pool.getConnection();
        connection.setAutoCommit(true);
    }

    @AfterEach
    public void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @Test
    void findById() throws SQLException, FileNotFoundException {
        DeliveredBaggage baggage = TestDataUtils.getTestBaggage();
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);

        Optional<DeliveredBaggage> optional = repo.findById(baggage.getId());

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(baggage, optional.get());

    }

    @Test
    void findAll() throws SQLException, FileNotFoundException {
        DeliveredBaggage baggage = TestDataUtils.getTestBaggage();
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);

        List<DeliveredBaggage> all = repo.findAll();
        Assertions.assertTrue(all.size() >= 1);
        Assertions.assertTrue(all.contains(baggage));
    }

    @Test
    void insert() throws SQLException, FileNotFoundException {
        DeliveredBaggage baggage = TestDataUtils.getTestBaggage();
        baggage.setId(null);
        repo.insert(baggage);
        Assertions.assertTrue(Objects.nonNull(baggage.getId()));

        Assertions.assertTrue(fetchAll().contains(baggage));
    }

    @Test
    void update() throws SQLException, FileNotFoundException {
        DeliveredBaggage baggage = TestDataUtils.getTestBaggage();
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        baggage.setType(BaggageType.DANGEROUS);
        baggage.setDescription("dangerous baggage");
        baggage.setVolume(baggage.getVolume()+100);
        baggage.setWeight(baggage.getWeight()+10);

        baggage = repo.update(baggage);

        Assertions.assertTrue(fetchAll().contains(baggage));
    }

    @Test
    void delete() throws SQLException, FileNotFoundException {
        DeliveredBaggage baggage = TestDataUtils.getTestBaggage();
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        repo.delete(baggage);
        Assertions.assertFalse(fetchAll().contains(baggage));
    }

    private List<DeliveredBaggage> fetchAll() throws SQLException, FileNotFoundException {
        return fetchBaggage(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    private static List<DeliveredBaggage> fetchBaggage(ResultSet resultSet) throws SQLException {
        return RepoTestsUtils.fetchAll(resultSet, new DeliveredBaggageParser(), ArrayList::new);
    }

}