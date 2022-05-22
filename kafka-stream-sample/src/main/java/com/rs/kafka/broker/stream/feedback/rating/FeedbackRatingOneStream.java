package com.rs.kafka.broker.stream.feedback.rating;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.rs.kafka.broker.message.FeedbackMessage;
import com.rs.kafka.broker.message.FeedbackRatingOneMessage;

//@Configuration
public class FeedbackRatingOneStream {

	@Bean
	public KStream<String, FeedbackMessage> kstreamFeedbackRating(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
		var feedbackRatingOneSerde = new JsonSerde<>(FeedbackRatingOneMessage.class);
		var feedbackRatingOneStoreValueSerde = new JsonSerde<>(FeedbackRatingOneStoreValue.class);

		var feedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

		var feedbackRatingStateStoreName = "feedbackRatingOneStateStore";
		var storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
		var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingOneStoreValueSerde);

		builder.addStateStore(storeBuilder);

		feedbackStream
				.transformValues(() -> new FeedbackRatingOneValueTransformer(feedbackRatingStateStoreName),
						feedbackRatingStateStoreName)
				.to("t-commodity-feedback-rating-one", Produced.with(stringSerde, feedbackRatingOneSerde));
		
		return feedbackStream;
	}

}
