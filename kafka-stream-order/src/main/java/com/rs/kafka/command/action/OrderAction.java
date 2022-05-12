package com.rs.kafka.command.action;

import com.rs.kafka.api.request.*;
import com.rs.kafka.broker.message.*;
import com.rs.kafka.broker.producer.*;
import com.rs.kafka.entity.*;
import com.rs.kafka.repositories.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * created by rs 5/11/2022.
 */
@Component
public class OrderAction {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer producer;

    public Order convertToOrder(OrderRequest request) {
        var result = new Order();

        result.setCreditCardNumber(request.getCreditCardNumber());
        result.setOrderLocation(request.getOrderLocation());
        result.setOrderDateTime(LocalDateTime.now());
        result.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        List<OrderItem> items = request.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
        items.forEach(item -> item.setOrder(result));

        result.setItems(items);

        return result;
    }

    private OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
        var result = new OrderItem();

        result.setItemName(itemRequest.getItemName());
        result.setPrice(itemRequest.getPrice());
        result.setQuantity(itemRequest.getQuantity());

        return result;
    }

    public void publishToKafka(OrderItem item) {
        var orderMessage = new OrderMessage();

        orderMessage.setItemName(item.getItemName());
        orderMessage.setPrice(item.getPrice());
        orderMessage.setQuantity(item.getQuantity());

        orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
        orderMessage.setOrderLocation(item.getOrder().getOrderLocation());
        orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
        orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());

        producer.publish(orderMessage);
    }

    public void saveToDatabase(Order order) {
        orderRepository.save(order);
        order.getItems().forEach(orderItemRepository::save);
    }
}
