package com.epam.cargo.dao.utils.parse.impl;

import com.epam.cargo.dao.utils.parse.ObjectParser;
import com.epam.cargo.entity.DeliveryApplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeliveryApplicationParser implements ObjectParser<DeliveryApplication> {

    private final DeliveryApplicationParser.DeliveryApplicationColumn columns;

    private final UserParser userParser;

    private final DeliveredBaggageParser baggageParser;

    private final AddressParser senderAddressParser;

    private final AddressParser receiverAddressParser;


    public DeliveryApplicationParser(DeliveryApplicationParser.DeliveryApplicationColumn columns, DeliveredBaggageParser baggageParser, AddressParser senderAddressParser, AddressParser receiverAddressParser, UserParser userParser) {
        this.columns = columns;
        this.baggageParser = baggageParser;
        this.senderAddressParser = senderAddressParser;
        this.receiverAddressParser = receiverAddressParser;
        this.userParser = userParser;
    }

    public DeliveryApplicationParser() {
        this(
                new DeliveryApplicationColumn(),
                new DeliveredBaggageParser(new DeliveredBaggageColumns()),
                new AddressParser(new CityParser(new SenderCityColumns()), new SenderAddressColumns()),
                new AddressParser(new CityParser(new ReceiverCityColumns()), new ReceiverAddressColumns()),
                new UserParser(new AddressParser(new CityParser(new UserCityColumns()), new UserAddressColumns()), new UserColumns())
        );
    }

    @Override
    public DeliveryApplication parse(ResultSet r) throws SQLException {
        return new DeliveryApplication(
                r.getLong(columns.id()),
                userParser.parse(r),
                senderAddressParser.parse(r),
                receiverAddressParser.parse(r),
                baggageParser.parse(r),
                r.getObject(columns.sendingDate(), LocalDate.class),
                r.getObject(columns.receivingDate(), LocalDate.class),
                DeliveryApplication.State.values()[r.getInt(columns.state())],
                r.getDouble(columns.price())
        );
    }

    public static class DeliveryApplicationColumn {
        public String id(){
            return "application_id";
        }
        public String receivingDate(){
            return "receiving_date";
        }
        public String sendingDate(){
            return "sending_date";
        }
        public String state(){
            return "state";
        }
        public String price(){
            return "price";
        }
    }

    private static class DeliveredBaggageColumns extends DeliveredBaggageParser.DeliveredBaggageColumns {
        @Override
        public String id() {
            return "baggage_id";
        }

    }

    private static class SenderAddressColumns extends AddressParser.AddressColumns {
        @Override
        public String id() {
            return "sender_address_id";
        }

        @Override
        public String street() {
            return "sender_street";
        }

        @Override
        public String houseNumber() {
            return "sender_house_number";
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
            return "sender_zipcode";
        }
    }

    private static class ReceiverAddressColumns extends AddressParser.AddressColumns {
        @Override
        public String id() {
            return "receiver_address_id";
        }

        @Override
        public String street() {
            return "receiver_street";
        }

        @Override
        public String houseNumber() {
            return "receiver_house_number";
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
            return "receiver_zipcode";
        }
    }

    private static class UserColumns extends UserParser.UserColumns {
        @Override
        public String id() {
            return "user_id";
        }

        @Override
        public String name() {
            return "user_name";
        }

        @Override
        public String surname() {
            return "user_surname";
        }
    }

    private static class UserAddressColumns extends AddressParser.AddressColumns {
        @Override
        public String id() {
            return "user_address_id";
        }

        @Override
        public String street() {
            return "user_street";
        }

        @Override
        public String houseNumber() {
            return "user_house_number";
        }
    }

    private static class UserCityColumns extends CityParser.CityColumns {
        @Override
        public String id() {
            return "user_city_id";
        }

        @Override
        public String name() {
            return "user_city_name";
        }

        @Override
        public String zipcode() {
            return "user_city_zipcode";
        }
    }
}