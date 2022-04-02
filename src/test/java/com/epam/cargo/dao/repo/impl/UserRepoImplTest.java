package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.test.infrastructure.application.TestApplication;
import com.epam.cargo.dao.utils.RepoTestsUtils;
import com.epam.cargo.dao.utils.TestDataUtils;
import com.epam.cargo.dao.utils.parse.impl.UserParser;
import com.epam.cargo.entity.Role;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSQLScript;
import static com.epam.cargo.dao.utils.ScriptExecutorUtils.executeSelectSQLScript;
import static com.epam.cargo.entity.Role.USER;

class UserRepoImplTest {

    private static final String SELECT_ALL_SQL_SCRIPT_PATH = "src/test/resources/db.sql/users/select-all.sql";
    private static final String TEST_INSERT_SQL_SCRIPT_PATH = "src/test/resources/db.sql/users/test-insert.sql";
    private static final String TEST_DELETE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/users/test-delete.sql";
    private static final String INSERT_BEFORE_SQL_SCRIPT_PATH = "src/test/resources/db.sql/users/insert-before.sql";

    private UserRepoImpl userRepo;

    private AddressRepoImpl addressRepo;

    private CityRepoImpl cityRepo;

    private Connection connection;

    public static Stream<Arguments> testUserCase() {
        return Stream.of(
          Arguments.of(TestDataUtils.getTestUser())
        );
    }

    @BeforeEach
    public void setUp() throws SQLException {
        ApplicationContext context = TestApplication.run();
        userRepo = context.getObject(UserRepoImpl.class);
        addressRepo = context.getObject(AddressRepoImpl.class);
        cityRepo = context.getObject(CityRepoImpl.class);
        connection = context.getObject(ConnectionPool.class).getConnection();
        connection.setAutoCommit(true);
    }

    @AfterEach
    public void tearDown() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_DELETE_SQL_SCRIPT_PATH);
    }

    @ParameterizedTest
    @MethodSource("testUserCase")
    void insert(User user) throws SQLException, FileNotFoundException {
        executeSQLScript(connection, INSERT_BEFORE_SQL_SCRIPT_PATH);
        userRepo.insert(user);
        Long id = user.getId();
        Assertions.assertNotNull(id);
        Assertions.assertEquals(user, fetchUserById(id));
    }

    @ParameterizedTest
    @MethodSource("testUserCase")
    void update(User user) throws SQLException, FileNotFoundException {
        insertUser();
        Long id = user.getId();
        User old = fetchUserById(id);
        String updatedName = "TestUserName2";
        user.setName(updatedName);
        Set<Role> updatedRole = Set.of(USER);
        user.setRoles(updatedRole);
        userRepo.update(user);
        User updated = fetchUserById(id);
        Assertions.assertNotEquals(old, updated);
        Assertions.assertEquals(updatedName, updated.getName());
        Assertions.assertEquals(updatedRole, updated.getRoles());
    }

    private User fetchUserById(Long id) throws SQLException, FileNotFoundException {
        return fetchAll().stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList()).get(0);
    }


    @ParameterizedTest
    @MethodSource("testUserCase")
    void delete(User user) throws SQLException, FileNotFoundException {
        insertUser();
        userRepo.delete(user);
        Assertions.assertFalse(fetchAll().contains(user));
    }

    @ParameterizedTest
    @MethodSource("testUserCase")
    void findById(User user) throws SQLException, FileNotFoundException {
        insertUser();
        Long id = user.getId();
        Assertions.assertTrue(userRepo.findById(id).isPresent());
        Assertions.assertEquals(user, userRepo.findById(id).orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("testUserCase")
    void findAll(User user) throws SQLException, FileNotFoundException {
        insertUser();
        List<User> users = userRepo.findAll();
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertTrue(users.contains(user));
    }

    @ParameterizedTest
    @MethodSource("testUserCase")
    void findByLogin(User user) throws SQLException, FileNotFoundException {
        insertUser();
        String login = user.getLogin();
        Assertions.assertNotNull(userRepo.findByLogin(login));
        Assertions.assertEquals(user, userRepo.findByLogin(login));
    }

    private List<User> fetchAll() throws SQLException, FileNotFoundException {
        return fetchUsers(executeSelectSQLScript(connection, SELECT_ALL_SQL_SCRIPT_PATH));
    }

    private List<User> fetchUsers(ResultSet resultSet) throws SQLException {
        List<User> users = RepoTestsUtils.fetchAll(resultSet, new UserParser(), ArrayList::new);
        for (User u : users) {
            u.setRoles(RepoTestsUtils.fetchUserRoles(connection, u.getId()));
        }
        return users;
    }

    private void insertUser() throws SQLException, FileNotFoundException {
        executeSQLScript(connection, TEST_INSERT_SQL_SCRIPT_PATH);
    }
}