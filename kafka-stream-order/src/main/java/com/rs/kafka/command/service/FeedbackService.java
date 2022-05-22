package com.rs.kafka.command.service;

import com.rs.kafka.api.request.*;
import com.rs.kafka.command.action.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/22/2022.
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackAction action;

    public void createFeedback(FeedbackRequest request) {
        action.publishToKafka(request);
    }

}
