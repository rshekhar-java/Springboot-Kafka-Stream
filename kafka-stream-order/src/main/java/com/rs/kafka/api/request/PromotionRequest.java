package com.rs.kafka.api.request;

/**
 * created by rs 5/11/2022.
 */
public class PromotionRequest {
    private String promotionCode;

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @Override
    public String toString() {
        return "PromotionRequest{" +
                "promotionCode='" + promotionCode + '\'' +
                '}';
    }
}
