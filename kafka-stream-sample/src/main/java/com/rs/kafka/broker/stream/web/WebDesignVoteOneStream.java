package com.rs.kafka.broker.stream.web;

import com.rs.kafka.broker.message.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.rs.kafka.broker.message.WebColorVoteMessage;
import com.rs.kafka.broker.message.WebDesignVoteMessage;
import com.rs.kafka.broker.message.WebLayoutVoteMessage;
import com.rs.kafka.util.WebColorVoteTimestampExtractor;
import com.rs.kafka.util.WebLayoutVoteTimestampExtractor;

//@Configuration
public class WebDesignVoteOneStream {

	@Bean
	public KStream<String, WebDesignVoteMessage> kstreamWebDesignVote(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
		var layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
		var designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

		builder.stream("t-commodity-web-vote-color",
				Consumed.with(stringSerde, colorSerde, new WebColorVoteTimestampExtractor(), null))
				.mapValues(v -> v.getColor()).to("t-commodity-web-vote-one-username-color");

		var colorTable = builder.table("t-commodity-web-vote-one-username-color",
				Consumed.with(stringSerde, stringSerde));

		builder.stream("t-commodity-web-vote-layout",
				Consumed.with(stringSerde, layoutSerde, new WebLayoutVoteTimestampExtractor(), null))
				.mapValues(v -> v.getLayout()).to("t-commodity-web-vote-one-username-layout");

		var layoutTable = builder.table("t-commodity-web-vote-one-username-layout",
				Consumed.with(stringSerde, stringSerde));

		var joinTable = colorTable.join(layoutTable, this::voteJoiner, Materialized.with(stringSerde, designSerde));

		joinTable.toStream().to("t-commodity-web-vote-one-result");

		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getColor(), votedDesign.getColor()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - color"));

		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getLayout(), votedDesign.getLayout()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - layout"));

		return joinTable.toStream();
	}

	private WebDesignVoteMessage voteJoiner(String color, String layout) {
		var result = new WebDesignVoteMessage();

		result.setColor(color);
		result.setLayout(layout);

		return result;
	}
}
