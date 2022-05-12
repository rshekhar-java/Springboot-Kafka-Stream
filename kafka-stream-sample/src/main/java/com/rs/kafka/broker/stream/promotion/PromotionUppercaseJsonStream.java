package com.rs.kafka.broker.stream.promotion;

import com.fasterxml.jackson.databind.*;
import com.rs.kafka.broker.message.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;

/**
 * created by rs 5/12/2022.
 */
//@Configuration
public class PromotionUppercaseJsonStream {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, stringSerde));
        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("JSON original stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("JSON uppercase stream"));

        return sourceStream;
    }

    public String uppercasePromotionCode(String message) {
        try {
            var original = objectMapper.readValue(message, PromotionMessage.class);
            var converted = new PromotionMessage(original.getPromotionCode().toUpperCase());

            return objectMapper.writeValueAsString(converted);
        } catch (Exception e) {
            return "";
        }
    }
}
