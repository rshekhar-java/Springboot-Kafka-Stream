package com.rs.kafka.broker.producer;

import com.rs.kafka.broker.message.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Service
public class DiscountProducer {

    @Autowired
    private KafkaTemplate<String, DiscountMessage> kafkaTemplate;

    public void publish(DiscountMessage message) {
        kafkaTemplate.send("t-commodity-promotion", message);
    }

}
