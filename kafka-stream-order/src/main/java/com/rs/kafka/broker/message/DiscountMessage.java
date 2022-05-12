package com.rs.kafka.broker.message;

/**
 * created by rs 5/11/2022.
 */
public class DiscountMessage {
    private String discountCode;

    private int discountPercentage;

    public DiscountMessage() {
    }

    public DiscountMessage(String discountCode, int discountPercentage) {
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
    }

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

    @Override
    public String toString() {
        return "DiscountMessage{" +
                "discountCode='" + discountCode + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
