package com.michal.szturc.model.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.szturc.model.Product;
import com.michal.szturc.model.requests.OrderRequest;
import com.michal.szturc.utils.NumberUtils;

import java.math.BigDecimal;

/**
 * @author Michał Szturc
 */
public record OrderDraft(
        String email,
        long productId,
        String promoCode,
        BigDecimal orderPrice,
        @JsonIgnore
        String orderSuffix
) {
    public OrderDraft(Product product, OrderRequest orderRequest, PromotionDraft promotionDraft, BigDecimal orderPrice) {
        this(orderRequest.getEmail(), product.id(), promotionDraft != null ? promotionDraft.code() : null,
                orderPrice, generateOrderSuffix());
    }

    private static String generateOrderSuffix() {
        var randomSuffix = String.valueOf((int) (Math.random() * 999 + 1));
        return NumberUtils.padWithZeros(randomSuffix, 3);
    }
}
