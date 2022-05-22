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
public class FeedbackOneStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Bean
    public KStream<String, String> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var goodFeedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMapValues(mapperGoodWords());

        goodFeedbackStream.to("t-commodity-feedback-one-good");

        return goodFeedbackStream;
    }

    private ValueMapper<FeedbackMessage, Iterable<String>> mapperGoodWords() {
        return feedbackMessage -> Arrays
                .asList(feedbackMessage.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .filter(word -> GOOD_WORDS.contains(word)).distinct().collect(Collectors.toList());
    }
}
