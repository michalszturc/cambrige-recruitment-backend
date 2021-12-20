package com.michal.szturc.service;

import com.michal.szturc.model.draft.OrderDraft;
import com.michal.szturc.model.draft.PromotionDraft;
import com.michal.szturc.model.requests.OrderRequest;
import com.michal.szturc.model.Product;
import com.michal.szturc.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Michał Szturc
 */
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductsService productsService;
    private final PromoCodesService promoCodesService;

    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, ProductsService productsService,
                         PromoCodesService promoCodesService) {
        this.ordersRepository = ordersRepository;
        this.productsService = productsService;
        this.promoCodesService = promoCodesService;
    }

    public OrderDraft prepareOrderDrat(OrderRequest orderRequest) {
        var product = productsService.getProductById(orderRequest.getProductId());
        if (orderRequest.getRequestQuantity() > product.maxQuantity()
                || orderRequest.getRequestQuantity() < product.minQuantity()) {
            log.error("Request quantity for product with id {} is out of limit", product.id());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request quantity is out of limit");
        }
        if (orderRequest.getEmail() == null || !orderRequest.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
        var promotionDraft = this.promoCodesService.findPromotion(orderRequest.getPromotionCode());
        var orderPrice = orderPriceCalculator(orderRequest, product, promotionDraft);

        return new OrderDraft(product, orderRequest, promotionDraft, orderPrice);
    }

    public String makeOrder(OrderRequest orderRequest) {
        var orderDraft = prepareOrderDrat(orderRequest);
        var newOrder = ordersRepository.save(orderDraft);
        log.info("Order with id {} was successfully created", newOrder.id());

        return newOrder.orderNumber();
    }

    public BigDecimal orderPriceCalculator(OrderRequest orderRequest, Product product, PromotionDraft promotionDraft) {
        var price = product.unitPrice().multiply(BigDecimal.valueOf(orderRequest.getRequestQuantity()));
        var discountAmount = price.multiply(promotionDraft != null ? promotionDraft.discountPercent() :
                BigDecimal.ZERO);

        return (price.subtract(discountAmount)).setScale(2, RoundingMode.HALF_UP);
    }

}
