package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.repo.AddressRepo;
import com.epam.cargo.dao.repo.RoleRepo;
import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.UserRepoImpl.UserColumns.*;

/**
 * Implementation of UserRepo interface. <br>
 * Writes reads columns of the object but doesn't operate relative objects referenced by foreign key.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean. <br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class UserRepoImpl implements UserRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String INSERT_INTO = "INSERT INTO users (name, surname, login, password, phone, email, cash, address_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, phone = ?, email = ?, cash = ?, address_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    @Inject
    private AddressRepo addressRepo;

    @Inject
    private RoleRepo roleRepo;

    private UserDaoPersist userDaoPersist;

    @SuppressWarnings("unused")
    public UserRepoImpl() {
    }

    public UserRepoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @PostConstruct
    private void init(){
        userDaoPersist = new UserDaoPersist(pool);
    }

    @Override
    public User insert(User user) {
        user = userDaoPersist.create(INSERT_INTO, user);
        assignRoles(user);
        return user;
    }

    @Override
    public User update(User user) {
        removeAllRoles(user);
        assignRoles(user);
        return userDaoPersist.update(UPDATE_BY_ID, user);
    }

    @Override
    public void delete(User user) {
        removeAllRoles(user);
        userDaoPersist.delete(DELETE_BY_ID, user);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = userDaoPersist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id)).orElse(null);
        fetchRoles(user);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDaoPersist.findAll(SELECT_ALL);
        users.forEach(this::fetchRoles);
        return users;
    }

    @Override
    public User findByLogin(String login) {
        User user = userDaoPersist.findBy(SELECT_BY_LOGIN, ps -> ps.setString(1, login)).orElse(null);
        fetchRoles(user);
        return user;
    }

    private void assignRoles(User user) {
        if (Objects.nonNull(user.getRoles())){
            roleRepo.assignRoles(user.getRoles(), user);
        }
    }

    private void removeAllRoles(User o) {
        if (Objects.nonNull(o.getRoles())){
            roleRepo.removeAllRoles(o);
        }
    }

    enum UserColumns {
        ID("id"), NAME("name"), SURNAME("surname"), LOGIN("login"),
        PASSWORD("password"), PHONE("phone"), EMAIL("email"), CASH("cash"),
        ADDRESS_ID("address_id");

        private final String column;

        UserColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

    private void fetchRoles(User user) {
        if (Objects.nonNull(user)){
            user.setRoles(roleRepo.getUserRoles(user));
        }
    }

    private class UserDaoPersist extends DaoPersist<User, Long> {

        public UserDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        /**
         * Parses only columns.<br>
         * Relations objects are not considered.<br>
         * @since 1.0
         * */
        @Override
        public User parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.getColumn());
            String name = result.getString(NAME.getColumn());
            String surname = result.getString(SURNAME.getColumn());
            String login = result.getString(LOGIN.getColumn());
            String password = result.getString(PASSWORD.getColumn());
            BigDecimal cash = result.getBigDecimal(CASH.getColumn());
            String phone = result.getString(PHONE.getColumn());
            String email = result.getString(EMAIL.getColumn());
            long addressId = result.getLong(ADDRESS_ID.getColumn());
            Address address = addressRepo.findById(addressId).orElse(null);

            return new User(id, name, surname, login, password, phone, email, cash, address, null, null, null);
        }


        @Override
        public void transferObjectToInsert(User o, PreparedStatement statement) throws SQLException {
            setUserColumns(o, statement);
        }

        @Override
        public void transferObjectToUpdate(User o, PreparedStatement statement) throws SQLException {
            setUserColumns(o, statement);
            statement.setLong(9, o.getId());
        }

        private void setUserColumns(User o, PreparedStatement statement) throws SQLException {
            statement.setString(1, o.getName());
            statement.setString(2, o.getSurname());
            statement.setString(3, o.getLogin());
            statement.setString(4, o.getPassword());
            statement.setString(5, o.getPhone());
            statement.setString(6, o.getEmail());
            statement.setBigDecimal(7, o.getCash());
            statement.setObject(8, Optional.ofNullable(o.getAddress()).map(Address::getId).orElse(null));
        }
    }
}
