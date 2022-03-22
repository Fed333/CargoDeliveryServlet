package com.epam.cargo.dao.repo.impl;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.dao.persist.DaoPersist;
import com.epam.cargo.dao.persist.OrderColumnRecognizer;
import com.epam.cargo.dao.persist.PageableQueryBuilder;
import com.epam.cargo.dao.repo.DeliveryApplicationRepo;
import com.epam.cargo.dao.repo.DeliveryReceiptRepo;
import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.page.impl.PageImpl;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.cargo.dao.repo.impl.DeliveryReceiptRepoImpl.DeliveryReceiptColumns.*;

/**
 * Implementation of {@link DeliveryReceiptRepo} interface.<br>
 * Reads relative by foreign key data objects, writes all column but doesn't update those states during writing.<br>
 * Part of infrastructure's ApplicationContext. Singleton plain JavaBean.<br>
 * Based on PostgreSQL database.
 * @author Roman Kovalchuk
 * @version 1.1
 * */
@Singleton(type = Singleton.Type.LAZY)
public class DeliveryReceiptRepoImpl implements DeliveryReceiptRepo {

    private static final String SELECT_BY_ID = "SELECT * FROM delivery_receipts WHERE id = ?";
    private static final String SELECT_BY_APPLICATION_ID = "SELECT * FROM delivery_receipts WHERE application_id = ?";
    private static final String SELECT_ALL_BY_CUSTOMER_USER_ID = "SELECT * FROM delivery_receipts WHERE customer_user_id = ?";
    private static final String SELECT_COUNT_BY_CUSTOMER_USER_ID = "SELECT COUNT(*) FROM delivery_receipts WHERE customer_user_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM delivery_receipts";
    private static final String INSERT_INTO = "INSERT INTO delivery_receipts (application_id, customer_user_id, manager_user_id, total_price, formation_date, paid) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE delivery_receipts SET application_id = ?, customer_user_id = ?, manager_user_id = ?, total_price = ?, formation_date = ?, paid = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM delivery_receipts WHERE id = ?";

    @Inject
    private ConnectionPool pool;

    @Inject
    private UserRepo userRepo;

    @Inject
    private DeliveryApplicationRepo applicationRepo;

    private DeliveryReceiptDaoPersist persist;

    private final PageableQueryBuilder pageableQueryBuilder = new PageableQueryBuilder(
            OrderColumnRecognizer.of(
                    "id", ID.getColumn(),
                    "totalPrice", TOTAL_PRICE.getColumn(),
                    "formationDate", FORMATION_DATE.getColumn(),
                    "paid", PAID.getColumn()
            )
    );

    @SuppressWarnings("unused")
    public DeliveryReceiptRepoImpl() {
    }

    public DeliveryReceiptRepoImpl(ConnectionPool pool) {
        this.pool = pool;
        init();
    }

    @PostConstruct
    private void init(){
        persist = new DeliveryReceiptDaoPersist(pool);
    }

    @Override
    public Optional<DeliveryReceipt> findById(Long id) {
        Optional<DeliveryReceipt> optional = persist.findBy(SELECT_BY_ID, ps -> ps.setLong(1, id));
        optional.ifPresent(this::fetchEager);
        return optional;
    }

    @Override
    public List<DeliveryReceipt> findAll() {
        List<DeliveryReceipt> all = persist.findAll(SELECT_ALL);
        all.forEach(this::fetchEager);
        return all;
    }

    @Override
    public DeliveryReceipt insert(DeliveryReceipt o) {
        return persist.create(INSERT_INTO, o);
    }

    @Override
    public DeliveryReceipt update(DeliveryReceipt o) {
        return persist.update(UPDATE_BY_ID, o);
    }

    @Override
    public void delete(DeliveryReceipt o) {
        persist.delete(DELETE_BY_ID, o);
    }

    @Override
    public Optional<DeliveryReceipt> findByApplicationId(Long id) {
        Optional<DeliveryReceipt> optional = persist.findBy(SELECT_BY_APPLICATION_ID, ps -> ps.setLong(1, id));
        optional.ifPresent(this::fetchEager);
        return optional;
    }

    @Override
    public List<DeliveryReceipt> findAllByCustomerId(Long id) {
        List<DeliveryReceipt> customerApplications = persist.findAllBy(SELECT_ALL_BY_CUSTOMER_USER_ID, ps -> ps.setLong(1, id));
        customerApplications.forEach(this::fetchEager);
        return customerApplications;
    }

    @Override
    public Page<DeliveryReceipt> findAllByCustomerId(Long id, Pageable pageable) {
        List<DeliveryReceipt> receipts = persist.findAllBy(pageableQueryBuilder.buildPageQuery(SELECT_ALL_BY_CUSTOMER_USER_ID, pageable), ps -> ps.setLong(1, id));
        receipts.forEach(this::fetchEager);
        int total = persist.countBy(SELECT_COUNT_BY_CUSTOMER_USER_ID, ps -> ps.setLong(1, id));
        return new PageImpl<>(receipts, pageable, total);
    }

    private void fetchEager(DeliveryReceipt r) {
        r.setApplication(applicationRepo.findById(r.getApplication().getId()).orElseThrow());
        r.setCustomer(userRepo.findById(r.getCustomer().getId()).orElseThrow());
        r.setManager(userRepo.findById(r.getManager().getId()).orElseThrow());
    }

    private static class DeliveryReceiptDaoPersist extends DaoPersist<DeliveryReceipt, Long> {

        public DeliveryReceiptDaoPersist(ConnectionPool pool) {
            super(pool);
        }

        @Override
        public DeliveryReceipt parseObjectFrom(ResultSet result) throws SQLException {
            Long id = result.getLong(ID.getColumn());
            Long applicationId = result.getLong(APPLICATION_ID.getColumn());
            Long customerId = result.getLong(CUSTOMER_ID.getColumn());
            Long managerId = result.getLong(MANAGER_ID.getColumn());
            Double totalPrice = result.getDouble(TOTAL_PRICE.getColumn());
            LocalDate formationDate = result.getObject(FORMATION_DATE.getColumn(), LocalDate.class);
            Boolean paid = result.getBoolean(PAID.getColumn());

            return new DeliveryReceipt(
                    id,
                    new DeliveryApplication(applicationId),
                    new User(customerId),
                    new User(managerId),
                    totalPrice,
                    formationDate,
                    paid
            );
        }

        @Override
        public void transferObjectToInsert(DeliveryReceipt o, PreparedStatement statement) throws SQLException {
            setDeliveryReceiptData(o, statement);
        }

        @Override
        public void transferObjectToUpdate(DeliveryReceipt o, PreparedStatement statement) throws SQLException {
            setDeliveryReceiptData(o, statement);
            statement.setLong(6, o.getId());
        }

        private void setDeliveryReceiptData(DeliveryReceipt o, PreparedStatement statement) throws SQLException {
            statement.setLong(1, o.getApplication().getId());
            statement.setLong(2, o.getCustomer().getId());
            statement.setLong(3, o.getManager().getId());
            statement.setDouble(4, o.getTotalPrice());
            statement.setObject(5, o.getFormationDate());
            statement.setBoolean(6, o.getPaid());
        }

    }

    enum DeliveryReceiptColumns {
        ID("id"),
        APPLICATION_ID("application_id"),
        CUSTOMER_ID("customer_user_id"),
        MANAGER_ID("manager_user_id"),
        TOTAL_PRICE("total_price"),
        FORMATION_DATE("formation_date"),
        PAID("paid");

        private final String column;

        DeliveryReceiptColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

}