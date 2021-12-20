package com.michal.szturc.model;

import com.michal.szturc.model.draft.OrderDraft;
import com.michal.szturc.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Michał Szturc
 */
public record Order(
        long id,
        String email,
        BigDecimal price,
        long productId,
        String promoCode,
        String orderNumberSuffix,
        Date createdAt
) {
    public Order(long id, OrderDraft draft) {
        this(id, draft.email(), draft.orderPrice(), draft.productId(), draft.promoCode(), draft.orderSuffix(), null);
    }


    public String orderNumber() {
        var orderNo = NumberUtils.padWithZeros(String.valueOf(this.id), 7);
        return "N" + orderNo + this.orderNumberSuffix;
    }

}
