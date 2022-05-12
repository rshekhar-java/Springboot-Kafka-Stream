package com.rs.kafka.broker.consumer;

import com.rs.kafka.broker.message.*;
import org.slf4j.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Service
public class PromotionUppercaseListener {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionUppercaseListener.class);

    @KafkaListener(topics = "t-commodity-promotion-uppercase")
    public void listenPromotionUppercase(PromotionMessage message) {
        LOG.info("Processing uppercase promotion : {}", message);
    }

}
