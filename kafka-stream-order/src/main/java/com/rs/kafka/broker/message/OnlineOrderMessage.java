package com.rs.kafka.broker.message;

import com.fasterxml.jackson.annotation.*;

import java.time.*;

/**
 * created by rs 5/11/2022.
 */
public class OnlineOrderMessage {

    private String onlineOrderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;

    private int totalAmount;
    private String username;

    public String getOnlineOrderNumber() {
        return onlineOrderNumber;
    }

    public void setOnlineOrderNumber(String onlineOrderNumber) {
        this.onlineOrderNumber = onlineOrderNumber;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "OnlineOrderMessage{" +
                "onlineOrderNumber='" + onlineOrderNumber + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", totalAmount=" + totalAmount +
                ", username='" + username + '\'' +
                '}';
    }
}
