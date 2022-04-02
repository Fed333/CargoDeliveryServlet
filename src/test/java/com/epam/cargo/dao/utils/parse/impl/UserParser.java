package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserParser implements ObjectParser<User> {

    private final AddressParser addressParser;

    private final UserColumns columns;

    public UserParser(AddressParser addressParser, UserColumns columns) {
        this.addressParser = addressParser;
        this.columns = columns;
    }

    public UserParser() {
        this(new AddressParser(new CityParser(new UserCityColumns()), new UserAddressColumns()), new UserColumns());
    }

    @Override
    public User parse(ResultSet r) throws SQLException {
        return new User(
                r.getLong(columns.id()),
                r.getString(columns.name()),
                r.getString(columns.surname()),
                r.getString(columns.login()),
                r.getString(columns.password()),
                r.getString(columns.phone()),
                r.getString(columns.email()),
                r.getBigDecimal(columns.cash()),
                addressParser.parse(r),
                null,
                null,
                null
        );
    }

    public static class UserColumns {
        public String id(){
            return "user_id";
        }
        public String name(){
            return "user_name";
        }
        public String surname(){
            return "surname";
        }
        public String login(){
            return "login";
        }
        public String password(){
            return "password";
        }
        public String phone(){
            return "phone";
        }
        public String email(){
            return "email";
        }
        public String cash(){
            return "cash";
        }
        public String addressId(){
            return "address_id";
        }
    }

    public static class UserAddressColumns extends AddressParser.AddressColumns {
        @Override
        public String id() {
            return "address_id";
        }
    }

    public static class UserCityColumns extends CityParser.CityColumns {
        @Override
        public String id() {
            return "city_id";
        }

        @Override
        public String name() {
            return "city_name";
        }
    }
}
