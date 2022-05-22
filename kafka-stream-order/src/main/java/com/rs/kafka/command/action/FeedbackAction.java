package com.rs.kafka.command.action;

import com.rs.kafka.api.request.*;
import com.rs.kafka.broker.message.*;
import com.rs.kafka.broker.producer.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.concurrent.*;

/**
 * created by rs 5/22/2022.
 */
@Component
public class FeedbackAction {
    @Autowired
    private FeedbackProducer producer;

    public void publishToKafka(FeedbackRequest request) {
        var message = new FeedbackMessage();
        message.setFeedback(request.getFeedback());
        message.setLocation(request.getLocation());
        message.setRating(request.getRating());
        // random date time between last 7 days - now
        message.setFeedbackDateTime(LocalDateTime.now().minusHours(ThreadLocalRandom.current().nextLong(7 * 7)));

        producer.publish(message);
    }
}
