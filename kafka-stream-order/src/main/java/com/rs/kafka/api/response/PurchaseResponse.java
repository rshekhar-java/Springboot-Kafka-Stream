package com.rs.kafka.api.response;

/**
 * created by rs 5/11/2022.
 */
public class PurchaseResponse {
    private String purchaseNumber;

    public PurchaseResponse(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @Override
    public String toString() {
        return "PurchaseResponse{" +
                "purchaseNumber='" + purchaseNumber + '\'' +
                '}';
    }
}
