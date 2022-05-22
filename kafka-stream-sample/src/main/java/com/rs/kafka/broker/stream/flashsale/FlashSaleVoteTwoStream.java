package com.rs.kafka.broker.stream.flashsale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.course.kafka.broker.message.FlashSaleVoteMessage;

//@Configuration
public class FlashSaleVoteTwoStream {

	@Bean
	public KStream<String, String> flashSaleVote(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

		var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 30));
		var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 30));

		var flashSaleVoteStream = builder
				.stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
				.transformValues(() -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
				.filter((key, transformedValue) -> transformedValue != null)
				.map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));

		flashSaleVoteStream.to("t-commodity-flashsale-vote-user-item");

		builder.table("t-commodity-flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
				.groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count().toStream()
				.to("t-commodity-flashsale-vote-two-result");

		return flashSaleVoteStream;
	}

}
