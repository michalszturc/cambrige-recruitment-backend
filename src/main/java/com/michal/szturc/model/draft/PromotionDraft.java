package com.michal.szturc.model.draft;

import java.math.BigDecimal;

/**
 * @author Michał Szturc
 */
public record PromotionDraft(
        String code,
        BigDecimal discountPercent
) {
}
