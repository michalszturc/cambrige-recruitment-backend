package com.michal.szturc.service;

import com.michal.szturc.model.draft.PromotionDraft;
import com.michal.szturc.repository.OrdersRepository;
import com.michal.szturc.repository.PromoCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

/**
 * @author Michał Szturc
 */
@Service
public class PromoCodesService {

    private final PromoCodesRepository promoCodesRepository;
    private final OrdersRepository ordersRepository;

    private final static BigDecimal ONE_HUNDRED = new BigDecimal(100);

    @Autowired
    public PromoCodesService(PromoCodesRepository promoCodesRepository, OrdersRepository ordersRepository) {
        this.promoCodesRepository = promoCodesRepository;
        this.ordersRepository = ordersRepository;
    }

    private String validatePromoCode(String promoCode) {
        var validatedCode = this.promoCodesRepository.findPromoCodeByCode(promoCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo code not found"));
        if (this.ordersRepository.checkIfPromoCodeWasUsed(validatedCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Promo code used");
        }

        return validatedCode;
    }

    public PromotionDraft findPromotion(String promoCode) {
        if(promoCode == null) {
           return null;
        }
        var validPromoCode = validatePromoCode(promoCode);

        return new PromotionDraft(validPromoCode, BigDecimal.valueOf(validPromoCode.length()).divide(ONE_HUNDRED));

    }

}
