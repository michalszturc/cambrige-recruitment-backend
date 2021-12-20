package com.michal.szturc.controllers;

import com.michal.szturc.model.draft.OrderDraft;
import com.michal.szturc.model.requests.OrderRequest;
import com.michal.szturc.model.response.MakeOrderResponse;
import com.michal.szturc.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michał Szturc
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/draft")
    public OrderDraft createOrderDraft(@RequestBody OrderRequest orderRequest) {
        return ordersService.prepareOrderDrat(orderRequest);
    }

    @PostMapping
    public MakeOrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return new MakeOrderResponse(ordersService.makeOrder(orderRequest));
    }
}
