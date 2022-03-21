package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.repo.AddressRepo;
import com.epam.cargo.dao.repo.DeliveredBaggageRepo;
import com.epam.cargo.dao.repo.DeliveryApplicationRepo;
import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.DeliveryApplicationRepoImpl.DeliveryApplicationColumns.*;

/**
 * Implementation of {@link DeliveryApplicationRepo} interface.<br>
 * Reads relative by foreign key data objects, writes all column but doesn't update those states during writing.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DeliveryApplicationRepoImpl implements DeliveryApplicationRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM delivery_applications WHERE id = ?";
    private static final String SELECT_ALL_BY_USER_ID = "SELECT * FROM delivery_applications WHERE user_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM delivery_applications";
    private static final String INSERT_INTO = "INSERT INTO delivery_applications (user_id, sender_address_id, receiver_address_id, baggage_id, sending_date, receiving_date, state, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE delivery_applications SET user_id = ?, sender_address_id = ?, receiver_address_id = ?, baggage_id = ?, sending_date = ?, receiving_date = ?, state = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM delivery_applications WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AddressRepo addressRepo;

    @Inject
    private DeliveredBaggageRepo baggageRepo;

    private DeliveryApplicationDaoPersist persist;

    @SuppressWarnings("unused")
    public DeliveryApplicationRepoImpl() {
    }

    public DeliveryApplicationRepoImpl(ConnectionPool pool){
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DeliveryApplicationDaoPersist(pool);
    }

    @Override
    public Optional<DeliveryApplication> findById(Long id) {
        Optional<DeliveryApplication> optional = persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
        optional.ifPresent(this::fetchEager);
        return optional;
    }

    @Override
    public List<DeliveryApplication> findAll() {
        List<DeliveryApplication> applications = persist.findAll(SELECT_ALL);
        applications.forEach(this::fetchEager);
        return applications;
    }

    @Override
    public List<DeliveryApplication> findAllByUserId(Long userId) {
        List<DeliveryApplication> applications = persist.findAllBy(SELECT_ALL_BY_USER_ID, ps -> ps.setLong(1, userId));
        applications.forEach(this::fetchEager);
        return applications;
    }

    @Override
    public DeliveryApplication insert(DeliveryApplication o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DeliveryApplication update(DeliveryApplication o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DeliveryApplication o) {
        persist.delete(DELETE_BY_ID, o);
    }

    private void fetchEager(DeliveryApplication application) {
        application.setCustomer(userRepo.findById(application.getCustomer().getId()).orElse(null));
        application.setSenderAddress(addressRepo.findById(application.getSenderAddress().getId()).orElse(null));
        application.setReceiverAddress(addressRepo.findById(application.getReceiverAddress().getId()).orElse(null));
        application.setDeliveredBaggage(baggageRepo.findById(application.getDeliveredBaggage().getId()).orElse(null));
    }

    private static class DeliveryApplicationDaoPersist extends DaoPersist<DeliveryApplication, Long> {

        public DeliveryApplicationDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DeliveryApplication parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.getColumn());
            Long userId = result.getLong(USER_ID.getColumn());
            Long senderAddressId = result.getLong(SENDER_ADDRESS_ID.getColumn());
            Long receiverAddressId = result.getLong(RECEIVER_ADDRESS_ID.getColumn());
            Long deliveredBaggageId = result.getLong(BAGGAGE_ID.getColumn());
            LocalDate sendingDate = result.getObject(SENDING_DATE.getColumn(), LocalDate.class);
            LocalDate receivingDate = result.getObject(RECEIVING_DATE.getColumn(), LocalDate.class);
            int ordinal = result.getInt(STATE.getColumn());
            DeliveryApplication.State state = DeliveryApplication.State.values()[ordinal];
            Double price = result.getDouble(PRICE.getColumn());

            return new DeliveryApplication(
                    id,
                    new User(userId),
                    new Address(senderAddressId),
                    new Address(receiverAddressId),
                    new DeliveredBaggage(deliveredBaggageId),
                    sendingDate,
                    receivingDate,
                    state,
                    price
            );
        }

        @Override
        public void transferObjectToInsert(DeliveryApplication o, PreparedStatement statement) throws SQLException {
            setDeliveryApplicationData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(DeliveryApplication o, PreparedStatement statement) throws SQLException {
            setDeliveryApplicationData(o, statement);
            statement.setLong(9, o.getId());
        }

        private void setDeliveryApplicationData(DeliveryApplication o, PreparedStatement statement) throws SQLException {
            statement.setLong(1, o.getCustomer().getId());
            statement.setLong(2, o.getSenderAddress().getId());
            statement.setLong(3, o.getReceiverAddress().getId());
            statement.setLong(4, o.getDeliveredBaggage().getId());
            statement.setObject(5, o.getSendingDate());
            statement.setObject(6, o.getReceivingDate());
            statement.setInt(7, o.getState().ordinal());
            statement.setDouble(8, o.getPrice());

        }
    }

    enum DeliveryApplicationColumns {

        ID("id"),
        USER_ID("user_id"),
        SENDER_ADDRESS_ID("sender_address_id"),
        RECEIVER_ADDRESS_ID("receiver_address_id"),
        BAGGAGE_ID("baggage_id"),
        SENDING_DATE("sending_date"),
        RECEIVING_DATE("receiving_date"),
        STATE("state"),
        PRICE("price");

        private final String column;

        DeliveryApplicationColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

}
