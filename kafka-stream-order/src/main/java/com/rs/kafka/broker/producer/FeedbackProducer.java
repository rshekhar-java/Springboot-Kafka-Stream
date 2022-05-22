package com.rs.kafka.broker.producer;

import com.rs.kafka.broker.message.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/22/2022.
 */
@Service
public class FeedbackProducer {

    @Autowired
    private KafkaTemplate<String, FeedbackMessage> kafkaTemplate;

    public void publish(FeedbackMessage message) {
        kafkaTemplate.send("t-commodity-feedback", message);
    }
}
