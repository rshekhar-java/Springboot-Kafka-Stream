package com.rs.kafka.broker.stream.customer.preference;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.course.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.course.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import com.course.kafka.broker.message.CustomerPreferenceWishlistMessage;

//@Configuration
public class CustomerPreferenceOneStream {

	private static final CustomerPreferenceShoppingCartAggregator SHOPPING_CART_AGGREGATOR = new CustomerPreferenceShoppingCartAggregator();

	private static final CustomerPreferenceWishlistAggregator WISHLIST_AGGREGATOR = new CustomerPreferenceWishlistAggregator();

	@Bean
	public KStream<String, CustomerPreferenceAggregateMessage> kstreamCustomerPreferenceAll(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var shoppingCartSerde = new JsonSerde<>(CustomerPreferenceShoppingCartMessage.class);
		var wishlistSerde = new JsonSerde<>(CustomerPreferenceWishlistMessage.class);
		var aggregateSerde = new JsonSerde<>(CustomerPreferenceAggregateMessage.class);

		var groupedShoppingCartStream = builder
				.stream("t-commodity-customer-preference-shopping-cart", Consumed.with(stringSerde, shoppingCartSerde))
				.groupByKey();

		var groupedWishlistStream = builder
				.stream("t-commodity-customer-preference-wishlist", Consumed.with(stringSerde, wishlistSerde))
				.groupByKey();

		var customerPreferenceStream = groupedShoppingCartStream.cogroup(SHOPPING_CART_AGGREGATOR)
				.cogroup(groupedWishlistStream, WISHLIST_AGGREGATOR)
				.aggregate(() -> new CustomerPreferenceAggregateMessage(),
						Materialized.with(stringSerde, aggregateSerde))
				.toStream();

		customerPreferenceStream.to("t-commodity-customer-preference-all", Produced.with(stringSerde, aggregateSerde));

		return customerPreferenceStream;
	}

}
