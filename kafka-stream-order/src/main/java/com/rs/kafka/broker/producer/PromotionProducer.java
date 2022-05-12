package com.rs.kafka.broker.producer;

import com.rs.kafka.broker.message.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;

/**
 * created by rs 5/11/2022.
 */
@Service
public class PromotionProducer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionProducer.class);

    @Autowired
    private KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    public void publish(PromotionMessage message) {
        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", message).get();

            LOG.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error publishing {}, cause {}", message, e.getMessage());
        }
    }
}
