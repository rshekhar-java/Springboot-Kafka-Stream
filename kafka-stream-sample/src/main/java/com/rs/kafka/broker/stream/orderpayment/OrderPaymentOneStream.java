package com.rs.kafka.broker.stream.orderpayment;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.course.kafka.broker.message.OnlineOrderMessage;
import com.course.kafka.broker.message.OnlineOrderPaymentMessage;
import com.course.kafka.broker.message.OnlinePaymentMessage;
import com.course.kafka.util.OnlineOrderTimestampExtractor;
import com.course.kafka.util.OnlinePaymentTimestampExtractor;

//@Configuration
public class OrderPaymentOneStream {

	@Bean
	public KStream<String, OnlineOrderMessage> kstreamOrderPayment(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
		var paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
		var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

		var orderStream = builder.stream("t-commodity-online-order",
				Consumed.with(stringSerde, orderSerde, new OnlineOrderTimestampExtractor(), null));

		var paymentStream = builder.stream("t-commodity-online-payment",
				Consumed.with(stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));

		orderStream
				.join(paymentStream, this::joinOrderPayment,
						JoinWindows.of(Duration.ofHours(1l)).grace(Duration.ofMillis(0l)),
						StreamJoined.with(stringSerde, orderSerde, paymentSerde))
				.to("t-commodity-join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde));

		return orderStream;
	}

	private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage order, OnlinePaymentMessage payment) {
		var result = new OnlineOrderPaymentMessage();

		result.setOnlineOrderNumber(order.getOnlineOrderNumber());
		result.setOrderDateTime(order.getOrderDateTime());
		result.setTotalAmount(order.getTotalAmount());
		result.setUsername(order.getUsername());

		result.setPaymentDateTime(payment.getPaymentDateTime());
		result.setPaymentMethod(payment.getPaymentMethod());
		result.setPaymentNumber(payment.getPaymentNumber());

		return result;
	}

}
