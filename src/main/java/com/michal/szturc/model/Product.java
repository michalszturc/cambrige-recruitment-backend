package com.michal.szturc.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Michał Szturc
 */
public record Product(
        long id,
        String name,
        int minQuantity,
        int maxQuantity,
        BigDecimal unitPrice,
        Date createdAt
) {
}
