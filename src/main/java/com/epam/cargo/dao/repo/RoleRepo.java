package com.epam.cargo.dao.repo;

import com.epam.cargo.entity.Role;
import com.epam.cargo.entity.User;

import java.util.Collection;
import java.util.Set;

/**
 * Repository to work with user's Role objects.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface RoleRepo {

    void assignRoles(Collection<Role> roles, User user);

    void removeAllRoles(User user);

    Set<Role> getUserRoles(User user);

}
