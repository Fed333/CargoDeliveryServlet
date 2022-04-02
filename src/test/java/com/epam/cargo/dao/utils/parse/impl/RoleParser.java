package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleParser implements ObjectParser<Role> {

    private final UserRoleColumns columns;

    public RoleParser(UserRoleColumns columns) {
        this.columns = columns;
    }

    public RoleParser() {
        this(new UserRoleColumns());
    }

    @Override
    public Role parse(ResultSet r) throws SQLException {
        return Role.valueOf(r.getString(columns.roles()));
    }

    public static class UserRoleColumns {
        public String userId(){
            return "user_id";
        }
        public String roles(){
            return "roles";
        }
    }
}
