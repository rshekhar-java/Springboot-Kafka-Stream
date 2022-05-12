package com.rs.kafka.command.service;

import com.rs.kafka.api.request.*;
import com.rs.kafka.command.action.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * created by rs 5/11/2022.
 */
@Service
public class PromotionService {

    @Autowired
    private PromotionAction action;

    public void createPromotion(PromotionRequest request) {
        action.publishToKafka(request);
    }

}
