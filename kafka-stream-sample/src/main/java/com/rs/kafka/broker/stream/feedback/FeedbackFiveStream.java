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
@Configuration
public class FeedbackFiveStream {
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    private Predicate<String, String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    private Predicate<String, String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var sourceStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));
//
//		var feedbackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());
//
//		feedbackStreams[0].through("t-commodity-feedback-five-good").groupByKey().count().toStream()
//				.to("t-commodity-feedback-five-good-count");
//		feedbackStreams[1].through("t-commodity-feedback-five-bad").groupByKey().count().toStream()
//				.to("t-commodity-feedback-five-bad-count");


        sourceStream.flatMap(splitWords()).split()
                .branch(isGoodWord(),
                        Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-good"))
                                .groupByKey().count().toStream().to("t-commodity-feedback-five-good-count")))
                .branch(isBadWord(),
                        Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-bad"))
                                .groupByKey().count().toStream().to("t-commodity-feedback-five-bad-count")));

        return sourceStream;
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getLocation(), word)).collect(Collectors.toList());
    }

}
