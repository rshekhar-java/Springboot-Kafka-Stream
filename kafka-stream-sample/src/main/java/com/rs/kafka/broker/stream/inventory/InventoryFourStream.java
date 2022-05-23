package com.rs.kafka.broker.stream.inventory;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.rs.kafka.broker.message.InventoryMessage;
import com.rs.kafka.util.InventoryTimestampExtractor;

//@Configuration
public class InventoryFourStream {

	@Bean
	public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var inventorySerde = new JsonSerde<>(InventoryMessage.class);
		var inventoryTimestampExtractor = new InventoryTimestampExtractor();

		var inventoryStream = builder.stream("t-commodity-inventory",
				Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

		inventoryStream.to("t-commodity-inventory-four", Produced.with(stringSerde, inventorySerde));

		return inventoryStream;
	}

}
