package com.rs.kafka.broker.consumer;

import com.rs.kafka.broker.message.*;
import org.slf4j.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Service
@KafkaListener(topics = "t.commodity.promotion")
public class PromotionListener {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionListener.class);

    @KafkaHandler
    public void listenDiscount(DiscountMessage message) {
        LOG.info("Processing discount : {}", message);
    }

    @KafkaHandler
    public void listenPromotion(PromotionMessage message) {
        LOG.info("Processing promotion : {}", message);
    }
}
