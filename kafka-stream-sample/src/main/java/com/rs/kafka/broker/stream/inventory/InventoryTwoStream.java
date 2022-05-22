package com.rs.kafka.broker.stream.inventory;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.rs.kafka.broker.message.InventoryMessage;

//@Configuration
public class InventoryTwoStream {

	@Bean
	public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var inventorySerde = new JsonSerde<>(InventoryMessage.class);
		var longSerde = Serdes.Long();

		var inventoryStream = builder.stream("t-commodity-inventory", Consumed.with(stringSerde, inventorySerde));

		inventoryStream
				.mapValues((item, inventory) -> inventory.getType().equalsIgnoreCase("ADD") ? inventory.getQuantity()
						: (-1 * inventory.getQuantity()))
				.groupByKey()
				.aggregate(() -> 0l, (aggKey, newValue, aggValue) -> aggValue + newValue,
						Materialized.with(stringSerde, longSerde))
				.toStream().to("t-commodity-inventory-total-two", Produced.with(stringSerde, longSerde));
		;

		return inventoryStream;
	}

}
