package com.rs.kafka.command.service;

import com.rs.kafka.api.request.*;
import com.rs.kafka.command.action.*;
import com.rs.kafka.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Service
public class OrderService {
    @Autowired
    private OrderAction action;

    public String saveOrder(OrderRequest request) {
        // 1. convert OrderRequest to Order
        Order order = action.convertToOrder(request);

        // 2. save Order to database
        action.saveToDatabase(order);

        // 3. flatten the item & order as kafka message, and publish
        order.getItems().forEach(action::publishToKafka);

        // 4. return order number (auto generated)
        return order.getOrderNumber();
    }
}
