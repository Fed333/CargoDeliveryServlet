package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.DirectionDelivery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectionDeliveryParser implements ObjectParser<DirectionDelivery> {

    private final CityParser senderCityParser;

    private final CityParser receiverCityParser;

    private final DirectionDeliveryColumns columns;

    public DirectionDeliveryParser(CityParser senderCityParser, CityParser receiverCityParser, DirectionDeliveryColumns columns) {
        this.senderCityParser = senderCityParser;
        this.receiverCityParser = receiverCityParser;
        this.columns = columns;
    }

    public DirectionDeliveryParser() {
        this(
                new CityParser(new SenderCityColumns()),
                new CityParser(new ReceiverCityColumns()),
                new DirectionDeliveryColumns()
        );
    }

    @Override
    public DirectionDelivery parse(ResultSet r) throws SQLException {
        return new DirectionDelivery(
                r.getLong(columns.id()),
                senderCityParser.parse(r),
                receiverCityParser.parse(r),
                r.getDouble(columns.distance())
        );
    }

    public static class DirectionDeliveryColumns {
        public String id(){
            return "distance_id";
        }
        public String distance(){
            return "distance";
        }
    }

    private static class SenderCityColumns extends CityParser.CityColumns {
        @Override
        public String id() {
            return "sender_city_id";
        }

        @Override
        public String name() {
            return "sender_city_name";
        }

        @Override
        public String zipcode() {
            return "sender_city_zipcode";
        }
    }

    private static class ReceiverCityColumns extends CityParser.CityColumns {

        @Override
        public String id() {
            return "receiver_city_id";
        }

        @Override
        public String name() {
            return "receiver_city_name";
        }

        @Override
        public String zipcode() {
            return "receiver_city_zipcode";
        }
    }
}
