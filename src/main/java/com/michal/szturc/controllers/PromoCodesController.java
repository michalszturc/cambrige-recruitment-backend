package com.michal.szturc.controllers;

import com.michal.szturc.model.draft.PromotionDraft;
import com.michal.szturc.model.requests.ValidationRequest;
import com.michal.szturc.model.response.PromotionResponse;
import com.michal.szturc.service.PromoCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Michał Szturc
 */
@RestController
@RequestMapping("/promo-codes")
public class PromoCodesController {

    private final PromoCodesService promoCodesService;

    @Autowired
    public PromoCodesController(PromoCodesService promoCodesService) {
        this.promoCodesService = promoCodesService;
    }

    @PostMapping
    public PromotionResponse getValidPromo(@RequestBody ValidationRequest validateRequest) {
        var promotionDraft = promoCodesService.findPromotion(validateRequest.getPromoCode());
        if (promotionDraft == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promo not found");
        }
        return new PromotionResponse(promotionDraft.discountPercent());
    }
}
