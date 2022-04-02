package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dao.utils.parse.impl.DeliveryApplicationParser;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;

class DeliveryApplicationRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivery_applications/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivery_applications/test-insert.sql";
    private static final String INSERT_BEFORE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivery_applications/insert-before.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/delivery_applications/test-delete.sql";

    private DeliveryApplicationRepoImpl applicationRepo;

    private Connection connection;

    public static Stream<Arguments> testDeliveryApplicationCase() {
        return Stream.of(
                Arguments.of(TestDataUtils.getTestApplication())
        );
    }

    @BeforeEach
    public void setUp() throws SQLException {
        ApplicationContext context = TestApplication.run();
        applicationRepo = context.getObject(DeliveryApplicationRepoImpl.class);
        ConnectionPool pool = context.getObject(ConnectionPool.class);
        connection = pool.getConnection();
        connection.setAutoCommit(true);

    }

    @AfterEach
    public void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void findById(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        Optional<DeliveryApplication> optional = applicationRepo.findById(application.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(application, optional.get());
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void findAll(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        List<DeliveryApplication> all = applicationRepo.findAll();
        Assertions.assertFalse(all.isEmpty());
        Assertions.assertTrue(all.contains(application));
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void findAllByUserId(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
        List<DeliveryApplication> usersApplications = applicationRepo.findAllByUserId(application.getCustomer().getId());
        Assertions.assertFalse(usersApplications.isEmpty());
        Assertions.assertTrue(usersApplications.contains(application));
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void insert(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, INSERT_BEFORE_SQL_SCRIPT_PATH);

        applicationRepo.insert(application);
        Long id = application.getId();
        Assertions.assertNotNull(id);
        Assertions.assertTrue(fetchAll().contains(application));
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void update(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);

        application.setPrice(application.getPrice() + 100);
        application.setSendingDate(LocalDate.now().plusDays(10));
        application.setReceivingDate(LocalDate.now().plusDays(20));

        applicationRepo.update(application);
        Assertions.assertTrue(fetchAll().contains(application));
    }

    @ParameterizedTest
    @MethodSource("testDeliveryApplicationCase")
    void delete(DeliveryApplication application) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);

        applicationRepo.delete(application);
        Assertions.assertFalse(fetchAll().contains(application));
    }


    private List<DeliveryApplication> fetchAll() throws SQLException, FileNotFoundException {
        List<DeliveryApplication> all = RepoTestsUtils.fetchAll(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH), new DeliveryApplicationParser(), ArrayList::new);
        for (DeliveryApplication a : all) {
            a.getCustomer().setRoles(RepoTestsUtils.fetchUserRoles(connection, a.getCustomer().getId()));
        }
        return all;
    }
}