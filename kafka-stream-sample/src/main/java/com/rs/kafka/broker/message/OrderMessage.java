package com.rs.kafka.broker.message;

import com.fasterxml.jackson.annotation.*;

import java.time.*;

/**
 * created by rs 5/13/2022.
 */
public class OrderMessage {

    private String creditCardNumber;

    private String itemName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;

    private String orderLocation;

    private String orderNumber;

    private int price;

    private int quantity;

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", itemName='" + itemName + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public OrderMessage copy() {
        var copy = new OrderMessage();

        copy.setCreditCardNumber(this.getCreditCardNumber());
        copy.setItemName(this.getItemName());
        copy.setOrderDateTime(this.getOrderDateTime());
        copy.setOrderLocation(this.getOrderLocation());
        copy.setOrderNumber(this.getOrderNumber());
        copy.setPrice(this.getPrice());
        copy.setQuantity(this.getQuantity());

        return copy;
    }

}
