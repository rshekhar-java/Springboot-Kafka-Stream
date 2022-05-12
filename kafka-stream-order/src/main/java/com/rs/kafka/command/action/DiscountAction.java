package com.rs.kafka.command.action;

import com.rs.kafka.api.request.*;
import com.rs.kafka.broker.message.*;
import com.rs.kafka.broker.producer.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Component
public class DiscountAction {

    @Autowired
    private DiscountProducer producer;

    public void publishToKafka(DiscountRequest request) {
        var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
        producer.publish(message);
    }

}
