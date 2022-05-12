package com.rs.kafka.entity;

import javax.persistence.*;
import java.time.*;
import java.util.*;

/**
 * created by rs 5/11/2022.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Column(nullable = false, length = 20)
    private String creditCardNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @Id
    @GeneratedValue
    private int orderId;

    @Column(nullable = false, length = 200)
    private String orderLocation;

    @Column(nullable = false, length = 20)
    private String orderNumber;

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "Order{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", items=" + items +
                ", orderDateTime=" + orderDateTime +
                ", orderId=" + orderId +
                ", orderLocation='" + orderLocation + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
