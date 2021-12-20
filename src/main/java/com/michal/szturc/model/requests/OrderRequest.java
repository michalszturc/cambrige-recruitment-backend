package com.michal.szturc.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * @author Michał Szturc
 */
public class OrderRequest {

    @JsonProperty("productId")
    private long productId;

    @JsonProperty(required = false)
    private String promotionCode;

    private int requestQuantity;

    private String email;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
