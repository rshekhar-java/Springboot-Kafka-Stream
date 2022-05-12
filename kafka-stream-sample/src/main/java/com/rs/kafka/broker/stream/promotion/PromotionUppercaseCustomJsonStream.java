package com.rs.kafka.broker.stream.promotion;

import com.rs.kafka.broker.message.*;
import com.rs.kafka.broker.serde.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;

/**
 * created by rs 5/12/2022.
 */
//@Configuration
public class PromotionUppercaseCustomJsonStream {

    @Bean
    public KStream<String, PromotionMessage> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var jsonSerde = new PromotionSerde();
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, jsonSerde));

        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t-commodity-promotion-uppercase", Produced.with(stringSerde, jsonSerde));

        sourceStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("Custom JSON serde original stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("Custom JSON serde uppercase stream"));

        return sourceStream;
    }

    public PromotionMessage uppercasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
