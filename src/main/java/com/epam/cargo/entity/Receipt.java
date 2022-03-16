package com.epam.cargo.entity;

import java.time.LocalDate;

/**
 * Interface to mark all Receipts classes.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Receipt {
    Double getTotalPrice();
    LocalDate getFormationDate();
}
