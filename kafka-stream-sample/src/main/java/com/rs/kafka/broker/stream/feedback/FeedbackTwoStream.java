package com.rs.kafka.broker.stream.feedback;

import com.rs.kafka.broker.message.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.support.serializer.*;

import java.util.*;
import java.util.stream.*;

/**
 * created by rs 5/22/2022.
 */
//@Configuration
public class FeedbackTwoStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Bean
    public KStream<String, String> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var goodFeedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMap((key, value) -> Arrays
                        .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                        .filter(word -> GOOD_WORDS.contains(word)).distinct()
                        .map(goodWord -> KeyValue.pair(value.getLocation(), goodWord)).collect(Collectors.toList()));

        goodFeedbackStream.to("t-commodity-feedback-two-good");

        return goodFeedbackStream;
    }

}
