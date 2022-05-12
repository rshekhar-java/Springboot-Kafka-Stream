package com.rs.kafka.api.server;

import com.rs.kafka.api.request.*;
import com.rs.kafka.command.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * created by rs 5/11/2022.
 */
@RestController
@RequestMapping("/api/promotion")
public class PromotionApi {
    @Autowired
    private PromotionService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody PromotionRequest request) {
        service.createPromotion(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request.getPromotionCode());
    }
}
