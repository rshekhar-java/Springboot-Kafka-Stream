package com.rs.kafka.api.request;

/**
 * created by rs 5/11/2022.
 */
public class DiscountRequest {
    private String discountCode;

    private int discountPercentage;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
