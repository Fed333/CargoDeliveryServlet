package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.repo.RoleRepo;
import com.epam.cargo.entity.Role;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of RoleRepo interface.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class RoleRepoImpl implements RoleRepo {

    private static final String INSERT_INTO_USER_ROLE = "INSERT INTO user_role (user_id, roles) VALUES (?, ?)";
    private static final String SELECT_USER_ROLE_BY_USER_ID = "SELECT roles FROM user_role WHERE user_id = ?";
    private static final String DELETE_USER_ROLE_BY_USER_ID = "DELETE FROM user_role WHERE user_id = ?";

    private static final String ROLES_COLUMN = "roles";

    @Inject
    private ConnectionPool pool;

    @SuppressWarnings("unused")
    public RoleRepoImpl() {
    }

    public RoleRepoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void assignRoles(Collection<Role> roles, User user) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement pr = connection.prepareStatement(INSERT_INTO_USER_ROLE);
            for (Role role : roles) {
                pr.setLong(1, user.getId());
                pr.setString(2, role.name());

                pr.addBatch();
                pr.clearParameters();
            }
            pr.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(connection)) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void removeAllRoles(User user) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_ROLE_BY_USER_ID);
            statement.setLong(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(connection)) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Set<Role> getUserRoles(User user) {
        Set<Role> roles = new HashSet<>();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_ROLE_BY_USER_ID);
            ps.setLong(1, user.getId());
            ResultSet result = ps.executeQuery();
            while (result.next()){
                Role role = Role.valueOf(result.getString(ROLES_COLUMN));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch user roles.", e);
        } finally {
            if (Objects.nonNull(connection)){
                pool.releaseConnection(connection);
            }
        }
        return roles;
    }

}
