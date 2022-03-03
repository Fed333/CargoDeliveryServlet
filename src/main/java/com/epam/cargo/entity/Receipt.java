package com.epam.cargo.entity;

import java.time.LocalDate;

public interface Receipt {
    Double getTotalPrice();
    LocalDate getFormationDate();
}
