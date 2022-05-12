package com.rs.kafka.broker.stream.promotion;

import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;

/**
 * created by rs 5/10/2022.
 */
@Configuration
public class PromotionUppercaseStream {


    @Bean
    public KStream<String, String> kstreamPromotionUppercase(StreamsBuilder builder) {
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), Serdes.String()));
        var uppercaseStream = sourceStream.mapValues(s -> s.toUpperCase());

        uppercaseStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Original stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("Uppercase stream"));

        return sourceStream;
    }
}
