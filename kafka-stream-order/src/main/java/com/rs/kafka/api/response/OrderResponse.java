package com.rs.kafka.api.response;

/**
 * created by rs 5/11/2022.
 */
public class OrderResponse {
    private String orderNumber;

    public OrderResponse(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
