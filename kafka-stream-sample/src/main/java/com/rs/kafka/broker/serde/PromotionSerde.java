package com.rs.kafka.broker.serde;

import com.rs.kafka.broker.message.*;

/**
 * created by rs 5/11/2022.
 */
public class PromotionSerde extends CustomJsonSerde<PromotionMessage>{
    public PromotionSerde() {
        super(new CustomJsonSerializer<PromotionMessage>(),
                new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }

}
