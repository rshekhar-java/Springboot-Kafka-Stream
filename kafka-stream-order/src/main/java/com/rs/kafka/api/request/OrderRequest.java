package com.rs.kafka.api.request;

import java.util.*;

/**
 * created by rs 5/11/2022.
 */
public class OrderRequest {
    private String creditCardNumber;
    private List<OrderItemRequest> items;
    private String orderLocation;

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", items=" + items +
                ", orderLocation='" + orderLocation + '\'' +
                '}';
    }
}
