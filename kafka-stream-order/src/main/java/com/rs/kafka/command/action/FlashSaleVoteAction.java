package com.rs.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rs.kafka.api.request.FlashSaleVoteRequest;
import com.rs.kafka.broker.message.FlashSaleVoteMessage;
import com.rs.kafka.broker.producer.FlashSaleVoteProducer;

@Component
public class FlashSaleVoteAction {

	@Autowired
	private FlashSaleVoteProducer producer;

	public void publishToKafka(FlashSaleVoteRequest request) {
		var message = new FlashSaleVoteMessage();

		message.setCustomerId(request.getCustomerId());
		message.setItemName(request.getItemName());

		producer.publish(message);
	}

}
