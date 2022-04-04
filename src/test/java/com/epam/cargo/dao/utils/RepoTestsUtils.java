package com.epam.cargo.dao.utils;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.dao.utils.parse.impl.*;
import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.*;

import java.sql.*;
import java.util.*;
import java.util.function.Supplier;

public class RepoTestsUtils {

    public static DirectionDeliveryFilterRequest createDirectionDeliveryFilterRequest(String senderCity, String receiverCity){
        DirectionDeliveryFilterRequest request = new DirectionDeliveryFilterRequest();
        request.setSenderCityName(senderCity);
        request.setReceiverCityName(receiverCity);
        return request;
    }

    public static Set<Role> fetchRoles(ResultSet resultSet) throws SQLException {
        return fetchAll(resultSet, new RoleParser(), HashSet::new);
    }

    public static <T, R extends Collection<T>> R fetchAll(ResultSet resultSet, ObjectParser<T> f, Supplier<R> collectionSupplier) throws SQLException {
        R collection = collectionSupplier.get();
        while (resultSet.next()){
            collection.add(f.parse(resultSet));
        }
        return collection;
    }

    public static Set<Role> fetchUserRoles(Connection connection, Long userId) throws SQLException {
        String query = "SELECT * FROM user_role WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, userId);
        return fetchRoles(ps.executeQuery());
    }

}
